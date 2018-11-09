package cn.hnhczn.app.commonsdk.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FClever on 2018/8/28.
 */
public class DataUtil {
    /**
     * 数据分组
     *
     * @param list
     * @param size
     * @param <T>
     * @return
     */
    public static <T> Map<Integer, List<T>> group(List<T> list, Integer size) {
        Map<Integer, List<T>> map = new LinkedHashMap<>();
        if (list != null && list.size() > 0) {
            int len = list.size();
            int pageCount = (int) Math.ceil(len * 1.0 / size);
            for (int i = 1; i < pageCount + 1; i++) {
                int toIndex = size * i;
                if (toIndex >= len) {
                    toIndex = len;
                }
                List<T> data = list.subList(size * (i - 1), toIndex);
                map.put(i, data);
            }
        }
        return map;
    }
}
