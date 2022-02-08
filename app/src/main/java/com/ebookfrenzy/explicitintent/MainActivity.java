package com.ebookfrenzy.explicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ebookfrenzy.explicitintent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        super.onCreate(savedInstanceState);
        setContentView(view);
    }

    public void sendText(View view) {

        // new Intent instance created, pass current activity (this) and SecondActivity
        Intent i = new Intent(this, SecondActivity.class);

        // extract text entered into EditText1
        String myString = binding.editText1.getText().toString();

        // extracted text is added to the intent object as key-value pair
        i.putExtra("qString", myString);

        // intent is started as the intent object is passed (the one containing extracted text)
        startActivity(i);
    }
}