package com.ebookfrenzy.applinking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ebookfrenzy.applinking.databinding.ActivityLandmarkBinding;

public class LandmarkActivity extends AppCompatActivity {

    private ActivityLandmarkBinding binding;
    private static final String TAG = "Database";
    private Landmark landmark = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLandmarkBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {

        String landmarkId = intent.getStringExtra(AppLinkingActivity.LANDMARK_ID);
        displayLandmark(landmarkId);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }




    public void deleteLandmark(View view) {

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        if (landmark != null) {
            dbHandler.deleteLandmark(landmark.getID());
            binding.titleText.setText("");
            binding.descriptionText.setText("");
            binding.deleteButton.setEnabled(false);
        }
    }

    private void displayLandmark(String landmarkId) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        landmark =
                dbHandler.findLandmark(landmarkId);

        if (landmark != null) {

            if (landmark.getPersonal() == 0) {
                binding.deleteButton.setEnabled(false);
            } else {
                binding.deleteButton.setEnabled(true);
            }

            binding.titleText.setText(landmark.getTitle());
            binding.descriptionText.setText(landmark.getDescription());
        } else {
            binding.titleText.setText("No Match Found");
        }
    }


}
