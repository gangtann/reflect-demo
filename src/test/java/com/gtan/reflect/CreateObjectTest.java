package com.gtan.basic;

import com.gtan.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * CreateObjectTest：演示获取 Class 对象的三种常见方式
 *
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateObjectTest {

    /**
     * 通过类字面常量获取 Class 对象。
     * 使用 ClassName.class 语法，无需加载或初始化类，适用于编译时已知的类型。
     */
    @Test
    public void testClassLiteral() {
        Class<User> userClass = User.class;
    }

    /**
     * 通过已有实例的 getClass() 方法获取 Class 对象。
     * 返回调用对象所属的运行时类，适用于已有对象实例的场景。
     */
    @Test
    public void testGetClassMethod() {
        User user = new User("GangTan", 18);
        Class<?> userClass = user.getClass();
    }

    /**
     * 通过 Class.forName() 动态加载并获取 Class 对象。
     * 根据全限定类名在运行时加载类，会触发类初始化，常用于类名在编译时未知的场景。
     *
     * @throws ClassNotFoundException 当指定类未找到时抛出此异常
     */
    @Test
    public void testForName() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.gtan.entity.User");
    }

}
