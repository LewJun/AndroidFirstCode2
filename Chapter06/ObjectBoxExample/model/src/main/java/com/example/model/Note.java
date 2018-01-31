package com.example.model;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

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

    public Note() {
    }

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
