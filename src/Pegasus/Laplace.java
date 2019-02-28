package Pegasus;

import org.apache.commons.math3.distribution.LaplaceDistribution;

/**
 * 生成拉普拉斯噪声值
 */
public class Laplace {

    public static double sample(double epsilon){
        LaplaceDistribution lap = new LaplaceDistribution(0,1/epsilon);
        return lap.sample();
    }

}
