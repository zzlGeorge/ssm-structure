package com.vct.common.util;

import org.apache.commons.collections.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: MapUtil <br>
 * date: 2019/12/13 21:24 <br>
 * author: liuzz <br>
 * version: 1.0 <br>
 */
public class MapUtil {

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Map<String, Object> convertBean2Map(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class<?> type = bean.getClass();
        Map<String, Object> returnMap = new HashMap<>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result != null) {
                    if(result instanceof List){
                        returnMap.put(propertyName, listBean2ListMapRecursion((List) result));
                    }else {
                        returnMap.put(propertyName, result);
                    }
                } else {
                    returnMap.put(propertyName, null);
                }
            }
        }
        return returnMap;
    }

    public static List<Map<String, Object>> listBean2ListMapRecursion(List listbean) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if (CollectionUtils.isNotEmpty(listbean)) {
            try {
                for (Object o : listbean) {
                    result.add(convertBean2Map(o));
                }
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
