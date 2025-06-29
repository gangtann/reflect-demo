package com.gtan.basic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * StaticFieldMethodTest：演示通过反射访问和修改静态字段，以及调用静态方法（包括私有方法）的多种场景
 *
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClassFieldMethodTest {

    /**
     * 读取公共静态字段 publicStaticField 的值（无需 setAccessible）。
     *
     * @throws ClassNotFoundException 如果加载类失败
     * @throws NoSuchFieldException   如果字段不存在
     * @throws IllegalAccessException 如果访问受限
     */
    @Test
    public void testReadPublicStaticField() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Field field = clazz.getDeclaredField("publicStaticField");
        System.out.println(field.get(null));
    }

    /**
     * 读取私有静态字段 privateStaticField 的值（需要 setAccessible）。
     *
     * @throws ClassNotFoundException 如果加载类失败
     * @throws NoSuchFieldException   如果字段不存在
     * @throws IllegalAccessException 如果访问受限
     */
    @Test
    public void testReadPrivateStaticField() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Field field = clazz.getDeclaredField("privateStaticField");
        field.setAccessible(true);
        System.out.println(field.get(null));
    }

    /**
     * 修改私有静态字段 privateStaticField 的值，并再次读取验证修改结果。
     *
     * @throws ClassNotFoundException 如果加载类失败
     * @throws NoSuchFieldException   如果字段不存在
     * @throws IllegalAccessException 如果访问受限
     */
    @Test
    public void testModifyPrivateStaticField() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Field field = clazz.getDeclaredField("privateStaticField");
        field.setAccessible(true);
        // 读取原始值
        System.out.println(field.get(null));
        // 修改值
        field.set(null, 100);
        // 读取修改后值
        System.out.println(field.get(null));
    }

    /**
     * 列出类中所有声明的方法，包括私有和公有方法。
     *
     * @throws ClassNotFoundException 如果加载类失败
     */
    @Test
    public void testListDeclaredMethods() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }

    /**
     * 调用公共静态方法 myPublicStaticMethod，无需实例化对象。
     *
     * @throws ClassNotFoundException    如果加载类失败
     * @throws NoSuchMethodException     如果方法不存在
     * @throws InvocationTargetException 如果方法内部抛出异常
     * @throws IllegalAccessException    如果访问受限
     */
    @Test
    public void testInvokePublicStaticMethod() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Method method = clazz.getDeclaredMethod("myPublicStaticMethod");
        method.invoke(null);
    }

    /**
     * 调用私有静态方法 myPrivateStaticMethod，无需参数。
     * 需要 setAccessible(true) 解锁私有访问权限。
     *
     * @throws ClassNotFoundException    如果加载类失败
     * @throws NoSuchMethodException     如果方法不存在
     * @throws InvocationTargetException 如果方法内部抛出异常
     * @throws IllegalAccessException    如果访问受限
     */
    @Test
    public void testInvokePrivateStaticMethodNoArgs() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Method method = clazz.getDeclaredMethod("myPrivateStaticMethod");
        method.setAccessible(true);
        method.invoke(null);
    }

    /**
     * 调用私有静态方法 myPrivateStaticMethod，带单个 String 参数。
     * 需要 setAccessible(true) 解锁私有访问权限。
     *
     * @throws ClassNotFoundException    如果加载类失败
     * @throws NoSuchMethodException     如果方法不存在
     * @throws InvocationTargetException 如果方法内部抛出异常
     * @throws IllegalAccessException    如果访问受限
     */
    @Test
    public void testInvokePrivateStaticMethodWithArg() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Method method = clazz.getDeclaredMethod("myPrivateStaticMethod", String.class);
        method.setAccessible(true);
        method.invoke(null, "hello world");
    }
}
