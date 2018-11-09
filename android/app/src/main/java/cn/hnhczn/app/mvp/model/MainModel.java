package cn.hnhczn.app.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import cn.hnhczn.app.mvp.contract.MainContract;
import cn.hnhczn.app.mvp.model.api.cache.CommonCache;
import cn.hnhczn.app.mvp.model.api.service.CommonService;
import cn.hnhczn.app.mvp.model.entity.AndroidAppInfoBean;
import io.reactivex.Observable;


@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<AndroidAppInfoBean> getLatestAppInfo() {
        Observable<AndroidAppInfoBean> observable = mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLatestAppInfo();
        return observable
                .onErrorReturn(throwable -> {
                    AndroidAppInfoBean androidAppInfoBean = null;
                    try {
                        Observable<AndroidAppInfoBean> cacheObservable = mRepositoryManager.obtainCacheService(CommonCache.class).getAppInfo(observable);
                        if (!cacheObservable.isEmpty().blockingGet()) {
                            androidAppInfoBean = cacheObservable.toList().blockingGet().get(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return androidAppInfoBean;
                })
                .doOnNext(androidAppInfoBean ->
                        mRepositoryManager.obtainCacheService(CommonCache.class).getAppInfo(Observable.just(androidAppInfoBean)).subscribe()
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

}