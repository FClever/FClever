package cn.hnhczn.app.mvp.model.api;

import android.os.strictmode.IntentReceiverLeakedViolation;

import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.hnhczn.app.app.App;

/**
 * Created by FClever on 2018/8/9.
 */
public class CacheManager {
    private static Map<Class, Object> classObjectMap = new HashMap<>();

    public static synchronized <T> T getService(Class<T> clazz) {
        T obj = (T) classObjectMap.get(clazz);
        if (obj == null) {
            obj = ArmsUtils
                    .obtainAppComponentFromContext(App.getContext())
                    .repositoryManager()
                    .obtainCacheService(clazz);
            classObjectMap.put(clazz, obj);
        }
        return obj;
    }

}
