package Pegasus;

import java.util.ArrayList;
import static java.lang.Math.abs;

/**
 * PeGaSus算法的Grouper模块，对流数据进行分组
 */
public class Grouper {

    static double theta; //当前标准差的界限值
    static double prevTheta; //上一次分组标准差的界限值

    public static void setTheta(double theta) {
        Grouper.theta = theta;
    }

    public static void setPrevTheta(double prevTheta) {
        Grouper.prevTheta = prevTheta;
    }

    /**
     * Grouper类的核心办法
     * @param originCountsList 输入的原始流数据（属性值未加噪）
     * @param previousGroupList 当前已有的分组，未对当前数据分组
     * @param privacyBudget Grouper所消耗的隐私预算
     * @return  对当前数据分组后，返回现有的所有分组
     */
    public static ArrayList<Group> grouper(ArrayList<Integer> originCountsList,
                                           ArrayList<Group> previousGroupList,
                                           double privacyBudget) {
        ArrayList<Group> currentGroupList ;//返回值
        Group previousLastGroup = new Group();
        Group currentlastGroup = new Group();

        int lastCount = originCountsList.get(originCountsList.size()-1);
        currentlastGroup.getCountsList().add(lastCount);
        if(1==originCountsList.size()){  //当第一条数据到达时条件为true
            previousLastGroup.close() ;
        }else{
            previousLastGroup = previousGroupList.get(previousGroupList.size()-1);// G
        }
        if(!previousLastGroup.isOpen()){// Algorithm 2:line 6 if G has been closed
            previousGroupList.add(currentlastGroup);
            currentlastGroup.open();
            currentGroupList = previousGroupList;
            theta = theta + Laplace.sample(4/privacyBudget);
        }else{
            theta = prevTheta;
            previousLastGroup.getCountsList().add(lastCount);
            if(calDeviation(previousLastGroup) + Laplace.sample(8/privacyBudget)<theta){
                currentGroupList = previousGroupList;
                previousLastGroup.isOpen();
            }else{
                previousLastGroup.getCountsList().remove(previousLastGroup.getCountsList().size()-1);
                previousGroupList.add(currentlastGroup);
                currentlastGroup.close();
                previousLastGroup.close();
                currentGroupList = previousGroupList;
            }
        }
        prevTheta = theta;//更新标准差界限
        return currentGroupList;
    }

    /**
     * 计算分组方法当中需要的标准差
     * @param group 分组
     * @return
     */
    public static double calDeviation(Group group){
        //计算均值
        double sum =0.0;
        for(int metaData:group.getCountsList()){
            sum += metaData;
        }
        double avg = sum/group.getCountsList().size();
        //计算标准差
        double deviationValue = 0.0;
        for(int metaData:group.getCountsList()){
            deviationValue +=abs(metaData - avg);
        }
        return deviationValue;
    }

    public static void main(String[] args) {
        ArrayList<Integer> originCountsList = new ArrayList<>();

        originCountsList.add(5);
        originCountsList.add(5);
        originCountsList.add(6);
        originCountsList.add(9);
        originCountsList.add(10);

        ArrayList<Integer> inputoriginCountsList = new ArrayList<>();
        ArrayList<Group> partition = new ArrayList<>();
        Grouper.setTheta(2);
        Grouper.setPrevTheta(2);
        for(int count:originCountsList){
            inputoriginCountsList.add(count);
            partition = Grouper.grouper(inputoriginCountsList,partition,0.1);
        }
        for(Group group:partition){
            for(int count:group.getCountsList()){
                System.out.println(count);
            }
            System.out.println();
        }

    }
}
