package com.example.model;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Transient;

/**
 * Created by LewJun on 2018/1/31.
 */
@Entity
public class Note {
    @Id
    public long id;
    public String text;
    public String comment;
    public Date date;


    @Transient//不需要持久化该字段
    public int tempUsageCount;

    public Note() {
    }

    @Index // 数据库会建立该属性的索引，当频繁查询该属性时会提高性能。
    public String serverUid;

    public Note(long id, String text, String comment, Date date) {
        this();
        this.id = id;
        this.text = text;
        this.comment = comment;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}
