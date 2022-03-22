package com.ebookfrenzy.applinking;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ebookfrenzy.applinking.databinding.ActivityAppLinkingBinding;

public class AppLinkingActivity extends AppCompatActivity {

    private ActivityAppLinkingBinding binding;
    public static final String LANDMARK_ID = "landmarkID";
    private static final String TAG = "AppIndexActivity";
    private static final int PERSONAL = 1;
    private static final int PUBLIC = 0;
    MyDBHandler dbHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAppLinkingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dbHandler = new MyDBHandler(this, null, null, 1);
    }

    public void findLandmark(View view) {

        if (!binding.idText.getText().equals("")) {
            Landmark landmark = dbHandler.findLandmark(binding.idText.getText().toString());

            if (landmark != null) {

                Intent intent = new Intent(this, LandmarkActivity.class);
                String landmarkid = binding.idText.getText().toString();
                intent.putExtra(LANDMARK_ID, landmarkid);
                startActivity(intent);

                /*
                Log.i(TAG, "Found landmark = " + landmark.getTitle());
                Uri uri = Uri.parse("http://example.com/landmarks/" + landmark.getID());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                */
            } else {
                binding.titleText.setText("No Match");
            }
        }
    }

    public void addLandmark(View view) {

        Landmark landmark =
                new Landmark(binding.idText.getText().toString(), binding.titleText.getText().toString(),
                        binding.descriptionText.getText().toString(), 1);

        dbHandler.addLandmark(landmark);
        binding.idText.setText("");
        binding.titleText.setText("");
        binding.descriptionText.setText("");

    }
}
