package cn.hnhczn.app.mvp.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by FClever on 2018/7/12.
 */
public class AndroidAppInfoBean extends ResultData<AndroidAppInfoBean> implements Serializable {
    @Expose
    private Integer id;
    // app名字
    @Expose
    private String appName;
    // 文件名
    @Expose
    private String fileName;
    // 服务器版本编码
    @Expose
    private Integer versionCode;
    // 服务器版本名称
    @Expose
    private String versionName;
    // 服务器标志
    @Expose
    private String serverFlag;
    // 强制升级
    @Expose
    private String lastForce;
    // app最新版本地址
    @Expose
    private String updateUrl;
    // 升级信息
    @Expose
    private String upgradeInfo;
    // 打包时间
    @Expose
    private String packagingTime;
    // 更新时间
    @Expose
    private String updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getServerFlag() {
        return serverFlag;
    }

    public void setServerFlag(String serverFlag) {
        this.serverFlag = serverFlag;
    }

    public String getLastForce() {
        return lastForce;
    }

    public void setLastForce(String lastForce) {
        this.lastForce = lastForce;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpgradeInfo() {
        return upgradeInfo;
    }

    public void setUpgradeInfo(String upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
    }

    public String getPackagingTime() {
        return packagingTime;
    }

    public void setPackagingTime(String packagingTime) {
        this.packagingTime = packagingTime;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "AndroidAppInfoBean{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", serverFlag='" + serverFlag + '\'' +
                ", lastForce='" + lastForce + '\'' +
                ", updateUrl='" + updateUrl + '\'' +
                ", upgradeInfo='" + upgradeInfo + '\'' +
                ", packagingTime='" + packagingTime + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
