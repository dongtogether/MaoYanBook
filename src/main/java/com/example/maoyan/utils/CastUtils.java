package com.example.maoyan.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * object转化为list
 * @author mjd
 * @date 2020/8/03 19:04
 */
public class CastUtils {
    public static <T> List<T> objectConvertToList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
}
