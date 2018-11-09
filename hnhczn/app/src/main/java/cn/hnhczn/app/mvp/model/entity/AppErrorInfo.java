package cn.hnhczn.app.mvp.model.entity;

/**
 * Created by FClever on 2018/8/24.
 */
public class AppErrorInfo {

    private Long lastDate = System.currentTimeMillis();//最后报错时间

    private Integer time = 0;//错误次数

    private String lastError = "";//最后报错异常

    public Long getLastDate() {
        return lastDate;
    }

    public void setLastDate(Long lastDate) {
        this.lastDate = lastDate;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    @Override
    public String toString() {
        return "AppErrorInfo{" +
                "lastDate=" + lastDate +
                ", time=" + time +
                ", lastError='" + lastError + '\'' +
                '}';
    }
}
