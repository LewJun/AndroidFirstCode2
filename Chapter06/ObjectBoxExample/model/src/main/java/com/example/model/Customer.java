package com.example.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * 客户类
 * Created by LewJun on 2018/02/01.
 */
@Entity
public class Customer {
    @Id
    public long id;

    public String name;

    // 一个客户可以对应多个订单
    // 使用@Backlink注解，它链接回目标对象中的一对一关系
    @Backlink // 必须在Order类中添加属性ToOne<Customer> 属性名; 属性名任意 否则会报错
    public ToMany<Order> orders;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orders='" + orders + '\'' +
                '}';
    }
}
