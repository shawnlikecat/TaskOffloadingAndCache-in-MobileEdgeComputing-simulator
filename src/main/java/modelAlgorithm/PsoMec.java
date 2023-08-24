package modelAlgorithm;
import entity.baseStation;
import entity.edgeServe;
import entity.task;
import entity.userRequest;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PsoMec {
    double[] position; 
    double[] cacheDecision;
    double[] velocity; 
    double[] pBest;    
    double[] cBest;
    double pBestFitness;
    List Memory = new ArrayList();
    List<task> taskListCache = new ArrayList<>();
    double C;
    List psoCacheMemory;
    Random rand;
    public PsoMec(double C,List psoCacheMemory,List<task> taskList_org,Random rand,int decisionSpaceSize,int countCache) throws InvocationTargetException, IllegalAccessException {
        
        this.taskListCache = taskList_org;
        this.rand = rand;
        this.psoCacheMemory = psoCacheMemory;
        this.C = C;
        position = new double[decisionSpaceSize];
        cacheDecision = new double[taskList_org.size()];
        int countCache1 = 0;
        
        psoCache();
        for (int i = 0; i < decisionSpaceSize; i++){
            int decision = rand.nextInt(0,2);
            position[i] = decision;
        }
        velocity = new double[decisionSpaceSize];
        for (int i = 0; i < decisionSpaceSize; i++){
            velocity[i] = rand.nextDouble(-0.5,0.5);
        }

        pBest = position;
        cBest = cacheDecision;
        pBestFitness = Double.MAX_VALUE; 
    }

    public void psoCache() throws InvocationTargetException, IllegalAccessException {
        Memory = new ArrayList();
        int cacheSum = 0;
        int countCache = 0;
        int countNoCache = 0;
        while(cacheSum<C&&countCache<taskListCache.size()&&countNoCache<taskListCache.size()){
            for (int i =0;i<taskListCache.size();i++)
            {
                int c = rand.nextInt(2);
                if(c==1){
                    if(Memory.size() == 0&&taskListCache.get(i).getI()<=C){
                        Memory.add(taskListCache.get(i));
                        cacheSum = (int) taskListCache.get(i).getI();
                        this.taskListCache.get(i).setIsCache(c);
                        cacheDecision[i] = 1;
                        countCache = countCache +1;
                    }
                    
                    else if(Memory.size() != 0&&taskListCache.get(i).getI()<C){
                        int tempSum = (int) (cacheSum+taskListCache.get(i).getI());
                        if(tempSum>C){
                            countNoCache = countNoCache +1;
                            cacheDecision[i] = 0;
                        }
                        else {
                            
                            int is = 0;
                            for (int k = 0; k < Memory.size(); k++) {
                                String taskName = ((task) Memory.get(k)).getName();
                               
                                if (taskName.equals(taskListCache.get(i).getName())) {
                                    is = 1;
                                    break;
                                }
                            }
                            if(is ==1 )
                                break;
                            else {
                                Memory.add(taskListCache.get(i));
                                cacheSum = tempSum;
                                countCache = countCache +1;
                                this.taskListCache.get(i).setIsCache(c);
                                cacheDecision[i] = 1;
                            }

                        }
                    }

                }
                else{
                    countNoCache = countNoCache +1;
                }

            }
        }
    }
    
    public void update(Random rand, double[] gBest, double[] w, double[] c1, double[] c2) {
        
        for (int i = 0; i < velocity.length; i++) {
            velocity[i] = w[i] * velocity[i]
                    + c1[i] * rand.nextDouble() * (pBest[i] - position[i])
                    + c2[i] * rand.nextDouble() * (gBest[i] - position[i]);
        }

        
        for (int i = 0; i < position.length; i++) {
            position[i] += velocity[i];
        }
    }

    
    public double calculateFitness(List<baseStation> baseStationes, List<edgeServe> edgeServes, List<userRequest> requestList, int decisionSpaceSize) throws InvocationTargetException, IllegalAccessException {
        allocation allocation = new allocation();
        double fitness = 0;
        double cost = 0;
        double costAll = 0;
        userRequest costReq = new userRequest();
        for(int i=0;i<decisionSpaceSize;i++){
            if(position[i]==0){
                costReq = allocation.isLocalCost(requestList.get(i));
                cost = costReq.getT()+costReq.getE();
            }
            else if(position[i]==1){
                costReq = allocation.isMecCost(baseStationes,edgeServes, requestList.get(i),taskListCache,1);
                cost = costReq.getT()+costReq.getE();
            }
            costAll = cost + costAll;
        }
        fitness = costAll;
        
        if (fitness < pBestFitness) {
            pBestFitness = fitness;
            System.arraycopy(position, 0, pBest, 0, position.length);
            System.arraycopy(cacheDecision, 0, cBest, 0, cacheDecision.length);
        }

        return fitness;
    }
}

