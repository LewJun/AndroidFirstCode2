package com.example.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * 客户类
 * Created by LewJun on 2018/02/01.
 */
@Entity
public class Customer {
    @Id
    public long id;

    public String name;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
