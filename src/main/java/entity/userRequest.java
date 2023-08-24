package entity;

import java.util.Date;

public class userRequest {
    
    private String Name;
    public String getName(){
        return this.Name;
    }
    public void setName(String name){
        this.Name = name;
    }

    
    private Date birthTime;
    public Date getBirthTime(){
        return this.birthTime;
    }
    public void setBirthTime(Date birthtime){
        this.birthTime = birthtime;
    }

    
    private Date finishTime;
    public Date getFinishTime(){
        return this.finishTime;
    }
    public void setFinishTime(Date finishTime){
        this.finishTime = finishTime;
    }

   
    private userDevice userDevice;
    public userDevice getUserDevice(){
        return this.userDevice;
    }
    public void setUserDevice(userDevice userDevice){
        this.userDevice = userDevice;
    }
 
    private task Task;
    public task getTask(){
        return this.Task;
    }
    public void setTask(task Task){this.Task = Task;}
  
    private int isLoacl;
    public int getIsLocal(){
        return this.isLoacl;
    }
    public void setIsLoacl(int isLoacl){this.isLoacl = isLoacl;}

    private double T;
    public double getT(){
        return this.T;
    }
    public void setT(double t){this.T = t;}
    
    private double E;
    public double getE(){
        return this.E;
    }
    public void setE(double e){this.E = e;}




}