package Pegasus;

/**
 * 定义元数据，属性为论文提到的用户id、时间戳、状态三个属性
 */
public class MetaData{
    private int userId;
    private int timeStamp;
    private double state;

    public MetaData(int u, int t, double s){
        userId = u;
        timeStamp = t;
        state = s;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setState(double state) {
        this.state = state;
    }

    public int getUserId() {
        return userId;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public double getState() {
        return state;
    }

    public void print(){
        System.out.println("userId:"+userId
                +";timestamp:"+timeStamp+";state:"+state);
    }
}
