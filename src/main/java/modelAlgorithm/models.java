package modelAlgorithm;

import entity.*;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class models {
    baseFunction baseFunction = new baseFunction();
   
    public List<userRequest> cacheUpdate(List<userRequest> lastCache, List<userRequest> requestList, int M){
        
        List<userRequest> taskRank = priorityValue(requestList,1,1);
        List<userRequest> currentCache = baseFunction.sort(null,taskRank,M);
        
        if (lastCache != null)
            lastCache = priorityValue(lastCache,1,1);
            currentCache =baseFunction.sort(lastCache,currentCache,M);
        return currentCache;
    }
    
    public List<userRequest> priorityValue(List<userRequest> requestList, float a , float b) {
        
        for(int i=0;i<requestList.size();i++)
        {
            userRequest request = requestList.get(i);
            float freK = fresh(request);
            List<Float> result = popular(requestList,request);
            float rK =  result.get(1);
            float popK = result.get(0);
            float priK = a*(popK)+b*(freK);
            request.getTask().setPriValue(priK);
            request.getTask().setTaskPop(popK);
            request.getTask().setRk(rK);
            requestList.set(i,request);
        }
        return requestList;
    }
    public float fresh(userRequest request){
        Date tKnow = new Date();
        long tKgen = request.getBirthTime().getTime()-1;
        long freK = 1/(1+(tKnow.getTime()-tKgen));
        return freK;
    }

    public List<Float> popular(List<userRequest> requestList, userRequest request){
        
        List bothK = baseFunction.countNumK(requestList,request);
        int Num = requestList.size()+1;
        int NumK = (int) bothK.get(0);
        Date tKlast = (Date) bothK.get(1);
        Date tKfirst = (Date) bothK.get(2);
        Date tKcom = (Date) bothK.get(3);
        float sizeMax = (float) bothK.get(4);
        float sizeK = request.getTask().getI();
        float rK = (float) NumK/Num;
        float tK =(float) (tKcom.getTime()-tKfirst.getTime())/NumK;
        float sK =(float) (tKlast.getTime()-tKfirst.getTime())/NumK;
        float weightK = (float) (1-(Math.log(sizeK)/Math.log(sizeMax)));

        float popk =weightK*(rK/(tK*sK));

        List<Float> result = new ArrayList<>();
        result.add(0,popk);
        result.add(1,rK);
        return result;
    }
   
    public List<Double> communication (baseStation baseStation, float sigma){
        
        float B = baseStation.getB();
        float pN = baseStation.getPn();
        float hN = baseStation.getPn();
        float N = 1+(pN*hN)/(sigma*sigma);
        double rNmec = (B*(Math.log(N)/Math.log(2)));
       
        double pMec = baseStation.getPmec();
        double N1 = 1+(pMec*hN)/(sigma*sigma);
        double rMecN =  (B*(Math.log(N1)/Math.log(2)));
        List<Double> R = new ArrayList<>();
        R.add(0,rNmec);
        R.add(1,rMecN);
        return R;
    }
    
    public List<Double> localProcess(userRequest request){
        
        double e = 1.0E-11;
        task task = request.getTask();
        userDevice userDevice = request.getUserDevice();
        double TnKlocal = (task.getW()/userDevice.getF());
        double EnKlocal = e*task.getW()*(userDevice.getF()*userDevice.getF());
        List<Double> cost = new ArrayList();
        
        cost.add(0,TnKlocal);
        cost.add(1,EnKlocal);
        return cost;
    }
    
    public List<Double> mecProcess(baseStation baseStation, edgeServe edgeServe, userRequest request){
        
        task task = request.getTask();
        userDevice userDevice = request.getUserDevice();
        List commu = communication(baseStation,1);
        double TnKtrans = (task.getI()/(double)commu.get(0));
        
        double TnKexe = task.getW()/edgeServe.getF();
        
        double TnKresult = task.getO()/(double)commu.get(1);
        
        double TnKoff = TnKtrans+TnKexe+TnKresult;
        double EnKoff = baseStation.getPn()*TnKtrans;
        
        List<Double> cost = new ArrayList();
        cost.add(0,TnKoff);
        cost.add(1,EnKoff);
        return cost;
    }
    
    public List<Double> mecProcessCache(baseStation baseStation, edgeServe edgeServe, userRequest request,List<task> taskList){
        
        int Ik_sum = 0;
        for(int i=0;i<taskList.size();i++) {
            if(Objects.equals(request.getTask().getName(), taskList.get(i).getName()))
            {Ik_sum=Ik_sum+1;}
        }
        int Sum = (int) Math.floor((double) edgeServe.getM()/(double) (Ik_sum/taskList.size()));
        double hitK = request.getTask().getRk()*Sum/(request.getTask().getRk()*Sum+(1-request.getTask().getRk())*taskList.size());
       
        List commu = communication(baseStation,1);
        double tNkCache= request.getTask().getO()/edgeServe.getR_read()+request.getTask().getO()/(double)commu.get(1);
        List<Double> cost = new ArrayList();
        cost.add(0,tNkCache);
        cost.add(1, (double) 0);
        return cost;
    }
}
