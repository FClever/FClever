package cn.hnhczn.app.app;

import android.content.Context;
import android.content.Intent;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import cn.hnhczn.app.commonsdk.BuildConfig;
import cn.hnhczn.app.commonsdk.cache.DisCacheHelper;
import cn.hnhczn.app.commonsdk.utils.Kits;
import cn.hnhczn.app.mvp.model.entity.AppErrorInfo;
import cn.hnhczn.app.mvp.ui.activity.ErrorActivity;
import cn.hnhczn.app.mvp.ui.activity.WelcomeActivity;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static String cacheKey = "CrashHandler";

    private Context context;

    public CrashHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            if (BuildConfig.LOG_DEBUG)
                putThrowable(e);
        } catch (Exception ex) {
            e.printStackTrace();
        }
    }

    /**
     * @param throwable
     */
    public void putThrowable(Throwable throwable) {
        AppErrorInfo appErrorInfo = DisCacheHelper.get(cacheKey, AppErrorInfo.class);
        if (appErrorInfo == null) {
            appErrorInfo = new AppErrorInfo();
        } else {
            appErrorInfo.setLastDate(System.currentTimeMillis());
            appErrorInfo.setTime(appErrorInfo.getTime() + 1);
        }
        appErrorInfo.setLastError(getThrowable(throwable));
        DisCacheHelper.put(cacheKey, appErrorInfo);
        saveFile(appErrorInfo);
    }

    /**
     * @param throwable
     * @return
     */
    public String getThrowable(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }

    /**
     * 保存文件
     **/
    public void saveFile(AppErrorInfo appErrorInfo) {
        new Thread(() -> {
            String str = appErrorInfo.toString();
            if (!Kits.Empty.check(str)) {
                File file = new File("/mnt/sdcard/hnhczn/log");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File mFile = new File("/mnt/sdcard/hnhczn/log/log-" + context.getApplicationInfo().processName + ".js");
                try {
                    //创建文件
                    mFile.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(mFile.getPath());
                    byte[] bytes = str.getBytes();
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            goErrorActivity();
        }).start();
    }

    /**
     * 跳转错误处理页面
     **/
    private void goErrorActivity() {
        Intent intent = new Intent(context, ErrorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());//再此之前可以做些退出等操作
    }

    /**
     * 重启应用
     */
    private void restartApp() {
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());//再此之前可以做些退出等操作
    }


}
