package com.example.litepaltest.model;

import org.litepal.annotation.Column;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by LewJun on 2018/1/16.
 */
public class Album extends BaseModel {
    @Column(unique = true, defaultValue = "unknown")
    public String name;

    public float price;

    public byte[] cover;

    public List<Song> songs = new ArrayList<>();

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", cover=" + Arrays.toString(cover) +
                ", songs=" + songs +
                '}';
    }
}
