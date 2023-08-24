package modelAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class PsoMainRun {
    public static void main(String[] args) {
        PsoApi psoApi = new PsoApi(1000, 0.001, 0.001, 2, 0.9, 500);
        double E = 70.1;
        double T = 62.2;
        psoApi.solve(T,E);
    
    }
    public List toPSO(int particleNum, double c1, double c2, double vMax, double w, int genMax, double E, double T){
        List weight = new ArrayList();
        PsoApi psoApi = new PsoApi(particleNum, c1, c2, vMax, w, genMax);
        weight = psoApi.solve(T,E);
        return weight;

    }
}
