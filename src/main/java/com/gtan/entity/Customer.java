package com.gtan.entity;

import com.gtan.annotation.Printable;

/**
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
public class Customer {

    private String name;

    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Printable
    public void printName() {
        System.out.println("Customer name: " + name);
    }

    @Printable
    public void printEmail() {
        System.out.println("Customer email: " + email);
    }

}
