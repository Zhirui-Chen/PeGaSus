package Pegasus;

import java.util.ArrayList;

/**
 * 负责维护分组的类
 */

public class Group{
    private ArrayList<MetaData> metaDataList;//维护分组内的数据
    private boolean open;//维护该分组打开或关闭的状态变量

    public Group(){
        metaDataList = new ArrayList<>();
        open = false;
    }

    public ArrayList<MetaData> getMetaDataList() {
        return metaDataList;
    }

    public void open() {
        open = true;
    }

    public void close(){
        open = false;
    }

    public boolean isOpen() {
        return open;
    }
}