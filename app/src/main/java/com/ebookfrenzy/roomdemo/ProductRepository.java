package com.ebookfrenzy.roomdemo;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Responsible for interacting with the Room database
on behalf of the ViewModel
 */
public class ProductRepository {

    // declares a MutableLiveData variable named searchResults
    // results of the search operation will be stored here
    // whenever an asynchronous search task completes
    private final MutableLiveData<List<Product>> searchResults = new MutableLiveData<>();
    private List<Product> results;

    // result of getAllProducts
    private final LiveData<List<Product>> allProducts;
    // var for the DAO reference
    private final ProductDao productDao;

    // return the search results to the repository thread
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override public void handleMessage(Message msg) {
            searchResults.setValue(results);
        }
    };

    // constructor that obtains the
    // DAO reference via a ProductRoomDatabase instance
    public ProductRepository(Application application) {
        ProductRoomDatabase db;
        db = ProductRoomDatabase.getDatabase(application);
        productDao = db.productDao();

        // call the getAllProducts method within the constructor
        allProducts = productDao.getAllProducts();
    }

    // insert delete find methods from DAO
    // create a new thread to insert a product
    public void insertProduct(Product newproduct) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            productDao.insertProduct(newproduct);
        });

        executor.shutdown();
    }

    // create a new thread to delete a product
    public void deleteProduct(String name) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            productDao.deleteProduct(name);
        });

        executor.shutdown();
    }

    // send a message to the handler indicating
    // that new results are available
    public void findProduct(String name) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            results = productDao.findProduct(name);
            handler.sendEmptyMessage(0);
        });

        executor.shutdown();
    }

    // method for ViewModel to get reference to allProducts live data object
    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    // method for ViewModel to get reference to searchResults live data object
    public MutableLiveData<List<Product>> getSearchResults() {
        return searchResults;
    }
}
