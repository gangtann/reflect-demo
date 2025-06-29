package com.gtan.entity;

import com.gtan.annotation.MyAnnotation;

import java.util.List;

/**
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
public class User extends Person {

    @MyAnnotation
    public String name;

    private final int age;

    private String email;

    private Message message;

    private List<String> comments;

    public static int publicStaticField = 1;

    private static int privateStaticField = 10;

    static {
        System.out.println("UserClass is initialized");
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User() {
        this.age = 18;
    }

    public String getName() {
        return name;
    }

    public String getEamil() {
        return email;
    }

    public void  myPublicMethod() {
        System.out.println("this is a public method");
    }

    private void myPrivateMethod() {
        System.out.println("this is a private method");
    }

    private void myPrivateMethod(String content, String mark) {
        System.out.println("this is a private method with parameters: content = " + content + ", mark = " + mark);
    }

    public static void myPublicStaticMethod() {
        System.out.println("this is a public static method");
    }

    private static void myPrivateStaticMethod() {
        System.out.println("this is a private static method");
    }

    private static void myPrivateStaticMethod(String content) {
        System.out.println("this is a private static method with parameters: content = " + content);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", comments=" + comments +
                '}';
    }
}
