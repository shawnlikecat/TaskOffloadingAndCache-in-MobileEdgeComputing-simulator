package modelAlgorithm;

import entity.baseStation;
import entity.edgeServe;
import entity.task;
import entity.userRequest;
import org.apache.commons.beanutils.BeanUtils;

import java.beans.beancontext.BeanContext;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class allocation {
    models model = new models();
    public List<userRequest> isLocal(List<userRequest> requestList) throws InvocationTargetException, IllegalAccessException {
        //List<userRequest> temp = new ArrayList<>();
        List<userRequest> requestList1 = new ArrayList<>();
        for(int i=0;i<requestList.size();i++)
        {
            userRequest request1 = new userRequest();
            BeanUtils.copyProperties(request1,requestList.get(i));
            //temp.add(request1);
            List cost = model.localProcess(request1);
            double time = request1.getBirthTime().getTime()+(double)cost.get(0)*1000;
            request1.setT((double) cost.get(0));
            request1.setE((double) cost.get(1));
            request1.setFinishTime(new Date((long)time));
            request1.setIsLoacl(1);
            requestList1.add(request1);
        }
        //System.out.println(requestList1.get(0).getT()+"  "+requestList1.get(0).getE());
        return requestList1;
    }
    public List<userRequest> isMec(List<baseStation> baseStationes, List<edgeServe> edgeServes, List<userRequest> requestList,List<task> taskList,int isCa) throws InvocationTargetException, IllegalAccessException {
        List<userRequest> requestList2 = new ArrayList<>();;
        baseStation baseStation1 = baseStationes.get(0);
        edgeServe edgeServe1 = edgeServes.get(0);
        for(int i=0;i<requestList.size();i++)
        {
            userRequest request1 = new userRequest();
            BeanUtils.copyProperties(request1,requestList.get(i));
            List cost = new ArrayList();
            double time = 0;
            if(isCa == 1){
                if(request1.getTask().getIsCache()==0)
                {
                    cost = model.mecProcess(baseStation1,edgeServe1,request1);
                    time = request1.getBirthTime().getTime()+(double)cost.get(0)*1000;
                }
                else {
            
                    cost = model.mecProcessCache(baseStation1,edgeServe1,request1,taskList);
                    time = request1.getBirthTime().getTime()+(double)cost.get(0)*1000;
                }
            }
            else
            {
                
                cost = model.mecProcess(baseStation1,edgeServe1,request1);
                time = request1.getBirthTime().getTime()+(double)cost.get(0)*1000;
            }

            request1.setT((double) cost.get(0));
            request1.setE((double) cost.get(1));
            request1.setFinishTime(new Date((long)time));
        
            request1.setIsLoacl(0);
            requestList2.add(request1);
        }
        return requestList2;
    }
    public userRequest isMecCost(List<baseStation> baseStationes, List<edgeServe> edgeServes, userRequest request,List<task> taskList,int isCa) throws InvocationTargetException, IllegalAccessException {
       
        List<userRequest> requestList2 = new ArrayList<>();;
        baseStation baseStation1 = baseStationes.get(0);
        edgeServe edgeServe1 = edgeServes.get(0);
        userRequest request1 = new userRequest();
        BeanUtils.copyProperties(request1,request);
        List cost = new ArrayList();
        double time = 0;
        if(isCa == 1){
            if(request1.getTask().getIsCache()==0)
            {
                cost = model.mecProcess(baseStation1,edgeServe1,request1);
                time = request1.getBirthTime().getTime()+(double)cost.get(0)*1000;
            }
            else {
              
                cost = model.mecProcessCache(baseStation1,edgeServe1,request1,taskList);
                time = request1.getBirthTime().getTime()+(double)cost.get(0)*1000;
            }
        }
        else
        {
            
            cost = model.mecProcess(baseStation1,edgeServe1,request1);
            time = request1.getBirthTime().getTime()+(double)cost.get(0)*1000;
        }

        request1.setT((double) cost.get(0));
        request1.setE((double) cost.get(1));
        request1.setFinishTime(new Date((long)time));
        
        request1.setIsLoacl(0);
        return request1;
    }
    public userRequest isLocalCost(userRequest request) throws InvocationTargetException, IllegalAccessException {
        userRequest request1 = new userRequest();
        BeanUtils.copyProperties(request1,request);
        
        List cost = model.localProcess(request1);
        double time = request1.getBirthTime().getTime()+(double)cost.get(0)*1000;
        request1.setT((double) cost.get(0));
        request1.setE((double) cost.get(1));
        request1.setFinishTime(new Date((long)time));
        request1.setIsLoacl(1);
        return request1;
    }

}
