package com.ebookfrenzy.dynamicfeature;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.content.IntentSender;

import java.util.Collections;
import java.util.Locale;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitcompat.SplitCompat;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;

import com.ebookfrenzy.dynamicfeature.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SplitInstallManager manager;
    private int mySessionId = 0;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplitCompat.install(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        manager = SplitInstallManagerFactory.create(this);
    }

    public void launchFeature(View view) {
        if (manager.getInstalledModules().contains("my_dynamic_feature")) {
            Intent i = new Intent(
                    "com.ebookfrenzy.my_dynamic_feature.MyFeatureActivity");
            startActivity(i);
        } else {
            binding.statusText.setText("Feature not yet installed");
        }
    }

    public void installFeature(View view) {

        SplitInstallRequest request =
                SplitInstallRequest
                        .newBuilder()
                        .addModule("my_dynamic_feature")
                        .build();

        manager.registerListener(listener);

        manager.startInstall(request)
                .addOnSuccessListener(new OnSuccessListener<Integer>() {
                    @Override
                    public void onSuccess(Integer sessionId) {
                        mySessionId = sessionId;
                        Toast.makeText(MainActivity.this,
                                "Module installation started",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(MainActivity.this,
                                "Module installation failed" + exception.toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onResume() {
        manager.registerListener(listener);
        super.onResume();
    }

    @Override
    public void onPause() {
        manager.unregisterListener(listener);
        super.onPause();
    }

    SplitInstallStateUpdatedListener listener =
            new SplitInstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(SplitInstallSessionState state) {

                    if (state.sessionId() == mySessionId) {
                        switch (state.status()) {

                            case SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION:

                                binding.statusText.setText(
                                        "Large Feature Module. Requesting Confirmation");

                                try {
                                    manager.startConfirmationDialogForResult(state,
                                            MainActivity.this, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException ex) {
                                    binding.statusText.setText("Confirmation Request Failed.");
                                }
                                break;

                            case SplitInstallSessionStatus.DOWNLOADING:
                                long size = state.totalBytesToDownload();
                                long downloaded = state.bytesDownloaded();
                                binding.statusText.setText(String.format(Locale.getDefault(),
                                        "%d of %d bytes downloaded.", downloaded, size));
                                break;

                            case SplitInstallSessionStatus.INSTALLING:

                                binding.statusText.setText("Installing feature");
                                break;

                            case SplitInstallSessionStatus.DOWNLOADED:

                                binding.statusText.setText("Download Complete");
                                break;

                            case SplitInstallSessionStatus.INSTALLED:

                                binding.statusText.setText("Installed - Feature is ready");
                                break;

                            case SplitInstallSessionStatus.CANCELED:

                                binding.statusText.setText("Installation cancelled");
                                break;

                            case SplitInstallSessionStatus.PENDING:

                                binding.statusText.setText("Installation pending");
                                break;

                            case SplitInstallSessionStatus.FAILED:

                                binding.statusText.setText("Installation Failed. Error code: " + state.errorCode());
                        }
                    }
                }
            };

    public void deleteFeature(View view) {
        manager.deferredUninstall(Collections.singletonList("my_dynamic_feature"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                binding.statusText.setText("Beginning Installation.");
            } else {
                binding.statusText.setText("User declined installation.");
            }
        }
    }

}