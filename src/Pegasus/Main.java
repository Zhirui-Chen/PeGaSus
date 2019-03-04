package Pegasus;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {

    public static void print(ArrayList<Double> countsList){
        for(double counts:countsList){
            System.out.print(counts+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {

        //初始化了5条数据，属性值依次为5，5，6，9，10
        ArrayList<Integer> originCountsList = new ArrayList<>();
        originCountsList.add(5);
        originCountsList.add(5);
        originCountsList.add(6);
        originCountsList.add(9);
        originCountsList.add(10);

        ArrayList<Double> originStreamData = new ArrayList<>();//原始流数据
        ArrayList<Double> estimatedStreamData = new ArrayList<>();//初步加了噪声的流数据
        ArrayList<Group> groupList = new ArrayList<>();//存储分组
        ArrayList<Double> avgSmoothStreamData = new ArrayList<>();//平滑处理的流数据
        ArrayList<Double> medianSmoothStreamData = new ArrayList<>();
        ArrayList<Double> jsSmoothStreamData = new ArrayList<>();
        ArrayList<Double> windowSmoothStreamData = new ArrayList<>();

        double estimatedData;//估计数据
        double avgSmoothData;//流数据
        double medianSmoothData;
        double jsSmoothData;
        double windowSmoothData;

        //参数设定
        Grouper.setTheta(2);
        Grouper.setPrevTheta(2);
        double prevateBudget_p = 1;
        double privateBudget_g = 1;
        //计算过程中的数据格式
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.print("原值为：");
        for(int counts:originCountsList){
            System.out.print(counts+" ");
        }
        System.out.println();
        System.out.print("加噪音结果为：");

        for(double metaD:originCountsList){//对原始数据进行处理，for循环模拟流数据
            //对到达的流数据进行备份和加噪
            originStreamData.add(metaD);
            estimatedData = metaD;
            double perturberResult = Perturber.perturber(metaD,prevateBudget_p);
            estimatedData = Double.valueOf(df.format(perturberResult));
            estimatedStreamData.add(estimatedData);
            System.out.print(estimatedData+" ");
            //对数据进行分组
            groupList = Grouper.grouper(originStreamData, groupList,privateBudget_g);
            //对数据平滑处理，推理出最终要发布的值
            double avgSmoothValue = Smoother.averageSmoother(estimatedStreamData,groupList.get(groupList.size()-1));
            double medianSmoothValue = Smoother.medianSmoother(estimatedStreamData,groupList.get(groupList.size()-1));
            double jsSmoothValue = Smoother.JSSmoother(estimatedStreamData,groupList.get(groupList.size()-1));
            double windowSmoothValue = Smoother.windowSumSmoother(estimatedStreamData,groupList,3);

            avgSmoothData = Double.valueOf(df.format(avgSmoothValue));
            medianSmoothData = Double.valueOf(df.format(medianSmoothValue));
            jsSmoothData = Double.valueOf(df.format(jsSmoothValue));
            windowSmoothData = Double.valueOf(df.format(windowSmoothValue));

            avgSmoothStreamData.add(avgSmoothData);
            medianSmoothStreamData.add(medianSmoothData);
            jsSmoothStreamData.add(jsSmoothData);
            windowSmoothStreamData.add(windowSmoothData);
        }

        System.out.println("\n分组数"+groupList.size());
        System.out.println("\n划分为：");
        for(Group group:groupList){
            for(int m :group.getIndexList()){
                System.out.print(m+" ");
            }
            System.out.println();
        }

        System.out.print("\n均值平滑处理结果为：    ");
        print(avgSmoothStreamData);

        System.out.print("中值平滑处理结果为：    ");
        print(medianSmoothStreamData);

        System.out.print("JS值平滑处理结果为：    ");
        print(jsSmoothStreamData);

        System.out.print("滑动窗口平滑处理结果为：");
        print(windowSmoothStreamData);


    }
}