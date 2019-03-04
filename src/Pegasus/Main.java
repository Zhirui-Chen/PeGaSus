package Pegasus;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {

    public static void print(ArrayList<MetaData> metaDataArrayList){
        for(MetaData metaData:metaDataArrayList){
            System.out.print(metaData.getState()+" ");
        }
        System.out.println();
    }
//    public static void main(String[] args) {
//
//        //初始化了5条数据，属性值依次为5，5，6，9，10
//        ArrayList<MetaData> metaDataList = new ArrayList<>();
//        MetaData metaData = new MetaData(1,1,5);
//        metaDataList.add(metaData);
//        metaData = new MetaData(2,2,5);
//        metaDataList.add(metaData);
//        metaData = new MetaData(3,3,6);
//        metaDataList.add(metaData);
//        metaData = new MetaData(4,4,9);
//        metaDataList.add(metaData);
//        metaData = new MetaData(5,5,10);
//        metaDataList.add(metaData);
//
//        ArrayList<MetaData> originStreamData = new ArrayList<>();//原始流数据
//        ArrayList<MetaData> estimatedStreamData = new ArrayList<>();//初步加了噪声的流数据
//        ArrayList<Group> groupList = new ArrayList<>();//存储分组
//        ArrayList<MetaData> avgSmoothStreamData = new ArrayList<>();//平滑处理的流数据
//        ArrayList<MetaData> medianSmoothStreamData = new ArrayList<>();
//        ArrayList<MetaData> jsSmoothStreamData = new ArrayList<>();
//        ArrayList<MetaData> windowSmoothStreamData = new ArrayList<>();
//
//        MetaData estimatedData;//估计数据
//        MetaData avgSmoothData;//流数据
//        MetaData medianSmoothData;
//        MetaData jsSmoothData;
//        MetaData windowSmoothData;
//
//        //参数设定
//        Grouper.setTheta(2);
//        Grouper.setPrevTheta(2);
//        double prevateBudget_p = 1;
//        double privateBudget_g = 1;
//        //计算过程中的数据格式
//        DecimalFormat df = new DecimalFormat("#0.00");
//        System.out.print("原值为：");
//        for(MetaData m:metaDataList){
//            System.out.print(m.getState()+" ");
//        }
//        System.out.println();
//        System.out.print("加噪音结果为：");
//
//        for(MetaData metaD:metaDataList){//对原始数据进行处理，for循环模拟流数据
//            //对到达的流数据进行备份和加噪
//            originStreamData.add(metaD);
//            estimatedData = MetaData.copy(metaD);
//            double perturberResult = Perturber.perturber(metaD.getState(),prevateBudget_p);
//            estimatedData.setState(Double.valueOf(df.format(perturberResult)));
//            estimatedStreamData.add(estimatedData);
//            System.out.print(estimatedData.getState()+" ");
//            //对数据进行分组
//            groupList = Grouper.grouper(originStreamData, groupList,privateBudget_g);
//            //对数据平滑处理，推理出最终要发布的值
//            double avgSmoothValue = Smoother.averageSmoother(estimatedStreamData,groupList.get(groupList.size()-1));
//            double medianSmoothValue = Smoother.medianSmoother(estimatedStreamData,groupList.get(groupList.size()-1));
//            double jsSmoothValue = Smoother.JSSmoother(estimatedStreamData,groupList.get(groupList.size()-1));
//            double windowSmoothValue = Smoother.windowSumSmoother(estimatedStreamData,groupList,3);
//            avgSmoothData = MetaData.copy(metaD);
//            medianSmoothData = MetaData.copy(metaD);
//            jsSmoothData = MetaData.copy(metaD);
//            windowSmoothData = MetaData.copy(metaD);
//
//            avgSmoothData.setState(Double.valueOf(df.format(avgSmoothValue)));
//            medianSmoothData.setState(Double.valueOf(df.format(medianSmoothValue)));
//            jsSmoothData.setState(Double.valueOf(df.format(jsSmoothValue)));
//            windowSmoothData.setState(Double.valueOf(df.format(windowSmoothValue)));
//
//            avgSmoothStreamData.add(avgSmoothData);
//            medianSmoothStreamData.add(medianSmoothData);
//            jsSmoothStreamData.add(jsSmoothData);
//            windowSmoothStreamData.add(windowSmoothData);
//        }
//
//        System.out.println("\n分组数"+groupList.size());
//        System.out.println("\n划分为：");
//        for(Group group:groupList){
//            for(MetaData m :group.getMetaDataList()){
//                System.out.print(m.getTimeStamp()+" ");
//            }
//            System.out.println();
//        }
//
//        System.out.print("\n均值平滑处理结果为：    ");
//        print(avgSmoothStreamData);
//        System.out.print("\n中值平滑处理结果为：    ");
//        print(medianSmoothStreamData);
//        System.out.print("\nJS值平滑处理结果为：    ");
//        print(jsSmoothStreamData);
//        System.out.print("\n滑动窗口平滑处理结果为：");
//        print(windowSmoothStreamData);
//
//
//    }
}