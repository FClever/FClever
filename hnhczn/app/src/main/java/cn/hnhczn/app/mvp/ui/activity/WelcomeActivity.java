package cn.hnhczn.app.mvp.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.hnhczn.app.R;
import cn.hnhczn.app.commonsdk.cache.DisCacheHelper;
import cn.hnhczn.app.commonsdk.core.RouterHub;
import cn.hnhczn.app.commonsdk.utils.PermissionPageUtils;
import cn.hnhczn.app.commonsdk.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * ================================================
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Route(path = RouterHub.APP_WELCOMEACTIVITY)
public class WelcomeActivity extends BaseActivity {
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        DisCacheHelper.put("savedInstanceState","savedInstanceState");
        getPermissions();
    }

    //获取权限
    public void getPermissions() {
        PermissionUtil.requestPermission(new PermissionUtil.RequestPermission() {
                                             @Override
                                             public void onRequestPermissionSuccess() {
                                                 //request permission success, do something.
                                                 Observable.timer(2, TimeUnit.SECONDS)
                                                         .observeOn(AndroidSchedulers.mainThread())
                                                         .subscribe(aLong -> {
                                                             Utils.navigation(WelcomeActivity.this, RouterHub.APP_MAINACTIVITY);
                                                             finish();
                                                             overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                                         });
                                             }

                                             @Override
                                             public void onRequestPermissionFailure(List<String> permissions) {
                                                 // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,再次申请
                                                 getPermissions();
                                             }

                                             @Override
                                             public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                                                 // 用户拒绝了该权限，并且选中『不再询问』
                                                 new QMUIDialog
                                                         .MessageDialogBuilder(WelcomeActivity.this)
                                                         .setTitle("提示")
                                                         .setMessage("为了更好的体验，需要存储权限和相机权限")
                                                         .addAction("已设置", ((dialog, index) -> {
                                                             getPermissions();
                                                             dialog.dismiss();
                                                         }))
                                                         .addAction("设置", ((dialog, index) -> {
                                                             new PermissionPageUtils(WelcomeActivity.this).jumpPermissionPage();
                                                         }))
                                                         .setCancelable(false)
                                                         .setCanceledOnTouchOutside(false)
                                                         .show();
                                             }
                                         }, new RxPermissions(WelcomeActivity.this), ArmsUtils.obtainAppComponentFromContext(WelcomeActivity.this).rxErrorHandler(),
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA);

    }


}
