package cn.hnhczn.app.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hnhczn.app.R;
import cn.hnhczn.app.app.CrashHandler;
import cn.hnhczn.app.commonsdk.cache.DisCacheHelper;
import cn.hnhczn.app.commonsdk.core.RouterHub;
import cn.hnhczn.app.mvp.model.entity.AppErrorInfo;


@Route(path = RouterHub.APP_ERRORACTIVITY)
public class ErrorActivity extends BaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_error; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        AppErrorInfo appErrorInfo = DisCacheHelper.get(CrashHandler.cacheKey, AppErrorInfo.class);
        if (appErrorInfo != null) {
            Integer time = appErrorInfo.getTime();
            if (time != null && time % 4 != 0) {
                restartApp();
            }
        } else {
            restartApp();
        }
        LogUtils.debugInfo(appErrorInfo.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_restart)
    public void onViewClicked(View view) {
        restartApp();
    }

    /**
     * 重启应用
     */
    private void restartApp() {
        ArmsUtils.makeText(ErrorActivity.this, "正在重启");
        Intent intent = new Intent(ErrorActivity.this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Process.killProcess(Process.myPid());//再此之前可以做些退出等操作
    }
}
