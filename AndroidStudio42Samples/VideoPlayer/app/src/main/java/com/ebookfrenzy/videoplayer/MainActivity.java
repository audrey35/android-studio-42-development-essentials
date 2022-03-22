package com.ebookfrenzy.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
import android.net.Uri;
import android.widget.MediaController;
import android.util.Log;
import android.media.MediaPlayer;

import com.ebookfrenzy.videoplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private VideoView videoView;
    private MediaController mediaController;
    String TAG = "VideoPlayer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        configureVideoView();
    }

    private void configureVideoView() {

        binding.videoView1.setVideoURI(Uri.parse("android.resource://"
                + getPackageName() + "/" + R.raw.movie));

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        binding.videoView1.setMediaController(mediaController);

        binding.videoView1.start();

        binding.videoView1.setOnPreparedListener(new
                         MediaPlayer.OnPreparedListener()  {
                             @Override
                             public void onPrepared(MediaPlayer mp) {
                                 mp.setLooping(true);
                                 Log.i(TAG, "Duration = " +
                                         binding.videoView1.getDuration());
                             }
                         });


    }

}