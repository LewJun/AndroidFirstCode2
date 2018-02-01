package com.example.model;

import java.util.Date;
import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

/**
 * Created by LewJun on 2018/02/01.
 */
@Entity
public class CheckItem {
    @Id
    public long id;
    public String itemCode;
    public String itemName;
    public int level;

    @Backlink
    public ToMany<CheckItem> children;

    public ToOne<CheckItem> parent;

    @Transient
    public List<CheckItem> itemSub;

    @Override
    public String toString() {
        return "CheckItem{" +
                "id=" + id +
                ", itemCode='" + itemCode + '\'' +
                ", itemName='" + itemName + '\'' +
                ", parent=" + parent + '\'' +
                ", children=" + children + '\'' +
                ", itemSub=" + itemSub + '\'' +
                '}';
    }
}
