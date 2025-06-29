package com.gtan.ioc;

import com.gtan.annotation.Printable;
import com.gtan.service.Container;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ReflectTest：测试自定义 IoC 容器的核心功能，包括：
 * 1. 根据 @Bean 注解从配置类创建单例 Bean
 * 2. 根据 @Autowired 注解构造器创建带依赖的实例
 * 3. 获取并调用标注 @Printable 的方法
 *
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReflectTest {

    /**
     * 测试 @Bean 注解：
     * 从配置类中获取 Customer Bean，并打印其字符串表示
     *
     * @throws ClassNotFoundException    加载 Customer 类失败时抛出
     * @throws InvocationTargetException @Bean 方法调用失败时抛出
     * @throws NoSuchMethodException     找不到配置类构造器时抛出
     * @throws InstantiationException    配置类实例化失败时抛出
     * @throws IllegalAccessException    构造器或方法访问受限时抛出
     */
    @Test
    public void test1() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Container container = new Container();
        container.init();
        String className = "com.gtan.entity.Customer";
        Class<?> clazz = Class.forName(className);
        Object obj = container.getServiceInstanceByClass(clazz);
        System.out.println(obj);

    }

    /**
     * 测试 @Autowired 注解：
     * 通过带依赖的构造器创建 Order 实例，并打印其依赖的 Customer 对象
     *
     * @throws ClassNotFoundException    加载 Order 类失败时抛出
     * @throws InvocationTargetException 构造器调用失败时抛出
     * @throws NoSuchMethodException     找不到指定构造器或字段时抛出
     * @throws InstantiationException    对象实例化失败时抛出
     * @throws IllegalAccessException    构造器或字段访问受限时抛出
     * @throws NoSuchFieldException      找不到字段 customer 时抛出
     */
    @Test
    public void test2() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        Container container = new Container();
        container.init();
        String className = "com.gtan.entity.Order";
        Class<?> clazz = Class.forName(className);
        Object obj = container.createInstance(clazz);
        System.out.println(obj);
        String fieldName = "customer";
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        Object fieldValue = field.get(obj);
        System.out.println(fieldValue);
    }

    /**
     * 测试 @Printable 注解：
     * 查找 Customer 对象中所有被 @Printable 标注的方法并调用它们
     *
     * @throws ClassNotFoundException    加载 Order 或 Customer 类失败时抛出
     * @throws InvocationTargetException 方法调用抛出异常时抛出
     * @throws NoSuchMethodException     找不到方法时抛出
     * @throws InstantiationException    对象实例化失败时抛出
     * @throws IllegalAccessException    访问受限时抛出
     * @throws NoSuchFieldException      找不到字段 customer 时抛出
     */
    @Test
    public void test() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        Container container = new Container();
        container.init();
        String className = "com.gtan.entity.Order";
        Class<?> clazz = Class.forName(className);
        Object obj = container.createInstance(clazz);
        System.out.println(obj);
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
