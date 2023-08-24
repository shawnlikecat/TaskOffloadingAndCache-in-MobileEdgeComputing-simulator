package modelAlgorithm;

import entity.task;
import entity.userRequest;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class cacheUpdate {
    List psoCacheMemoryRecord;
    List<task> taskList;
    List<userRequest> requestList;
    public cacheUpdate(List psoCacheMemoryRecord,List<task> taskList,List<userRequest> requestList){
        this.psoCacheMemoryRecord = psoCacheMemoryRecord;
        this.taskList = taskList;
        this.requestList = requestList;
    }
    public void toUpdate() throws InvocationTargetException, IllegalAccessException {
        BubbleSort();
       
    //    taskList.sort(Comparator.comparing(Task::getPop).reversed());

    //    int[] cacheDecision1 = {1, 0, 1, 0, 1};
    //    int[] cacheDecision2 = {0, 1, 1, 0, 0};
    //    int cacheCapacity = 30;

      
    //    int[] result = new int[5];
    //    int currentCacheSize = 0;

    //    for (int i = 0; i < taskList.size(); i++) {
    //        Task task = taskList.get(i);
    //        int size = task.getSize();
    //        int cache1 = cacheDecision1[i];
    //        int cache2 = cacheDecision2[i];

    //        if (cache1 == 1 && cache2 == 1 && currentCacheSize + size <= cacheCapacity) {
    //            result[i] = 1;
    //            currentCacheSize += size;
    //        } else {
    //            result[i] = 0;
    //        }
    //    }
    //    System.out.println(Arrays.toString(result));
    }
    public void BubbleSort() throws InvocationTargetException, IllegalAccessException {
        List<task> taskListTemp = new ArrayList<>();
        for (int i=0;i<taskList.size();i++){
            task taskTemp = new task();
            BeanUtils.copyProperties(taskTemp,taskList.get(i));
            taskListTemp.add(taskTemp);
        }
        
        int n = taskListTemp.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (taskListTemp.get(j).getPriValue() < taskListTemp.get(j + 1).getPriValue()) {
                    
                    task temp = taskListTemp.get(j);
                    taskListTemp.set(j, taskListTemp.get(j + 1));
                    taskListTemp.set(j + 1, temp);
                }
            }
        }
        String[] sortedNames = new String[n];
        for (int i = 0; i < n; i++) {
            sortedNames[i] = taskListTemp.get(i).getName();
        }
        for (String name : sortedNames) {
            System.out.println(name);
        }
    }


}
