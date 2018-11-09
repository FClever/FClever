package cn.hnhczn.app.app.utils;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.LogUtils;

import cn.hnhczn.app.commonsdk.http.Api;
import cn.hnhczn.app.commonsdk.utils.GsonUtil;
import cn.hnhczn.app.commonsdk.utils.SimpleCallback;
import cn.hnhczn.app.commonsdk.utils.StrUtil;
import cn.hnhczn.app.mvp.model.entity.AndroidAppInfoBean;

/**
 * Created by FClever on 2018/8/3.
 */

public class UpdateAppUtil {

    private static String url = Api.MAIN_DOMAIN + "/android/findLatestAndroidAppInfo";

    public enum Status {
        latest("已是最新版本"),
        cancelUpgrade("取消升级"),
        requestFail("网络请求失败"),
        other("未知错误");

        String msg;

        Status(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static String isForceUpdate = "1";

    public static void request(Context context, SimpleCallback<Status> callback) {
        try {
            AllenVersionChecker
                    .getInstance()
                    .requestVersion()
                    .setRequestUrl(url)
                    .request(new RequestVersionListener() {
                        @Nullable
                        @Override
                        public UIData onRequestVersionSuccess(String result) {
                            try {
                                LogUtils.debugInfo("线程：" + Thread.currentThread().getName());
                                LogUtils.debugInfo(result.toString());
                                AndroidAppInfoBean appInfoBean = GsonUtil.GsonToObject(result, AndroidAppInfoBean.class).getResult();
                                LogUtils.debugInfo(appInfoBean.toString());
                                if (!isUpdateApp(context, appInfoBean)) {
                                    if (callback != null)
                                        callback.action(Status.latest);
                                    return null;
                                }
                                show(appInfoBean, context, callback);
                            } catch (Exception e) {
                                LogUtils.debugInfo(e.toString());
                                if (callback != null)
                                    callback.action(Status.other);
                            }
                            return null;
                        }

                        @Override
                        public void onRequestVersionFailure(String message) {
                            LogUtils.debugInfo("获取最新版本信息失败");
                            if (callback != null)
                                callback.action(Status.requestFail);
                        }
                    })
                    .excuteMission(context);
        } catch (Exception e) {
            if (callback != null)
                callback.action(Status.other);
        }
    }

    /**
     * 验证是否需要升级
     *
     * @param androidAppInfoBean
     * @return
     */
    public static boolean isUpdateApp(Context context, AndroidAppInfoBean androidAppInfoBean) {
        Boolean isUpgrade = false;
        try {
            if (androidAppInfoBean != null) {
                Integer versionCode = androidAppInfoBean.getVersionCode();
                if (versionCode != null) {
                    Integer oldVersionCode = DeviceUtils.getVersionCode(context);
                    if ((oldVersionCode != null && oldVersionCode < versionCode)) {
                        isUpgrade = true;
                    }
                } else {
                    LogUtils.debugInfo("版本编码不正确");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpgrade;
    }

    /**
     * 升级App弹窗配置
     *
     * @param androidAppInfoBean
     */
    public static void show(AndroidAppInfoBean androidAppInfoBean, Context context, SimpleCallback<Status> callback) throws Exception {
        if (Looper.myLooper() != Looper.getMainLooper()) { // UI主线程
            LogUtils.debugInfo("请在UI主线程内操作App升级");
            throw new Exception("DownloadBuilder");
        }
        String url = "";
        String info = "";
        String lastForce = "";
        String newVersionName = "未知";
        if (androidAppInfoBean != null) {
            url = StrUtil.toString(androidAppInfoBean.getUpdateUrl());
            info = StrUtil.toString(androidAppInfoBean.getUpgradeInfo());
            lastForce = StrUtil.toString(androidAppInfoBean.getLastForce());
            newVersionName = StrUtil.toString(androidAppInfoBean.getVersionName(), "未知");
        }
        String oldVersionName = StrUtil.toString(DeviceUtils.getVersionName(context));
        DownloadBuilder builder = AllenVersionChecker
                .getInstance()
                .downloadOnly(UIData
                        .create()
                        .setDownloadUrl(url)
                        .setTitle("系统升级")
                        .setContent("当前版本：" + oldVersionName + "\n最新版本：" + newVersionName + "\n\n" + info)
                )
                .setForceRedownload(true)
                .setOnCancelListener(() -> {
                    if (callback != null)
                        callback.action(Status.cancelUpgrade);
                });
        if (isForceUpdate.equals(lastForce)) {
            builder.setForceUpdateListener(() -> {

            });
        }
        builder.excuteMission(context);
    }
}
