package cn.hnhczn.app.commonsdk.app;

import android.content.Context;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.tencent.smtt.sdk.QbSdk;

import cn.hnhczn.app.commonsdk.cache.DisCacheHelper;
import cn.hnhczn.app.commonsdk.utils.GsonUtil;

/**
 * Created by FClever on 2018/8/27.
 */
public class AppContext {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void init(Context context) {
        mContext = context;
        GsonUtil.init(ArmsUtils.obtainAppComponentFromContext(mContext).gson());//Gson工具类
        DisCacheHelper.init(context); //缓存帮助类
        initX5();//腾讯x5
    }

    /**
     * 初始化X5
     */
    private static void initX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.debugInfo("app initX5" + " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(mContext, cb);
    }
}
