package com.example.model;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * 订单类
 * Created by LewJun on 2018/02/01.
 */
@Entity
public class Order {
    @Id
    public long id;

    // 下单时间
    public Date date;

    // 一个订单对应一个客户
    public ToOne<Customer> customer;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", customer=" + customer +
                '}';
    }
}
