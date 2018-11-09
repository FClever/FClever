package cn.hnhczn.app.mvp.model.api.cache;

import cn.hnhczn.app.mvp.model.entity.AndroidAppInfoBean;
import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;

/**
 * ================================================
 * 展示 {@link RxCache#using(Class)} 中需要传入的 Providers 的使用方式
 * LifeCache设置缓存过期时间. 如果没有设置@LifeCache , 数据将被永久缓存理除非你使用了 EvictProvider,EvictDynamicKey or EvictDynamicKeyGroup .
 * <p>
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface CommonCache {
    //@LifeCache(duration = 2, timeUnit = TimeUnit.SECONDS)
    Observable<Reply<Object>> getString(Observable<Object> objectObservable, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);

    Observable<AndroidAppInfoBean> getAppInfo(Observable<AndroidAppInfoBean> observable);
}
