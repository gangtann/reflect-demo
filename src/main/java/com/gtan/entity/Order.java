package com.gtan.entity;

import com.gtan.annotation.Autowired;

/**
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
public class Order {

    private Customer customer;

    private Address address;

    public Customer getCustomer() {
        return customer;
    }

    public Address getAddress() {
        return address;
    }

    @Autowired
    public Order(Customer customer, Address address) {
        this.customer = customer;
        this.address = address;
    }

    public Order() {
    }

}
