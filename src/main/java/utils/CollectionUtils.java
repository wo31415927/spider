package utils;

import com.google.common.collect.ObjectArrays;

/**
 * zeyu
 * 2017/2/10
 */
public class CollectionUtils {
    public static Object[] transfer2dTo1d(Object[][] arr2d) {
        if (null == arr2d) {
            return null;
        }
        Object[] result = {};
        for (Object[] arr : arr2d) {
            result = ObjectArrays.concat(result, arr, Object.class);
        }
        return result;
    }
}
