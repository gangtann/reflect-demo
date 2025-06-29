package com.gtan.entity;

/**
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
public class Address {

    private String street;

    private String postCode;

    public String getStreet() {
        return street;
    }

    public String getPostCode() {
        return postCode;
    }

    public Address(String street, String postCode) {
        this.street = street;
        this.postCode = postCode;
    }

    public void printStreet() {
        System.out.println("Address street: " + street);
    }

    public void printPostCode() {
        System.out.println("Address postCode: " + postCode);
    }

}
