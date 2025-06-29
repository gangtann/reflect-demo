package com.gtan.service;

import com.gtan.annotation.Autowired;
import com.gtan.annotation.Bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 简易 IoC 容器：
 * 负责扫描配置类中标注 @Bean 的方法，将其返回类型和方法映射保存，
 * 并支持根据类型获取单例 Bean 或通过带 @Autowired 的构造器创建服务实例
 *
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
public class Container {

    /**
     * 存放配置类中所有返回类型到方法的映射，用于根据类型创建 Bean
     */
    private Map<Class<?>, Method> methods;

    /**
     * 配置类实例，用于调用 @Bean 方法生成 Bean
     */
    private Object config;

    /**
     * 单例服务缓存，保证同一类型的 Bean 只创建一次
     */
    private Map<Class<?>, Object> services;

    /**
     * 初始化容器：
     * 1. 扫描 com.gtan.config.Config 类中的所有 @Bean 方法
     * 2. 将返回类型与对应方法保存到 methods
     * 3. 实例化配置类对象，供后续调用
     *
     * @throws ClassNotFoundException    配置类未找到时抛出
     * @throws NoSuchMethodException     无默认构造器时抛出
     * @throws InvocationTargetException 构造器或方法调用失败时抛出
     * @throws InstantiationException    无法实例化配置类时抛出
     * @throws IllegalAccessException    构造器或方法访问受限时抛出
     */
    public void init() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        this.methods = new HashMap<>();
        this.services = new HashMap<>();
        // 加载配置类及其 @Bean 方法
        Class<?> clazz = Class.forName("com.gtan.config.Config");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(Bean.class)) {
                // 保存返回类型到方法的映射
                this.methods.put(declaredMethod.getReturnType(), declaredMethod);
            }
        }
        // 实例化配置类，用于后续生成 Bean
        this.config = clazz.getConstructor().newInstance();
    }

    /**
     * 按类型获取单例服务实例：
     * 1. 若 services 缓存中存在，直接返回
     * 2. 否则根据 methods 调用对应 @Bean 方法生成，缓存并返回
     *
     * @param clazz 服务类型
     * @return 对应类型的单例 Bean，若未定义则返回 null
     * @throws InvocationTargetException 方法调用失败时抛出
     * @throws IllegalAccessException    方法访问受限时抛出
     */
    public Object getServiceInstanceByClass(Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        if (this.services.containsKey(clazz)) {
            return this.services.get(clazz);
        } else {
            if (this.methods.containsKey(clazz)) {
                // 调用配置类的 @Bean 方法创建 Bean
                Method method = this.methods.get(clazz);
                Object obj = method.invoke(this.config);
                this.services.put(clazz, obj);
                return obj;
            }
        }
        return null;
    }

    /**
     * 为指定类型创建实例：
     * 1. 查找所有构造器，若标注 @Autowired，则优先使用该构造器，
     * 并递归获取所需依赖类型的实例作为参数
     * 2. 若无 @Autowired 构造器，使用默认无参构造器创建
     *
     * @param clazz 目标类类型
     * @return 创建好的实例对象
     * @throws InvocationTargetException 构造器调用失败时抛出
     * @throws IllegalAccessException    构造器访问受限时抛出
     * @throws InstantiationException    无法实例化类时抛出
     * @throws NoSuchMethodException     找不到对应构造器时抛出
     */
    public Object createInstance(Class<?> clazz) throws InvocationTargetException, IllegalAccessException,
            InstantiationException, NoSuchMethodException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] arguments = new Object[parameterTypes.length];
                // 为每个参数获取依赖实例
                for (int i = 0; i < parameterTypes.length; i++) {
                    arguments[i] = getServiceInstanceByClass(parameterTypes[i]);
                }
                // 使用带参构造器创建实例
                return constructor.newInstance(arguments);
            }
        }
        // 若无 @Autowired 构造器，使用无参构造器创建实例
        return clazz.getConstructor().newInstance();
    }
}
