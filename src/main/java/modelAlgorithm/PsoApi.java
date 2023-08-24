package modelAlgorithm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PsoApi {

    private int particleNum;

    private double c1;

    private double c2;

    private double vMax;

    private double w;

    private int genMax;

    public PsoApi(int particleNum, double c1, double c2, double vMax, double w, int genMax) {
        this.particleNum = particleNum;
        this.c1 = c1;
        this.c2 = c2;
        this.vMax = vMax;
        this.w = w;
        this.genMax = genMax;
    }

    public List solve(double T, double E) {

        Particle[] particleArr;

        double gBest = Double.MAX_VALUE;

        double bestX = 0;
        double bestY = 0;

        Random random = new Random();
        long start = System.currentTimeMillis();

        double[][][] positionArr = new double[this.genMax][this.particleNum][2];

        particleArr = new Particle[this.particleNum];
        for (int i = 0; i < particleArr.length; i++) {

            particleArr[i] = new Particle(0, 1, 0, 1, 0.01, 0.01, random, T, E);

            particleArr[i].setBestX(particleArr[i].getX());
            particleArr[i].setBestY(particleArr[i].getY());
            double pValue = this.objectFunction(particleArr[i].getX(), particleArr[i].getY(), T, E);

            if (pValue < gBest) {
                bestX = particleArr[i].getX();
                bestY = particleArr[i].getY();
                gBest = pValue;
            }
        }

        for (int i = 0; i < this.genMax; i++) {

            for (int j = 0; j < this.particleNum; j++) {

                while (true) {
                    particleArr[j].setxV(this.w * particleArr[j].getxV()
                            + this.c1 * (random.nextDouble() * (particleArr[j].getBestX() - particleArr[j].getX()))
                            + this.c2 * (random.nextDouble() * (bestX - particleArr[j].getX())));

                    if (particleArr[j].getxV() > this.vMax) {
                        particleArr[j].setxV(this.vMax);
                    } else if (particleArr[j].getxV() < -this.vMax) {
                        particleArr[j].setxV(-this.vMax);
                    }

                    particleArr[j].setyV(this.w * particleArr[j].getyV()
                            + this.c1 * (random.nextDouble() * (particleArr[j].getBestY() - particleArr[j].getY()))
                            + this.c2 * (random.nextDouble() * (bestY - particleArr[j].getY())));

                    if (particleArr[j].getyV() > this.vMax) {
                        particleArr[j].setyV(this.vMax);
                    } else if (particleArr[j].getyV() < -this.vMax) {
                        particleArr[j].setyV(-this.vMax);
                    }

                    double nextX = saveTwo(particleArr[j].getX() + particleArr[j].getxV());

                    if (nextX > particleArr[j].getxMax()) {
                        nextX = particleArr[j].getxMax();
                    } else if (nextX < particleArr[j].getxMin()) {
                        nextX = particleArr[j].getxMin();
                    }

                    double nextY = saveTwo(particleArr[j].getY() + particleArr[j].getyV());

                    if (nextY > particleArr[j].getyMax()) {
                        nextY = particleArr[j].getyMax();
                    } else if (nextY < particleArr[j].getyMin()) {
                        nextY = particleArr[j].getyMin();
                    }

                    if (nextY * nextX != 0) {
                        particleArr[j].setX(nextX);
                        particleArr[j].setY(nextY);

                        break;
                    }

                }

                double pValue = this.objectFunction(particleArr[j].getX(), particleArr[j].getY(), T, E);

                if (pValue < particleArr[j].getpBest()) {
                    particleArr[j].setBestX(particleArr[j].getX());
                    particleArr[j].setBestY(particleArr[j].getY());
                    particleArr[j].setpBest(pValue);
                }

                if (pValue < gBest) {
                    bestX = particleArr[j].getX();
                    bestY = particleArr[j].getY();
                    gBest = pValue;
                }

                positionArr[i][j][0] = particleArr[j].getX();
                positionArr[i][j][1] = particleArr[j].getY();
            }
        }

        List weight = new ArrayList();
        weight.add(0, bestX);
        weight.add(1, bestY);

        return weight;
    }

    public double saveTwo(double one) {
        DecimalFormat format = new DecimalFormat("#.000");
        String str = format.format(one);
        double four = Double.parseDouble(str);
        return four;
    }

    /**
     * object
     *
     * @param x
     * @param y
     * @return
     */
    private double objectFunction(double x, double y, double T, double E) {
        int Emax = 10;

        return x * T + y * E;
    }

    class Particle {
        private double x;
        private double y;

        private double xMin;
        private double xMax;
        private double yMin;
        private double yMax;

        private double xV;

        private double yV;

        private double pBest;

        private double bestX;
        private double bestY;

        public Particle(double xMin, double xMax, double yMin, double yMax, double xV, double yV, Random random,
                double T, double E) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
            this.xV = xV;
            this.yV = yV;

            this.initParticle(random, T, E);
        }

        public void initParticle(Random random, double T, double E) {

            this.pBest = (T + E) / 2;
            while (true) {
                this.x = saveTwo(random.nextDouble() * (this.xMax - this.xMin) + this.xMin);
                this.y = saveTwo(random.nextDouble() * (this.yMax - this.yMin) + this.yMin);

                if (this.x + this.y == 1) {

                    break;

                }
            }

        }

        public double saveTwo(double one) {
            DecimalFormat format = new DecimalFormat("#.000");
            String str = format.format(one);
            double four = Double.parseDouble(str);
            return four;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getxMin() {
            return xMin;
        }

        public void setxMin(double xMin) {
            this.xMin = xMin;
        }

        public double getxMax() {
            return xMax;
        }

        public void setxMax(double xMax) {
            this.xMax = xMax;
        }

        public double getyMin() {
            return yMin;
        }

        public void setyMin(double yMin) {
            this.yMin = yMin;
        }

        public double getyMax() {
            return yMax;
        }

        public void setyMax(double yMax) {
            this.yMax = yMax;
        }

        public double getxV() {
            return xV;
        }

        public void setxV(double xV) {
            this.xV = xV;
        }

        public double getyV() {
            return yV;
        }

        public void setyV(double yV) {
            this.yV = yV;
        }

        public double getpBest() {
            return pBest;
        }

        public void setpBest(double pBest) {
            this.pBest = pBest;
        }

        public double getBestX() {
            return bestX;
        }

        public void setBestX(double bestX) {
            this.bestX = bestX;
        }

        public double getBestY() {
            return bestY;
        }

        public void setBestY(double bestY) {
            this.bestY = bestY;
        }

    }

}
