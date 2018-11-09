package cn.hnhczn.app.mvp.model.api.service;

import cn.hnhczn.app.mvp.model.entity.AndroidAppInfoBean;
import io.reactivex.Observable;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static cn.hnhczn.app.mvp.model.api.Api.APP_DOMAIN;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * ================================================
 * 存放通用的一些 API
 * <p>
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface CommonService {
    String HEADER_API_VERSION = "Content-Type:application/x-www-form-urlencoded; charset=utf-8";

    @Headers({DOMAIN_NAME_HEADER + APP_DOMAIN})
    @POST("/android/findLatestAndroidAppInfo")
    Observable<AndroidAppInfoBean> getLatestAppInfo();

}
