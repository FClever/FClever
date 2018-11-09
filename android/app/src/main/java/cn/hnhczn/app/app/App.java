package cn.hnhczn.app.app;

import android.content.Context;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.utils.LogUtils;
import com.tencent.smtt.sdk.QbSdk;

import cn.hnhczn.app.commonsdk.app.AppContext;
import cn.hnhczn.app.commonsdk.cache.DisCacheHelper;
import cn.hnhczn.app.commonsdk.utils.GsonUtil;


/**
 * Created by FClever on 2018/8/3.
 */
public class App extends BaseApplication {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.debugInfo("App onCreate()");

        context = this;

        AppContext.init(context);

        //app异常处理注册
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(context));

    }


}
