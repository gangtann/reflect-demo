package com.gtan.ioc;

import com.gtan.entity.Address;
import com.gtan.entity.Customer;
import com.gtan.entity.Order;
import org.junit.jupiter.api.Test;

/**
 * @author gangtann@126.com
 * @version 1.0
 * @since 2025-06-29
 */
public class NoReflectTest {

    @Test
    public void test() {
        Address address = new Address("某某街道", "123456");
        Customer customer = new Customer("GangTan", "gangtann@126.com");
        Order order = new Order(customer, address);
        order.getCustomer().printName();
        order.getAddress().printStreet();
    }
}
