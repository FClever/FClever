package cn.hnhczn.app.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;
import com.qmuiteam.qmui.widget.QMUIFontFitTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hnhczn.app.R;
import cn.hnhczn.app.app.utils.UpdateAppUtil;
import cn.hnhczn.app.commonres.dialog.ConfirmDialog;
import cn.hnhczn.app.commonres.dialog.ProgresDialog;
import cn.hnhczn.app.commonsdk.core.RouterHub;
import cn.hnhczn.app.commonsdk.utils.Utils;
import cn.hnhczn.app.commonservice.hnhczn.service.HnhcznInfoService;
import cn.hnhczn.app.di.component.DaggerMainComponent;
import cn.hnhczn.app.di.module.MainModule;
import cn.hnhczn.app.mvp.contract.MainContract;
import cn.hnhczn.app.mvp.presenter.MainPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @Autowired(name = RouterHub.HNHCZN_SERVICE_HNHCZNSERVICE)
    HnhcznInfoService mHnhcznInfoService;

    @BindView(R.id.bt)
    Button bt;
    @BindView(R.id.bt_2)
    Button bt2;
    @BindView(R.id.tv)
    QMUIFontFitTextView tv;

    private long mPressedTime;

    ConfirmDialog confirmDialog = null;

    private ProgresDialog progresDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        progresDialog = new ProgresDialog(this);
        ARouter.getInstance().inject(this);
        loadHnhcznInfo();
    }

    private void loadHnhcznInfo() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mHnhcznInfoService == null) {
            bt2.setEnabled(false);
            return;
        }
        bt2.setText(mHnhcznInfoService.getInfo().getName());
    }

    @Override
    public void onBackPressed() {
        if (confirmDialog == null) {
            confirmDialog = new ConfirmDialog(this)
                    .setTitle("是否退出？")
                    .setOnClickListener(isTrue -> {
                        if (isTrue) {
                            super.onBackPressed();
                        }
                    });
        }
        confirmDialog.show();

//        //获取第一次按键时间
//        long mNowTime = System.currentTimeMillis();
//        //比较两次按键时间差
//        if ((mNowTime - mPressedTime) > 2000) {
//            ArmsUtils.makeText(getApplicationContext(),
//                    "再按一次退出" + ArmsUtils.getString(getApplicationContext(), R.string.public_app_name));
//            mPressedTime = mNowTime;
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public void showLoading() {
        progresDialog.show();
    }

    @Override
    public void hideLoading() {
        progresDialog.hide();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.bt, R.id.bt_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt:
                bt.setEnabled(false);
                updateApp();
                break;
            case R.id.bt_2:
                Utils.navigation(MainActivity.this, RouterHub.HNHCZN_HOMEACTIVITY);
                break;
        }
    }

    public void updateApp() {
        UpdateAppUtil.request(MainActivity.this, (status) -> {
            tv.setText(status.getMsg());
            bt.setEnabled(true);
        });
    }
}
