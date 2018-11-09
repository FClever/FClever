package cn.hnhczn.app.commonsdk.cache;


import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

import cn.hnhczn.app.commonsdk.utils.GsonUtil;
import cn.hnhczn.app.commonsdk.utils.Kits;

/**
 * Created by FClever on 2018/5/22.
 */

public class DisCacheHelper {
    private static DiskCache instance;

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static DiskCache getInstance() {
        if (instance == null) {
            synchronized (DisCacheHelper.class) {
                if (instance == null) {
                    instance = DiskCache.getInstance(mContext);
                }
            }
        }
        return instance;
    }

    /**
     * 缓存数据
     *
     * @param key
     * @param obj
     */
    public static void put(final Object key, final Object obj) {
        try {
            getInstance().put(key.toString(), GsonUtil.GsonToString(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void put(Object key, Object obj, long expireMills) {
        try {
            getInstance().put(key.toString(), GsonUtil.GsonToString(obj), expireMills);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static Object get(Object key) {
        return getInstance().get(key.toString());
    }


    @Nullable
    public static <T> T get(Object key, Class<T> cls) {
        try {
            String val = getInstance().get(key.toString());
            if (!Kits.Empty.check(val)) {
                return GsonUtil.GsonToObject(val, cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
            remove(key.toString());
        }
        return null;
    }

    @Nullable
    public static <T> T get(Object key, TypeToken<T> type) {
        try {
            String val = getInstance().get(key.toString());
            if (!Kits.Empty.check(val)) {
                return GsonUtil.getGson().fromJson(val, type.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
            remove(key.toString());
        }
        return null;
    }

    public static void remove(String key) {
        getInstance().remove(key);
    }

    public static boolean contains(String key) {
        return getInstance().contains(key);
    }

    public static void clear() {
        getInstance().clear();
    }
}
