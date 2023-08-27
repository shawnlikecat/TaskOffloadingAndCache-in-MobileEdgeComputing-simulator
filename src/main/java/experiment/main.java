package experiment;

import entity.*;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

import modelAlgorithm.baseFunction;
import modelAlgorithm.cacheUpdate;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class main {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ParseException, InvocationTargetException, IllegalAccessException {
        int xK = 3;
        int M=10;
        int N =20;
        double C = 500;
        int requestNum = 30;
        long timeRange = 86400*1000;
        Date startTime = new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("nowthetime: "+sdf.format(startTime)+"   "+startTime.getTime());
        List<edgeServe> edgeServes = new ArrayList<>();
        List<baseStation> baseStationes = new ArrayList<>();
        List<baseStation> allResults = new ArrayList<>();
        Object ob = new JSONParser().parse(new FileReader("src/main/java/data/baseStationes.json"));
        JSONObject js = (JSONObject) ob;
        JSONArray dataArr = (JSONArray) js.get("data");
        int baseLength = dataArr.size();
        for(int i=0;i<baseLength;i++)
        {
            JSONObject dataContext= (JSONObject) dataArr.get(i);
            baseStation baseStation1 = new baseStation();
            baseStationes.add(baseStation1);
            baseStation1.setName((String) dataContext.get("Name"));
            baseStation1.setB((long) dataContext.get("B"));
            baseStation1.setHn((long) dataContext.get("Hn"));
            baseStation1.setPn((long) dataContext.get("Pn"));
            baseStation1.setPmec((long) dataContext.get("Pmec"));
            baseStationes.set(i,baseStation1);
        }
        
        Object ob1 = new JSONParser().parse(new FileReader("src/main/java/data/edgeServes.json"));
        JSONObject js1 = (JSONObject) ob1;
        JSONArray dataArr1 = (JSONArray) js1.get("data");
        int edgeLength = dataArr1.size();
        for(int i=0;i<edgeLength;i++)
        {
            JSONObject dataContext1= (JSONObject) dataArr1.get(i);
            edgeServe edgeServe1 = new edgeServe();
            edgeServes.add(edgeServe1);
            edgeServe1.setName((String) dataContext1.get("Name"));
            edgeServe1.setF((long) dataContext1.get("F"));
            edgeServe1.setS((long) dataContext1.get("S"));
            edgeServe1.setC((long) dataContext1.get("C"));
            edgeServe1.setM((long) dataContext1.get("M"));
            edgeServe1.setR_read((long) dataContext1.get("R_read"));
            edgeServes.set(i,edgeServe1);
        }
        
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        List<userRequest> requestList= new ArrayList<>();
        List<task> taskList= new ArrayList<>();
        baseFunction baseFunction = new baseFunction();
        List<task> taskList_org = new ArrayList();
        List randomList = baseFunction.randomList(taskList,taskList_org,requestList,M,N,startTime,timeRange,0);
        taskList = (List<task>) randomList.get(0);

        List randCacheMemoryRecord = new ArrayList();
        List<memory> psoCacheMemoryRecord = new ArrayList();
        
        taskList_org = baseFunction.listCopy(taskList_org,taskList);

        expMec exp_mec = new expMec(randCacheMemoryRecord,psoCacheMemoryRecord,taskList,taskList_org,baseStationes, edgeServes, M, N,C, startTime, timeRange);
        for(int j = 0;j<requestNum;j++){
            exp_mec.toSimulation();
            if(psoCacheMemoryRecord.size()!=1){
                cacheUpdate cacheUpdate = new cacheUpdate(psoCacheMemoryRecord,taskList,requestList);
                cacheUpdate.toUpdate();
            }
        }
    }

}
class costModel {
    private double x1;
    private double y1;

    public costModel(double x1, double y1) {
        this.x1 = x1;
        this.y1 = y1;
    }

    public double costModel1(double T, double E){
        //System.out.println("this.x1：  "+this.x1+"this.y1:  "+this.y1);
        return T+E;
    }
}
