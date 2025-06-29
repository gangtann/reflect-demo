package com.gtan.reflect;

import com.gtan.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * InstanceFieldMethodTest：演示通过反射创建对象实例、访问和修改实例字段，以及调用实例方法的多种场景。
 *
 * {@implNote
 * 类型转换并不是反射的最佳实践，使用反射直接调用对象的方法和字段，而不是先进行类型转换。
 * 如果在编写阶段已经明确了要转换的类型，那么直接显式地调用更合适，而不必依赖反射。
 * 反射真正的价值在于处理编译时未知的类型，从而编写更具有通用性的代码。
 * 以下示例操作的是实例对象中的字段值和方法，静态成员操作方式类似，
 * 唯一的区别就是将之前的 null 参数换成对应的实例对象。
 * }
 *
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InstanceFieldMethodTest {

    /**
     * 通过无参构造器反射创建 User 实例，并进行类型校验。
     *
     * @throws ClassNotFoundException    加载 User 类失败时抛出
     * @throws NoSuchMethodException     找不到无参构造器时抛出
     * @throws InvocationTargetException 构造器内部抛出异常时抛出
     * @throws InstantiationException    实例化失败时抛出，如抽象类或接口
     * @throws IllegalAccessException    构造器访问受限时抛出
     */
    @Test
    public void testInstantiateWithoutArgs() throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        Object object = constructor.newInstance();
        if (object instanceof User) {
            User user = (User) object;
            System.out.println(user);
        }
        // 通过 Class.forName 创建的 Class 对象不支持 cast() 方法
    }

    /**
     * 通过类字面常量获取 Class 对象，并使用 cast() 将反射创建的实例转换为 User。
     *
     * @throws NoSuchMethodException     找不到无参构造器时抛出
     * @throws InvocationTargetException 构造器内部抛出异常时抛出
     * @throws InstantiationException    实例化失败时抛出
     * @throws IllegalAccessException    构造器访问受限时抛出
     */
    @Test
    public void testInstantiateWithCast() throws NoSuchMethodException,
            InvocationTargetException, InstantiationException,
            IllegalAccessException {
        Class<User> clazz = User.class;
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        Object object = constructor.newInstance();
        User user = clazz.cast(object);
        System.out.println(user);
    }

    /**
     * 通过带参构造器反射创建 User 实例，并进行类型校验。
     *
     * @throws ClassNotFoundException    加载 User 类失败时抛出
     * @throws NoSuchMethodException     找不到指定参数构造器时抛出
     * @throws InvocationTargetException 构造器内部抛出异常时抛出
     * @throws InstantiationException    实例化失败时抛出
     * @throws IllegalAccessException    构造器访问受限时抛出
     */
    @Test
    public void testInstantiateWithArgs() throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        Object object = constructor.newInstance("GangTan", 18);
        if (object instanceof User) {
            User user = (User) object;
            System.out.println(user);
        }
    }

    /**
     * 读取 public 实例字段 name 并打印其值。
     *
     * @throws ClassNotFoundException    加载 User 类失败时抛出
     * @throws NoSuchMethodException     找不到对应构造器时抛出
     * @throws InstantiationException    实例化失败时抛出
     * @throws IllegalAccessException    实例化或字段访问受限时抛出
     * @throws NoSuchFieldException      找不到字段 name 时抛出
     */
    @Test
    public void testAccessPublicFieldName() throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        Object object = constructor.newInstance("GangTan", 18);
        Field field = clazz.getDeclaredField("name");
        System.out.println(field.get(object));
    }

    /**
     * 读取 private 实例字段 age，需要 setAccessible(true)。
     *
     * @throws ClassNotFoundException    加载 User 类失败时抛出
     * @throws NoSuchMethodException     找不到对应构造器时抛出
     * @throws InstantiationException    实例化失败时抛出
     * @throws IllegalAccessException    字段访问受限时抛出
     * @throws NoSuchFieldException      找不到字段 age 时抛出
     */
    @Test
    public void testAccessPrivateFieldAge() throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        Object object = constructor.newInstance("GangTan", 18);
        Field field = clazz.getDeclaredField("age");
        field.setAccessible(true);
        System.out.println(field.get(object));
    }

    /**
     * 修改 private 实例字段 age 的值，并验证修改结果。
     *
     * @throws ClassNotFoundException    加载 User 类失败时抛出
     * @throws NoSuchMethodException     找不到对应构造器时抛出
     * @throws InstantiationException    实例化失败时抛出
     * @throws IllegalAccessException    字段访问受限时抛出
     * @throws NoSuchFieldException      找不到字段 age 时抛出
     */
    @Test
    public void testModifyPrivateFieldAge() throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        Object object = constructor.newInstance("GangTan", 18);
        Field field = clazz.getDeclaredField("age");
        field.setAccessible(true);
        field.set(object, 19); // 破坏封装性以演示修改值
        System.out.println(field.get(object));
    }

    /**
     * 列出所有声明的实例方法 (包括 private 方法) 并打印方法名。
     *
     * @throws ClassNotFoundException    加载 User 类失败时抛出
     */
    @Test
    public void testListDeclaredInstanceMethods() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }

    /**
     * 调用 public 实例方法 myPublicMethod。
     *
     * @throws ClassNotFoundException    加载 User 类失败时抛出
     * @throws NoSuchMethodException     找不到方法 myPublicMethod 时抛出
     * @throws InvocationTargetException 方法执行抛出异常时抛出
     * @throws IllegalAccessException    方法访问受限时抛出
     * @throws InstantiationException    无法实例化对象时抛出
     */
    @Test
    public void testInvokePublicInstanceMethod() throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            InstantiationException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        Object object = constructor.newInstance("GangTan", 18);
        Method method = clazz.getDeclaredMethod("myPublicMethod");
        method.invoke(object);
    }

    /**
     * 调用 private 实例方法 myPrivateMethod (无参数)，需要 setAccessible(true)。
     *
     * @throws ClassNotFoundException    加载 User 类失败时抛出
     * @throws NoSuchMethodException     找不到方法 myPrivateMethod 时抛出
     * @throws InvocationTargetException 方法执行抛出异常时抛出
     * @throws IllegalAccessException    方法访问受限时抛出
     * @throws InstantiationException    无法实例化对象时抛出
     */
    @Test
    public void testInvokePrivateInstanceMethodNoArgs() throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            InstantiationException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        Object object = constructor.newInstance("GangTan", 18);
        Method method = clazz.getDeclaredMethod("myPrivateMethod");
        method.setAccessible(true);
        method.invoke(object);
    }

    /**
     * 调用 private 实例方法 myPrivateMethod (两个 String 参数)，需要 setAccessible(true)。
     *
     * @throws ClassNotFoundException    加载 User 类失败时抛出
     * @throws NoSuchMethodException     找不到方法 myPrivateMethod 时抛出
     * @throws InvocationTargetException 方法执行抛出异常时抛出
     * @throws IllegalAccessException    方法访问受限时抛出
     * @throws InstantiationException    无法实例化对象时抛出
     */
    @Test
    public void testInvokePrivateInstanceMethodWithArgs() throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            InstantiationException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
        Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        Object object = constructor.newInstance("GangTan", 18);
        Method method = clazz.getDeclaredMethod("myPrivateMethod", String.class, String.class);
        method.setAccessible(true);
        method.invoke(object, "GangTan", "!");
    }
}
