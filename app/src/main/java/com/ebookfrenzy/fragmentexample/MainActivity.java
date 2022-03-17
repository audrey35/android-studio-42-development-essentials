package com.ebookfrenzy.fragmentexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import com.ebookfrenzy.fragmentexample.databinding.ActivityMainBinding;

public class MainActivity extends FragmentActivity implements ToolbarFragment.ToolbarListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onButtonClick(int fontsize, String text) {

    }
}