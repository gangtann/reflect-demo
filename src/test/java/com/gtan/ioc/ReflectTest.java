package com.gtan.reflect;

import com.gtan.annotation.Printable;
import com.gtan.service.Container;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
public class ReflectTest {

    @Test
    public void test() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        Container container = new Container();
        container.init();
        String className = "com.gtan.entity.Order";
        Class<?> clazz = Class.forName(className);
        Object obj = container.createInstance(clazz);
        String fieldName = "customer";
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Object fieldValue = field.get(obj);
        System.out.println(fieldValue);
        Method[] methods = fieldValue.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Printable.class)) {
                method.invoke(fieldValue);
            }
        }
    }

}
