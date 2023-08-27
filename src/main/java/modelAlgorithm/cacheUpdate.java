package modelAlgorithm;

import entity.task;
import entity.userRequest;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
        int recordLen = psoCacheMemoryRecord.size();
        double[] lastM2 = (double[]) psoCacheMemoryRecord.get(recordLen-2);
        double[] lastM1 = (double[]) psoCacheMemoryRecord.get(recordLen-1);
        int arrayLen = lastM2.length;
        String[] lastM = new String[arrayLen];
        String[] lastM3 = new String[arrayLen];
        String[] compare = new String[lastM.length + lastM2.length];
        for (int i = 0; i < arrayLen; i++) {
            if(lastM1[i]==1)
                lastM[i]=taskList.get(i).getName();
            else
                lastM[i]=null;
        }
        for (int i = 0; i < arrayLen; i++) {
            if(lastM2[i]==1)
                lastM3[i]=taskList.get(i).getName();
            else
                lastM3[i]=null;
        }
        String[] mergedList = MergeStringArrays(lastM,lastM3,compare);
        return BubbleSort(mergedList);
    }
    public String[] MergeStringArrays(String[] lastM,String[] lastM3,String[] compare){
        List<String> list1 = Arrays.asList(lastM);
        List<String> list2 = Arrays.asList(lastM3);
        List<String> mergedList = new ArrayList<>();
        for (String item : list1) {
            if (!list2.contains(item)) {
                mergedList.add(item);
            }
        }
        mergedList.addAll(list2);
        String[] resultArray = mergedList.toArray(new String[0]);
        return resultArray;
    }
    public void BubbleSort(String[] mergedList) throws InvocationTargetException, IllegalAccessException {
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
        return sortedNames;
    }


}
