package Pegasus;

import java.util.*;

public class Smoother {

    /**
     * 计算当前数据所在的当前分组的均值
     *
     * @param lastGroup 最后一个分组，也就是当前数据所在的当前分组
     * @return 最后一个分组数据的均值
     */
    public static double averageSmoother(ArrayList<Integer> estimatedStreamData, Group lastGroup) {
        double sum = 0.0;
        for (int index: lastGroup.getIndexList()) {
            sum += estimatedStreamData.get(index);
        }
        double avg = sum / lastGroup.getIndexList().size();
        return avg;
    }

    /**
     * 将当前数据更新为所在当前分组的中值
     *
     * @param lastGroup 最后一个分组，也就是当前数据所在的当前分组
     * @return 最后一个分组数据的中值
     */
//    public static double medianSmoother(ArrayList<MetaData> estimatedStreamData, Group lastGroup) {
//        ArrayList<Double> stateList = new ArrayList<>();
////        int startIndex = lastGroup.getMetaDataList().get(0).getTimeStamp();
//        for (MetaData metaData : lastGroup.getMetaDataList()) {
//            stateList.add(estimatedStreamData.get(metaData.getTimeStamp() - 1).getState());
//        }
//        Collections.sort(stateList);
//        double medianValue = stateList.get((stateList.size() - 1) / 2);
////        System.out.println("median value:"+medianValue);
//        return medianValue;
//    }

    /**
     * 计算一个比均值更接近原始值的结果
     *
     * @param lastGroup 最后一个分组
     * @return (Ct - avg)/|G| + avg （Ct：当前值，avg：均值，G：当前分组）
     */
//    public static double JSSmoother(ArrayList<MetaData> estimatedStreamData, Group lastGroup) {
//        double avg = averageSmoother(estimatedStreamData, lastGroup);
//        ArrayList<MetaData> metaDataList = lastGroup.getMetaDataList();
//        double noisyCount = estimatedStreamData.get(estimatedStreamData.size() - 1).getState();
//        double JSvalue = (noisyCount - avg) / metaDataList.size() + avg;
//        return JSvalue;
//    }

    /**
     * 滑动窗口求和平滑处理
     *
     * @param estimatedStreamData 加噪处理的流数据
     * @param currentGroupList    当前分组
     * @param windowSize          滑动窗口大小
     * @return 返回滑动窗口平滑处理后的值的和
     */
//      public static double windowSumSmoother(ArrayList<MetaData> estimatedStreamData, ArrayList<Group> currentGroupList, int windowSize) {
        //  System.out.println("currentGroupList size:"+currentGroupList.size());
//        double windowSum = 0.0;
//        ArrayList<MetaData> lastMetaDataList = currentGroupList.get(currentGroupList.size() - 1).getMetaDataList();
//        int windowEndTimeStamp = lastMetaDataList.get(lastMetaDataList.size() - 1).getTimeStamp();//窗口下端也当前时间戳
//        int windowStartTimeStamp = windowEndTimeStamp - windowSize + 1;
//
//        for (Group group : currentGroupList) {
//            ArrayList<MetaData> metaDataList = group.getMetaDataList();
//            int gStartTimeStamp = metaDataList.get(0).getTimeStamp();
//            int gEndTimeStamp = metaDataList.get(metaDataList.size() - 1).getTimeStamp();
//            Set<Integer> intersectSet = intersectPart(gStartTimeStamp, gEndTimeStamp, windowStartTimeStamp, windowEndTimeStamp);
//            if (!intersectSet.isEmpty()) {
//                double medianValue = medianSmoother(estimatedStreamData, group);
//                windowSum += medianValue * intersectSet.size();
//            }
//        }
//
//        return windowSum;
//    }

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
