package com.example.litepaltest.model;

import org.litepal.annotation.Column;

/**
 * Created by LewJun on 2018/1/16.
 */
public class Song extends BaseModel {
    @Column(nullable = false)
    public String name;

    public int duration;

    @Column(ignore = true)
    public String uselessField;

    public Album album;

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", uselessField='" + uselessField + '\'' +
                ", album=" + album +
                '}';
    }
}
