package com.example.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Uid;

/**
 * Created by LewJun on 2018/02/02.
 */
@Entity
@Uid(824981247062842558L)
public class MyNewEntity {
    @Id
    public long id;

    @Uid(7499039675103557753L)
    public int year;

    @Uid(8954002211315514987L)
    public String dayOfMonth;

    public MyNewEntity() {}

    public MyNewEntity(long id, int year, String dayOfMonth) {
        this.id = id;
        this.year = year;
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public String toString() {
        return "MyNewEntity{" +
                "id=" + id +
                ", year='" + year + '\'' +
                ", dayOfMonth='" + dayOfMonth + '\'' +
                '}';
    }
}
