package com.ebookfrenzy.roomdemo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/*
Implements methods to insert, find and delete
records from the products database.
 */
@Dao
public interface ProductDao {

    // pass a Product entity object with
    // data to be stored
    @Insert
    void insertProduct(Product product);

    // pass a String with name of the product
    // to find the product
    @Query("SELECT * FROM products WHERE productName = :name")
    List<Product> findProduct(String name);

    // pass a String with name of the product
    // to delete the product
    @Query("DELETE FROM products WHERE productName = :name")
    void deleteProduct(String name);

    // returns a LiveData object containing all records
    // within the database
    // used to keep the RecyclerView product list in the UI
    // synchronized with the database
    @Query("SELECT * FROM products")
    LiveData<List<Product>> getAllProducts();
}
