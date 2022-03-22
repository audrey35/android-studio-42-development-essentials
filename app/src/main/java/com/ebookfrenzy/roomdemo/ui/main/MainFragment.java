package com.ebookfrenzy.roomdemo.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebookfrenzy.roomdemo.Product;
import com.ebookfrenzy.roomdemo.R;
import com.ebookfrenzy.roomdemo.databinding.MainFragmentBinding;

import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainFragmentBinding binding;
    private ProductListAdapter adapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        listenerSetup();
        observerSetup();
        recyclerSetup();
    }

    private void listenerSetup() {

        // runs basic validation to ensure that user entered
        // a product name and a quantity
        // uses given data to create a new product entity object
        // ViewModel.insertProduct is called and passed the product object
        // before the fields are cleared
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.productName.getText().toString();
                String quantity = binding.productQuantity.getText().toString();

                if (!name.equals("") && !quantity.equals("")) {
                    Product product = new Product(name, Integer.parseInt(quantity));
                    mViewModel.insertProduct(product);
                    clearFields();
                } else {
                    binding.productID.setText(R.string.incomplete);
                }
            }
        });

        binding.findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.findProduct(binding.productName.getText().toString());
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.deleteProduct(binding.productName.getText().toString());
                clearFields();
            }
        });
    }

    private void observerSetup() {

        // "all products" observer passes the current list of products
        // to the RecyclerAdapter.setProductList where the displayed list
        // list will be updated
        mViewModel.getAllProducts().observe(getViewLifecycleOwner(),
                new Observer<List<Product>>() {
                    @Override
                    public void onChanged(@Nullable final List<Product> products) {
                        adapter.setProductList(products);
                    }
                });

        // "search results" observer
        mViewModel.getSearchResults().observe(getViewLifecycleOwner(),
                new Observer<List<Product>>() {
                    @Override
                    public void onChanged(@Nullable final List<Product> products) {

                        if (products.size() > 0) {
                            binding.productID.setText(String.format(Locale.US, "%d",
                                    products.get(0).getId()));
                            binding.productName.setText(products.get(0).getName());
                            binding.productQuantity.setText(String.format(
                                    Locale.US, "%d",
                                    products.get(0).getQuantity()));
                        } else {
                            binding.productID.setText(R.string.no_match);
                        }
                    }
                });
    }

    private void clearFields() {
        binding.productID.setText("");
        binding.productName.setText("");
        binding.productQuantity.setText("");
    }

}