package cn.hnhczn.app.hnhczn.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import cn.hnhczn.app.commonsdk.core.RouterHub;
import cn.hnhczn.app.hnhczn.di.component.DaggerHnhcznHomeComponent;
import cn.hnhczn.app.hnhczn.di.module.HnhcznHomeModule;
import cn.hnhczn.app.hnhczn.mvp.contract.HnhcznHomeContract;
import cn.hnhczn.app.hnhczn.mvp.presenter.HnhcznHomePresenter;

import cn.hnhczn.app.hnhczn.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;

@Route(path = RouterHub.HNHCZN_HOMEACTIVITY)
public class HnhcznHomeActivity extends BaseActivity<HnhcznHomePresenter> implements HnhcznHomeContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHnhcznHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .hnhcznHomeModule(new HnhcznHomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.hnhczn_activity_home; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}
