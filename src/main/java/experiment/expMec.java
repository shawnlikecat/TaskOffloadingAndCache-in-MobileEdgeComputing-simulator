package experiment;

import entity.baseStation;
import entity.edgeServe;
import entity.task;
import entity.userRequest;
import modelAlgorithm.*;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class expMec {
    static double x1= 0.6;
    static double y1= 0.4;
    List<userRequest> requestList= new ArrayList<>();
    List<userRequest> requestList_org= new ArrayList<>();
    List<task> taskList= new ArrayList<>();
    List<task> taskList_org= new ArrayList<>();
    int M;
    int N;
    double C;
    Date startTime;
    long timeRange;
    List<baseStation> baseStationes;
    List<edgeServe> edgeServes;
    List allocationArray_all = new ArrayList<>();
    List lastCache_all = new ArrayList();
    List lasCacheList_all = new ArrayList<>();
    List lastCache = new ArrayList();
    List allResults = new ArrayList();
    List randCacheMemory = new ArrayList();
    List psoCacheMemory = new ArrayList();
    List randCacheMemoryRecord = new ArrayList();
    List psoCacheMemoryRecord = new ArrayList();
    List<userRequest> requestList_local = new ArrayList<>();
    List<userRequest> requestList_mec  = new ArrayList<>();
    List<userRequest> requestList_mecNoCache = new ArrayList<>();

    List allocationArray = new ArrayList<>();
    costModel costModel = new costModel(x1,y1);

    List<Integer> psoDecision = new ArrayList<>();
    List<Integer> randDecision = new ArrayList<>();
    int countCache;
    baseFunction baseFunction = new baseFunction();
    allocation allocation = new allocation();
    Random rand = new Random();

    public expMec(List randCacheMemoryRecord,List psoCacheMemoryRecord,List<task> taskList,List<task> taskList_org,List<baseStation> baseStationes, List<edgeServe> edgeServes, int M, int N,double C, Date startTime, long timeRange) throws NoSuchAlgorithmException {
        this.M = M;
        this.N =N;
        this.C = C;
        this.randCacheMemoryRecord = randCacheMemoryRecord;
        this.psoCacheMemoryRecord = psoCacheMemoryRecord;
        this.startTime = startTime;
        this.timeRange =timeRange;
        this.baseStationes = baseStationes;
        this.edgeServes = edgeServes;
        this.taskList = taskList;
        this.taskList_org = taskList_org;
    }
    public List toSimulation() throws NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException {
        
        randCache();
        
        requestList = new ArrayList<>();
        List randomList2 = baseFunction.randomList(taskList,taskList_org,requestList,M,N,startTime,timeRange,1);
        requestList = (List<userRequest>) randomList2.get(1);
        models model = new models();

        requestList_org = (List<userRequest>) randomList2.get(2);

        allocationArray = new ArrayList<>();
        costModel = new costModel(x1,y1);
        try {
            requestList_local =allocation.isLocal(requestList);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            requestList_mec =allocation.isMec(baseStationes,edgeServes,requestList,taskList,1);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            requestList_mecNoCache = allocation.isMec(baseStationes,edgeServes,requestList,taskList,0);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        toLocal();
        toEdge();
        toNoCacheEdge();
        toCacheEdge();
        PSO pso = toPsoCacheEdge();
        for (int i=0;i<pso.gBest.length;i++)
            psoDecision.add((int) pso.gBest[i]);
        allResults.add(taskList);
        allResults.add(taskList_org);
        allResults.add(requestList);
        allResults.add(randDecision);
        allResults.add(psoDecision);
        requestList = model.priorityValue(requestList,1,1);
        requestList_org = model.priorityValue(requestList,1,1);
        return allResults;
    }

    private void toLocal(){
        double cost_T1 = 0;
        double cost_E1 = 0;
        double cost_all1 = 0;
        for(int i=0;i<requestList.size();i++)
        {
            cost_T1 = requestList_local.get(i).getT();
            cost_E1 = requestList_local.get(i).getE();
            cost_all1 = cost_all1+cost_T1+cost_E1;
        }
        //System.out.println("local：  "+cost_all1);
    }
    private void toEdge(){
        double cost_all1 = 0;
        double cost_T1=0;
        double cost_E1=0;
        for(int i=0;i<requestList.size();i++)
            cost_all1 = cost_all1 + requestList_mecNoCache.get(i).getT()+requestList_mecNoCache.get(i).getE();
        //System.out.println("edge：  "+cost_all1);
    }
    private void toNoCacheEdge(){
        double cost_T = 0;
        double cost_E = 0;
        double cost_all = 0;
        List<String> d = new ArrayList<>();
        for(int i=0;i<requestList.size();i++)
        {
            int a = 0;
            cost_T = requestList_local.get(i).getT();
            cost_E = requestList_local.get(i).getE();
            double costLocal = cost_T + cost_E;
            cost_T = requestList_mecNoCache.get(i).getT();
            cost_E = requestList_mecNoCache.get(i).getE();
            double costMec = cost_T + cost_E;
            if (costLocal<costMec){
                cost_all = cost_all+costLocal;
                a = 0;
            }
            else {
                cost_all = cost_all+costMec;
                a = 1;
            }
            randDecision.add(a);
            d.add(String.valueOf(a));

            //System.out.println("Global Best Decision: " + a);
        }
        // String result = String.join(",", d);
        // System.out.println("nocachedecision:"+result);
        // System.out.println("nocache：  "+cost_all);
    }
    private void toCacheEdge(){
        double cost_all = 0;
        double cost_T=0;
        double cost_E= 0;
        for(int i=0;i<requestList.size();i++)
        {
            int a = 2;
            double costLocal = costModel.costModel1(requestList_local.get(i).getT(),requestList_local.get(i).getE());
            double costMec = costModel.costModel1(requestList_mec.get(i).getT(),requestList_mec.get(i).getE());

            if (costLocal<costMec){
                cost_T = requestList_local.get(i).getT();
                cost_E = requestList_local.get(i).getE();
                userRequest request1 = requestList.get(i);
                double time = 0;
                request1.setT(cost_T);
                request1.setE(cost_E);
                time = request1.getBirthTime().getTime()+cost_T*1000;
                request1.setFinishTime(new Date((long)time));
                request1.setIsLoacl(1);
                requestList.set(i,request1);
                a = 0;
            }
            else {
                cost_T = requestList_mec.get(i).getT();
                cost_E = requestList_mec.get(i).getE();
                userRequest request1 = requestList.get(i);
                double time = 0;
                request1.setT(cost_T);
                request1.setE(cost_E);
                time = request1.getBirthTime().getTime()+cost_T*1000;
                request1.setFinishTime(new Date((long)time));
                request1.setIsLoacl(0);
                requestList.set(i,request1);
                a = 1;
            }
            allocationArray.add(a);
            cost_all = cost_all+cost_T+cost_E;
        }
        //System.out.println("cacheedge：  "+cost_all);
    }
    private PSO toPsoCacheEdge() throws InvocationTargetException, IllegalAccessException {
        

        int numParticles = 5000;
        int decisionSpaceSize = N;

        PSO pso = new PSO(C,psoCacheMemoryRecord,psoCacheMemory,baseStationes,edgeServes,taskList_org,requestList_org,rand,numParticles, decisionSpaceSize,countCache);

        int maxIterations = 100;
        double[] w = new double[decisionSpaceSize]; 
        double[] c1 = new double[decisionSpaceSize];
        double[] c2 = new double[decisionSpaceSize];
        
        try {
            pso.optimize(maxIterations, w, c1, c2);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        
        System.out.println("Global Best Decision: " + Arrays.toString(pso.gBest));
        System.out.println("Global Best Cache Decision: " + Arrays.toString(pso.cgBest));
        System.out.println("psocacheedge：  "+pso.gBestFitness);
        return pso;
    }
    public void randCache(){
        randCacheMemory = new ArrayList();
        int cacheSum = 0;
        int countCache = 0;
        int countNoCache = 0;
        while(cacheSum<C&&countCache<taskList.size()&&countNoCache<taskList.size()){
            for (int i =0;i<taskList.size();i++)
            {
                int c = rand.nextInt(2);
                if(c==1){
                    if(randCacheMemory.size() == 0&&taskList.get(i).getI()<=C){
                        this.randCacheMemory.add(taskList.get(i));
                        cacheSum = (int) taskList.get(i).getI();
                        this.taskList.get(i).setIsCache(c);
                        countCache = countCache +1;
                    }
                    
                    else if(randCacheMemory.size() != 0&&taskList.get(i).getI()<C){
                        int tempSum = (int) (cacheSum+taskList.get(i).getI());
                        if(tempSum>C){
                            countNoCache = countNoCache +1;
                        }
                        else {
                            
                            int is = 0;
                            for (int k = 0; k < randCacheMemory.size(); k++) {
                                String taskName = ((task) randCacheMemory.get(k)).getName();
                                
                                if (taskName.equals(taskList.get(i).getName())) {
                                    is = 1;
                                    break;
                                }
                            }
                            if(is ==1 )
                                break;
                            else {
                                randCacheMemory.add(taskList.get(i));
                                cacheSum = tempSum;
                                countCache = countCache +1;
                                this.taskList.get(i).setIsCache(c);
                            }

                        }
                    }

                }
                else{
                    countNoCache = countNoCache +1;
                }

            }
        }
        randCacheMemoryRecord.add(randCacheMemory);
    }
}
