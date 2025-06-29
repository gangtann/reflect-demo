package com.gtan.config;

import com.gtan.annotation.Bean;
import com.gtan.entity.Customer;
import com.gtan.entity.Address;
import com.gtan.entity.Message;

public class Config {

    @Bean
    public Customer customer() {
        return new Customer("GangTan", "gangtann@126.com");
    }

    @Bean
    public Address address() {
        return new Address("China", "100000");
    }

    public Message message() {
        return new Message("Hello World!");
    }

}
