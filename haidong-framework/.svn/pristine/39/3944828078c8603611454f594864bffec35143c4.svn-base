package com.huixin.framework.utils;

import java.lang.reflect.Method;

/**
 * User: X.Y.CHEN
 * Date: 14-9-28
 * Time: 下午3:33
 */
public class ModelUtils {


    /**
     * 复制bean对象的值
     *
     * @param sourceBean 原对象
     * @param targetBean 目标对象
     */
    public static void copyBean(Object sourceBean, Object targetBean) {
        Method[] mthsSource = sourceBean.getClass().getMethods();
        Method[] mthsTarget = targetBean.getClass().getMethods();
        for (Method method : mthsTarget) {
            if (method.getName().startsWith("set")) {
                // 复制成员变量值
                copyValue(method, mthsSource, sourceBean, targetBean);
            }
        }

    }

    /**
     * 复制成员变量值
     *
     * @param methodTarget 目标对象的方法
     * @param mthsSource   原对象方法数组
     * @param sourceBean   原对象
     * @param targetBean   目标对象
     */
    private static void copyValue(Method methodTarget, Method[] mthsSource,
                                  Object sourceBean, Object targetBean) {
        String name = "get" + methodTarget.getName().substring(3);
        try {
            for (Method method : mthsSource) {
                if (name.equals(method.getName())) {
                    methodTarget.invoke(targetBean, method.invoke(sourceBean));
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
