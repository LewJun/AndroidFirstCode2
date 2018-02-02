package com.example.model;

import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Uid;
import io.objectbox.converter.PropertyConverter;

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

    @Convert(converter = ColorConverter.class, dbType = Integer.class)
    public Color color;

    @Convert(converter = DeviceIdConverter.class, dbType = String.class)
    public List<Long> deviceIds;

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
                ", color='" + color + '\'' +
                ", deviceIds='" + deviceIds + '\'' +
                '}';
    }

    public enum Color {
        RED(1, "F00"),
        GREEN(2, "0F0"),
        BLUE(3, "00F"),
        BLACK(4, "000"),
        WHITE(5, "FFF");

        public int code;

        public String msg;

        Color(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

//    如果您在实体类中定义自定义类型或转换器，则它们必须是静态的。否则编译错误: 需要包含MyNewEntity.ColorConverter的封闭实例
    static class ColorConverter implements PropertyConverter<Color, Integer> {

        @Override
        public Color convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }

            for (Color color : Color.values()) {
                if (color.code == databaseValue) {
                    return color;
                }
            }
            return Color.BLACK;
        }

        @Override
        public Integer convertToDatabaseValue(Color entityProperty) {
            return entityProperty == null ? null : entityProperty.code;
        }
    }

    static class DeviceIdConverter implements PropertyConverter<List<Long>, String> {
        @Override
        public List<Long> convertToEntityProperty(String databaseValue) {
            if (databaseValue == null) {
                return null;
            }

            String[] databaseValues = databaseValue.split(";");
            List<Long> result = new ArrayList<>();
            for (String dv : databaseValues) {
                result.add(Long.valueOf(dv));
            }
            return result;
        }

        @Override
        public String convertToDatabaseValue(List<Long> entityProperty) {
            if (entityProperty == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (Long ep : entityProperty) {
                sb.append(ep).append(";");
            }
            return sb.toString();
        }
    }
}
