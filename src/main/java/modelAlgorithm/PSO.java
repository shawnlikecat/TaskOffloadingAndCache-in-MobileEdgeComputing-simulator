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

public class PSO {
    public List psoCacheMemoryRecord = new ArrayList();
    int numParticles; 
    int decisionSpaceSize; 
    PsoMec[] particles; 
    public double[] gBest; 

    public double[] cgBest; 
    public double gBestFitness; 
    List<userRequest> requestList = new ArrayList<>();
    List<baseStation> baseStationes;
    List<edgeServe> edgeServes;
    List<task> taskList;
    public List psoCacheMemory;
    double C;

    public PSO(double C,List psoCacheMemory,List psoCacheMemoryRecord,List<baseStation> baseStationes, List<edgeServe> edgeServes, List<task> taskList, List<userRequest> requestList, Random rand, int numParticles, int decisionSpaceSize,int countCache) throws InvocationTargetException, IllegalAccessException {
        this.requestList = requestList;
        this.C = C;
        this.psoCacheMemoryRecord = psoCacheMemoryRecord;
        this.psoCacheMemory = psoCacheMemory;
        this.baseStationes = baseStationes;
        this.edgeServes = edgeServes;
        this.taskList = taskList;
        this.numParticles = numParticles;
        this.decisionSpaceSize = decisionSpaceSize;
        particles = new PsoMec[numParticles];
        gBest = new double[decisionSpaceSize];
        cgBest = new double[taskList.size()];
        gBestFitness = Double.MAX_VALUE; 

        
        for (int i = 0; i < numParticles; i++) {
            particles[i] = new PsoMec(C,psoCacheMemory,taskList,rand, decisionSpaceSize,countCache);
        }
    }

    
    public void optimize(int maxIterations, double[] w, double[] c1, double[] c2) throws InvocationTargetException, IllegalAccessException {
        Random rand = new Random();

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            
            for (PsoMec particle : particles) {
                double fitness = particle.calculateFitness(baseStationes, edgeServes,requestList,decisionSpaceSize);
                if (fitness < gBestFitness) {
                    gBestFitness = fitness;
                    System.arraycopy(particle.pBest, 0, gBest, 0, gBest.length);
                    System.arraycopy(particle.cBest, 0, cgBest, 0, cgBest.length);
                }
            }

            
            for (PsoMec particle : particles) {
                particle.update(rand, gBest, w, c1, c2);
            }
        }
        psoCacheMemoryRecord.add(cgBest);
    }

    public static void main(String[] args) {
        Random rand = new Random();
        int numParticles = 5000;
        int decisionSpaceSize = 30; 
    }
}
