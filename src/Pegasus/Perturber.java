package Pegasus;

import org.apache.commons.math3.distribution.LaplaceDistribution;

/**
 * PeGaSus算法的Perturber模块，对原始数据加噪处理。
 */
public class Perturber {
    /**
     *对输入的一个原始值添加噪声，返回加噪结果
     * @param originValue
     * @param privateBudget
     * @return 返回加了拉普拉斯噪声的结果
     */
    public static double perturber(double originValue,double privateBudget){
        double noise = Laplace.sample(privateBudget);
        return originValue+noise;
    }


    public static void main(String[] args) {
        double originValued = 10.0;
        double privateBudgetd = 0.5;
        double resultd =  Perturber.perturber(originValued,privateBudgetd);
        System.out.println(resultd);
    }
}
