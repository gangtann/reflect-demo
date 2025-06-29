# Reflection and simple IOC

## Reflection

反射的基础知识，常用方法在`test`包下的`reflect`包中，可以直接运行。

## IOC

实现了`IOC`的创建对象。利用反射实现了基于方法和基于构造器的对象创建。
`annotation`包中是自定义注解，`config`包中是通过自定义`@Bean`注解创建对象。
`entity`包是实体类，`service`包中是简易的IOC容器实现类。

`test`包下的`reflect`包中实现了`IOC`的测试，可以直接运行。
