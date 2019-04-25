package Pegasus;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Main {

    public static void writeFile(ArrayList<Double> data, String pathName) throws IOException {
        File fout = new File(pathName);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (int i = 0; i < data.size(); i++) {
            bw.write(String.valueOf(data.get(i)));
            bw.newLine();
        }
        bw.close();
    }

    public static void print(ArrayList<Double> countsList){
        for(double counts:countsList){
            System.out.print(counts+" ");
        }
        System.out.println();
    }

    public static double L1error(ArrayList<Double> origin, ArrayList<Double> now){
        double sum=0;
        for (int i = 0; i <origin.size() ; i++) {
            sum += abs(origin.get(i)-now.get(i));
        }
        return sum/origin.size();
    }

    public static void main(String[] args) throws FileNotFoundException {

        //初始化了5条数据，属性值依次为5，5，6，9，10
        ArrayList<Integer> originCountsList = new ArrayList<>();
        String file="./data/counts.dat";
        try(FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader)
        ){
            String line;
            while((line = br.readLine())!=null){
                originCountsList.add(Integer.valueOf(line));
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println();

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
        Grouper.setTheta(80);
        Grouper.setPrevTheta(80);
        double privateBudget_p = 0.04;
        double privateBudget_g = 0.01;
        //计算过程中的数据格式
        DecimalFormat df = new DecimalFormat("#0.00");
       // System.out.print("原值为：");
//        for(int counts:originCountsList){
//            System.out.print(counts+" ");
//        }
//        System.out.println();
     //   System.out.print("加噪音结果为：");

        for(double metaD:originCountsList){//对原始数据进行处理，for循环模拟流数据
            //对到达的流数据进行备份和加噪
            originStreamData.add(metaD);
//            estimatedData = metaD;
            double perturberResult = Perturber.perturber(metaD,privateBudget_p);
            estimatedData = Double.valueOf(df.format(perturberResult));
            estimatedStreamData.add(estimatedData);
          //  System.out.print(estimatedData+" ");
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
        try{
            writeFile(estimatedStreamData,"data/estimatedStreamData.dat");
            writeFile(avgSmoothStreamData,"data/avgSmoothStreamData.dat");
            writeFile(medianSmoothStreamData,"data/medianSmoothStreamData.dat");
            writeFile(jsSmoothStreamData,"data/jsSmoothStreamData.dat");
            writeFile(windowSmoothStreamData,"data/windowSmoothStreamData.dat");
        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("\n分组数"+groupList.size());
//        System.out.println("\n划分为：");
//        for(Group group:groupList){
//            for(int m :group.getIndexList()){
//                System.out.print(m+" ");
//            }
//            System.out.println();
//        }

        System.out.println("LML1误差");
        System.out.println(L1error(originStreamData,estimatedStreamData));
//        System.out.print("\n均值平滑处理结果为：    ");
//        print(avgSmoothStreamData);
        System.out.println("均值L1误差");
        System.out.println(L1error(originStreamData,avgSmoothStreamData));

//        System.out.print("中值平滑处理结果为：    ");
//        print(medianSmoothStreamData);
        System.out.println("中值L1误差");
        System.out.println(L1error(originStreamData,medianSmoothStreamData));

//        System.out.print("JS值平滑处理结果为：    ");
//        print(jsSmoothStreamData);
        System.out.println("JSL1误差");
        System.out.println(L1error(originStreamData,jsSmoothStreamData));

//        System.out.print("滑动窗口平滑处理结果为：");
//        print(windowSmoothStreamData);
        System.out.println("WSSL1误差");
        System.out.println(L1error(originStreamData,windowSmoothStreamData));

    }
}