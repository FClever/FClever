package cn.hnhczn.app.commonsdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jess.arms.utils.DeviceUtils;

/**
 * Created by FClever on 2016/8/31.
 */

/**
 * 监听网络变化的广播接收器
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private static OnNetworkChangedListener networkChangedListener;


    /**
     * 接收到网路变化后的处理逻辑
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean status = false;//网络连接状态，true表示已连接，false表示未连接
        //判断是否连接网络
        status = DeviceUtils.hasInternet(context);
        //判断当前网络连接类型
        int type = DeviceUtils.getNetworkType(context);
        //网络变化可能多次接收到广播
        if (null != networkChangedListener) {
            networkChangedListener.onNetworkChanged(status, type);
        }

    }

    /**
     * 暴露回调接口给前台使用
     *
     * @param onNetworkChangedListener
     */
    public static void setOnNetworkChangedListener(OnNetworkChangedListener onNetworkChangedListener) {
        NetworkChangeReceiver.networkChangedListener = onNetworkChangedListener;
    }

    //回调接口
    public interface OnNetworkChangedListener {
        /**
         * @param isConnected 当前是否有网络连接
         * @param type        当前连接的类型
         */
        public void onNetworkChanged(boolean isConnected, int type);

    }

}

