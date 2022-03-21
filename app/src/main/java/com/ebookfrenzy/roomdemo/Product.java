package com.ebookfrenzy.roomdemo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
Represents an entity for a table named products
 */
@Entity(tableName = "products")
public class Product {

    /*
    configured to be primary key and auto-generated
    primary key can't be null so assign @NonNull annotation
    assign column name of productId
     */
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "productId")
    private int id;

    /*
    assign column name of productName
     */
    @ColumnInfo(name = "productName")
    private String name;

    /*
    no need to reference quantity col in SQL queries
    so no column name assigned
     */
    private int quantity;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
