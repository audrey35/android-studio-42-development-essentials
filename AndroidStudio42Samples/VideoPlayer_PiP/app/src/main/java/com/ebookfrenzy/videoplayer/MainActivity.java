package com.ebookfrenzy.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;
import android.net.Uri;
import android.widget.MediaController;
import android.util.Log;
import android.media.MediaPlayer;
import android.app.PictureInPictureParams;
import android.util.Rational;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.graphics.drawable.Icon;

import java.util.ArrayList;

import com.ebookfrenzy.videoplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    private BroadcastReceiver receiver;
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

    public void enterPipMode(View view) {

        Rational rational = new Rational(binding.videoView1.getWidth(),
                binding.videoView1.getHeight());

        PictureInPictureParams params =
                new PictureInPictureParams.Builder()
                        .setAspectRatio(rational)
                        .build();

        binding.pipButton.setVisibility(View.INVISIBLE);
        binding.videoView1.setMediaController(null);
        enterPictureInPictureMode(params);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);

        if (isInPictureInPictureMode) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(
                    "com.ebookfrenzy.videoplayer.VIDEO_INFO");

            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context,
                                      Intent intent) {
                    Toast.makeText(context,
                            "Favorite Home Movie Clips",
                            Toast.LENGTH_LONG).show();
                }
            };

            registerReceiver(receiver, filter);
            createPipAction();
        } else {
            binding.pipButton.setVisibility(View.VISIBLE);
            binding.videoView1.setMediaController(mediaController);
            if (receiver != null) {
                unregisterReceiver(receiver);
            }
        }
    }

    private void createPipAction() {

        final ArrayList<RemoteAction> actions = new ArrayList<>();

        Intent actionIntent =
                new Intent("com.ebookfrenzy.videoplayer.VIDEO_INFO");

        final PendingIntent pendingIntent =
                PendingIntent.getBroadcast(MainActivity.this,
                        REQUEST_CODE, actionIntent, 0);

        final Icon icon =
                Icon.createWithResource(MainActivity.this,
                        R.drawable.ic_info_24dp);
        RemoteAction remoteAction = new RemoteAction(icon, "Info",
                "Video Info", pendingIntent);

        actions.add(remoteAction);

        PictureInPictureParams params =
                new PictureInPictureParams.Builder()
                        .setActions(actions)
                        .build();

        setPictureInPictureParams(params);
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