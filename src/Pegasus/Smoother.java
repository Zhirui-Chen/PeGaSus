package Pegasus;

import java.util.*;

public class Smoother {

    /**
     * 计算当前数据所在的当前分组的均值
     * @param lastGroup 最后一个分组，也就是当前数据所在的当前分组
     * @return 最后一个分组数据的均值
     */
    public static double averageSmoother(ArrayList<Double> estimatedStreamData, Group lastGroup) {
        double sum = 0.0;
        for (int index: lastGroup.getIndexList()) {
            sum += estimatedStreamData.get(index);
        }
        double avg = sum / lastGroup.getIndexList().size();
        return avg;
    }

    /**
     * 将当前数据更新为所在当前分组的中值
     * @param lastGroup 最后一个分组，也就是当前数据所在的当前分组
     * @return 最后一个分组数据的中值
     */
    public static double medianSmoother(ArrayList<Double> estimatedStreamData, Group lastGroup) {
        ArrayList<Integer> indexList = lastGroup.getIndexList();
        ArrayList<Double> countsList = new ArrayList<>();
        int startIndex = indexList.get(0);
        int endIndex = indexList.get(indexList.size()-1);
        for (int i = startIndex; i <= endIndex ; i++) {
            countsList.add(estimatedStreamData.get(i));
        }
        Collections.sort(countsList);
        double medianValue = countsList.get((countsList.size()-1)/2);
        return medianValue;
    }

    /**
     * 计算一个比均值更接近噪声值的结果
     *
     * @param lastGroup 最后一个分组
     * @return (Ct - avg)/|G| + avg （Ct：当前值，avg：均值，G：当前分组）
     */
    public static double JSSmoother(ArrayList<Double> estimatedStreamData, Group lastGroup) {
        double avg = averageSmoother(estimatedStreamData, lastGroup);
        ArrayList<Integer> indexList = lastGroup.getIndexList();
        double lastEstimatedData = estimatedStreamData.get(estimatedStreamData.size() - 1);
        double JSvalue = (lastEstimatedData - avg) / indexList.size() + avg;
        return JSvalue;
    }

    /**
     * 滑动窗口求和平滑处理
     * @param estimatedStreamData 加噪处理的流数据
     * @param currentGroupList    当前分组
     * @param windowSize          滑动窗口大小
     * @return 返回滑动窗口平滑处理后的值的和
     */
      public static double windowSumSmoother(ArrayList<Double> estimatedStreamData, ArrayList<Group> currentGroupList, int windowSize) {
       // System.out.println("currentGroupList size:"+currentGroupList.size());
        double windowSum = 0.0;
        ArrayList<Integer> lastIndexList = currentGroupList.get(currentGroupList.size() - 1).getIndexList();
        int windowEndTimeStamp = lastIndexList.get(lastIndexList.size()-1);//窗口下端也当前时间戳
        int windowStartTimeStamp = windowEndTimeStamp - windowSize + 1;

        for (Group group : currentGroupList) {
            ArrayList<Integer> indexList = group.getIndexList();
            int gStartTimeStamp = indexList.get(0);
            int gEndTimeStamp = indexList.get(indexList.size()-1);
            Set<Integer> intersectSet = intersectPart(gStartTimeStamp, gEndTimeStamp, windowStartTimeStamp, windowEndTimeStamp);
            if (!intersectSet.isEmpty()) {
                double medianValue = medianSmoother(estimatedStreamData, group);
                windowSum += medianValue * intersectSet.size();
            }
        }

        return windowSum;
    }

    /**
     * 计算闭区间[x,y]和[a,b]的整数交集
     *
     * @param x
     * @param y
     * @param a
     * @param b
     * @return 交集，为空则不相交
     */

    public static Set<Integer> intersectPart(int x, int y, int a, int b) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int i = x; i <= y; i++) {
            set1.add(i);
        }
        for (int i = a; i <= b; i++) {
            set2.add(i);
        }
        set1.retainAll(set2);
        return set1;
    }
}
