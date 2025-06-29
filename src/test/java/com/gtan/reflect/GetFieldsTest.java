package com.gtan.basic;

import com.gtan.annotation.MyAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;

/**
 * getDeclaredXxx() 只返回当前类自身声明的所有成员（不限访问权限，不含继承来的）；
 * 而 getXxx() 只返回该类及其父类中对外公开的 public 成员。
 *
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetFieldsTest {

    /**
     * 获取指定类的所有声明字段（包括 private、protected、public），并打印字段名称。
     * 使用 getDeclaredFields() 可获取类自身声明的所有字段。
     *
     * @throws ClassNotFoundException 当指定类未找到时抛出此异常
     */
    @Test
    public void testDeclaredFields() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }

    /**
     * 获取指定类及其父类的所有公共字段，并打印字段名称。
     * 使用 getFields() 可获取类自身及继承自父类的 public 字段。
     *
     * @throws ClassNotFoundException 当指定类未找到时抛出此异常
     */
    @Test
    public void testPublicFields() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }

    /**
     * 获取指定类的父类中所有声明字段（包括 private、protected、public），并打印字段名称。
     * 使用 getSuperclass().getDeclaredFields() 可获取父类自身声明的所有字段。
     *
     * @throws ClassNotFoundException 当指定类未找到时抛出此异常
     */
    @Test
    public void testSuperclassDeclaredFields() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Field[] fields = clazz.getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }

    /**
     * name 字段声明了 @MyAnnotation 注解时，获取并打印该注解实例。
     *
     * @throws ClassNotFoundException 当指定类未找到时抛出异常
     * @throws NoSuchFieldException   当指定字段不存在时抛出异常
     */
    @Test
    public void testNameFieldAnnotation() throws ClassNotFoundException, NoSuchFieldException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Field field = clazz.getDeclaredField("name");
        System.out.println(field.getType());
        System.out.println(field.getDeclaredAnnotation(MyAnnotation.class));
    }

    /**
     * email 字段未声明 @MyAnnotation 注解时，返回 null。
     *
     * @throws ClassNotFoundException 当指定类未找到时抛出异常
     * @throws NoSuchFieldException   当指定字段不存在时抛出异常
     */
    @Test
    public void testEmailFieldNoAnnotation() throws ClassNotFoundException, NoSuchFieldException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Field field = clazz.getDeclaredField("email");
        System.out.println(field.getType());
        System.out.println(field.getDeclaredAnnotation(MyAnnotation.class));
    }

    /**
     * comments 字段声明为 List<String> 类型，打印原始类型和泛型类型。
     *
     * @throws ClassNotFoundException 当指定类未找到时抛出异常
     * @throws NoSuchFieldException   当指定字段不存在时抛出异常
     */
    @Test
    public void testCommentsFieldGenericType() throws ClassNotFoundException, NoSuchFieldException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Field field = clazz.getDeclaredField("comments");
        System.out.println(field.getType());
        System.out.println(field.getGenericType());
    }

    /**
     * commentsNotExist 字段不存在时，获取字段将抛出异常。
     *
     * @throws ClassNotFoundException 当指定类未找到时抛出异常
     * @throws NoSuchFieldException   当指定字段不存在时抛出异常
     */
    @Test
    public void testNonExistentField() throws ClassNotFoundException, NoSuchFieldException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Field field = clazz.getDeclaredField("commentsNotExist");
        System.out.println(field.getType());
        System.out.println(field.getGenericType());
    }

}
