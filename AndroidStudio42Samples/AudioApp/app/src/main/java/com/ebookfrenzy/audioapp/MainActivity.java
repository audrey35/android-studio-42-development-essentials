package com.ebookfrenzy.audioapp;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Toast;
import android.Manifest;

import com.ebookfrenzy.audioapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int RECORD_REQUEST_CODE = 101;
    private static final int STORAGE_REQUEST_CODE = 102;

    private ActivityMainBinding binding;
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;

    private static String audioFilePath;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        audioSetup();
    }

    public void recordAudio (View view)
    {
        isRecording = true;
        binding.stopButton.setEnabled(true);
        binding.playButton.setEnabled(false);
        binding.recordButton.setEnabled(false);

        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(
                    MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
    }

    public void stopAudio (View view)
    {
        binding.stopButton.setEnabled(false);
        binding.playButton.setEnabled(true);

        if (isRecording)
        {
            binding.recordButton.setEnabled(false);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
            binding.recordButton.setEnabled(true);
        }
    }

    public void playAudio (View view) throws IOException
    {
        binding.playButton.setEnabled(false);
        binding.recordButton.setEnabled(false);
        binding.stopButton.setEnabled(true);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioFilePath);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void audioSetup()
    {
        if (!hasMicrophone())
        {
            binding.stopButton.setEnabled(false);
            binding.playButton.setEnabled(false);
            binding.recordButton.setEnabled(false);
        } else {
            binding.playButton.setEnabled(false);
            binding.stopButton.setEnabled(false);
        }

        audioFilePath =
                this.getExternalFilesDir(Environment.DIRECTORY_MUSIC).
                        getAbsolutePath() + "/myaudio.3gp";

        requestPermission(Manifest.permission.RECORD_AUDIO,
                RECORD_REQUEST_CODE);
    }

    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(this,
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionType}, requestCode
            );
        }
    }

    protected boolean hasMicrophone() {
        PackageManager pmanager = this.getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }

}