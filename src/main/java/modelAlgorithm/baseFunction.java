package modelAlgorithm;

import entity.task;
import entity.userDevice;
import entity.userRequest;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class baseFunction {
    //List list = new ArrayList();
    public List<userRequest> sort(List<userRequest> lastCache, List<userRequest> currentCache, int M){

        return currentCache;
    }
    public List countNumK(List requestList, userRequest request){
        int NumK = 0;
        Date tKlast = null;
        Date tKfirst = null;
        Date tKcom = null;
        float sizeMax = 0;
        for (int i = 0; i < requestList.size(); i++) {
            userRequest request1 = (userRequest) requestList.get(i);
           
            if (Objects.equals(request.getTask().getName(), request1.getTask().getName())) {
                NumK = NumK + 1;
                if (NumK == 1) {
                    tKfirst = request1.getBirthTime();
                }

                tKlast = request1.getBirthTime();
                
                tKcom = request1.getFinishTime();

            }
            
            if (sizeMax <= request1.getTask().getI())
            {
                sizeMax=request1.getTask().getI();
            }

        }
        List Numk = new ArrayList();
        Numk.add(0, (int)NumK);
        Numk.add(1, tKlast);
        Numk.add(2, tKfirst);
        Numk.add(3, tKcom);
        Numk.add(4, sizeMax);

        return Numk;

    }

    public List randomList(List<task> taskList,List<task> taskList2,List<userRequest> requestList,int M,int N,Date startTime ,long timeRange,int k) throws NoSuchAlgorithmException {
        if (M > N)
            return null;
       
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<userRequest> requestList2 = null;
        if (k == 0) {
            for (int i = 0; i < M; i++) {
                task task1 = new task();
                taskList.add(task1);
                task1.setName("任务" + i);
                
                task1.setI(random.nextInt(2, 250));
                task1.setO(random.nextInt(1, (int) task1.getI()));
                task1.setW(random.nextInt(1, 120));
                task1.setIsCache(0);
                taskList.set(i, task1);
            }
        }
        //随机请求列表
        else {
            requestList2 = new ArrayList<>();
            for (int i = 0; i < N; i++) {

                userRequest request1 = new userRequest();
                userRequest request2 = new userRequest();
                requestList.add(request1);
                requestList2.add(request2);
                request1.setName("请求" + i);
                request2.setName("请求" + i);
                //随机请求开始时间
                long randomTime = startTime.getTime() + random.nextInt(1, (int) timeRange);
                request1.setBirthTime(new Date(randomTime));
                request2.setBirthTime(new Date(randomTime));
                //随机用户设备
                userDevice userDevice = new userDevice();
                int F = random.nextInt(1, 32);
                userDevice.setF(F);
                request1.setUserDevice(userDevice);
                request2.setUserDevice(userDevice);
                //随机任务
                int taskIndex = random.nextInt(M - 1);
                request1.setTask(taskList.get(taskIndex));
                request2.setTask(taskList2.get(taskIndex));
            }
        }

        List result = new ArrayList();
        result.add(0, taskList);
        result.add(1, requestList);
        result.add(2, requestList2);
        return result;
    }

    public List listCopy(List<task> list1,List<task> list2) throws InvocationTargetException, IllegalAccessException {

        for(int i=0;i<list2.size();i++)
        {
            task task1 = new task();
            BeanUtils.copyProperties(task1,list2.get(i));
            list1.add(task1);
        }
        return list1;
    }

    public List reqlistCopy(List<userRequest> list1,List<userRequest> list2) throws InvocationTargetException, IllegalAccessException {

        for(int i=0;i<list2.size();i++)
        {
            userRequest req1 = new userRequest();
            BeanUtils.copyProperties(req1,list2.get(i));
            req1.getTask().setIsCache(0);
            list1.add(req1);
        }
        return list1;
    }


}
