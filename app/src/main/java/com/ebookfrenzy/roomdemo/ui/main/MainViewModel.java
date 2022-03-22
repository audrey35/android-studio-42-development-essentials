package com.ebookfrenzy.roomdemo.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ebookfrenzy.roomdemo.Product;
import com.ebookfrenzy.roomdemo.ProductRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private ProductRepository repository;
    private LiveData<List<Product>> allProducts;
    private MutableLiveData<List<Product>> searchResults;

    // constructor creates a repository instance
    // the repo used to get references to the results
    // and live data objects so that they can be observed
    // by the UI controller
    public MainViewModel (@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        allProducts = repository.getAllProducts();
        searchResults = repository.getSearchResults();
    }

    MutableLiveData<List<Product>> getSearchResults() {
        return searchResults;
    }

    LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public void insertProduct(Product product) {
        repository.insertProduct(product);
    }

    public void findProduct(String name) {
        repository.findProduct(name);
    }

    public void deleteProduct(String name) {
        repository.deleteProduct(name);
    }
}