package com.ebookfrenzy.explicitintent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.ebookfrenzy.explicitintent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int request_code = 5;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void sendText(View view) {

        Intent i = new Intent(this, SecondActivity.class);

        String myString = binding.editText1.getText().toString();
        i.putExtra("qString", myString);
        startActivityForResult(i, request_code);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == request_code) &&
                (resultCode == RESULT_OK)) {
            String returnString = data.getExtras().getString("returnData");
            binding.textView1.setText(returnString);
        }
    }
}