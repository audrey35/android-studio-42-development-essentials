package com.ebookfrenzy.threadexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.os.Handler;
import android.os.Message;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.ebookfrenzy.threadexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Future<String> future;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
            binding.myTextView.setText(string);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void buttonClick(View view) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        future = executor.submit(new Callable<String>() {
            public String call() {
                long endTime = System.currentTimeMillis() + 10 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime - System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                return("Task Completed");
            }
        });
        executor.shutdown();
    }

    public void statusClick(View view) {

        if (future.isDone()) {
            String result = null;
            try {
                result = future.get(3, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException
                    | TimeoutException e) {
                e.printStackTrace();
            }
            binding.myTextView.setText(result);
        } else {
            binding.myTextView.setText("Waiting");
        }
    }
/*
    public void buttonClick(View view) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(new Runnable(){
            public void run(){
                long endTime = System.currentTimeMillis() + 10 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime - System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("myKey", "Button Pressed");
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
        executor.shutdown();
    }
*/
/*
    public void buttonClick(View view) {

        Runnable runnable = new Runnable() {
            public void run() {
                long endTime = System.currentTimeMillis() + 20 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime - System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                handler.sendEmptyMessage(0);
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("myKey", "Thread Completed");
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        };

        Thread myThread = new Thread(runnable);
        myThread.start();
    }
*/
}