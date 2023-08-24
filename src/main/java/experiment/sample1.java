package experiment;

import entity.task;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class sample1 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // write your code here
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        for(int i = 0 ;i<10;i++)
        {
            System.out.println(random.nextInt(2));
        }
        List list = new ArrayList();
        for(int i = 0;i<3;i++)
        {
            task task1 = new task();
            task1.setPriValue(i);
            System.out.println(task1.getPriValue());
            list.add(task1);
        }
        for(int i = 0;i<3;i++)
        {
            task task1 = (task) list.get(i);
            System.out.println(task1.getPriValue());
        }
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date();
        System.out.println(sdf.format(time.getTime())+"compare "+sdf.format(time.getTime()+86400000));
        System.out.println(sdf.format(time));
        System.out.println(sdf.format(System.currentTimeMillis()));
        float e = (float) -1.0E-25;
        System.out.println(e);
    }
//    public float calc(float Tlocal,float Tunca,float Tcach,int X){
//        float Tt = ((2-X)*(1-X)/2)*Tlocal+X*(2-X)*Tunca+(X*(X-1)/2)*Tcach;
//        return Tt;
//    }
}
