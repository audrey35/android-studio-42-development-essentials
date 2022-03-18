package com.ebookfrenzy.viewmodeldemo.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebookfrenzy.viewmodeldemo.R;
import com.ebookfrenzy.viewmodeldemo.databinding.MainFragmentBinding;

import java.util.Locale;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private MainFragmentBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // setting the MainFragment to use view binding
        binding = MainFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get reference to ViewModel
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // replace set resultText, handled by resultObserver's onChanged method
        // new observer instance
        final Observer<Float> resultObserver = new Observer<Float>() {

            // observing data change
            @Override
            public void onChanged(@Nullable final Float result) {
                binding.resultText.setText(String.format(Locale.ENGLISH,
                        "%.2f", result));
            }
        };

        // start observing for changes in result variable of the ViewModel
        mViewModel.getResult().observe(getViewLifecycleOwner(), resultObserver);

        // called when user clicks convertButton
        binding.convertButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                // check dollarText isn't empty
                if (!binding.dollarText.getText().toString().equals("")) {
                    // set ViewModel's dollarText amount with current value in dollarText view
                    mViewModel.setAmount(String.format(Locale.ENGLISH,"%s",
                            binding.dollarText.getText()));

                    // deleted line for setting resultText with updated value,
                    // because this is handled by resultObserver's onChange method
                } else {
                    // if dollarText is empty, display No value on resultText view
                    binding.resultText.setText("No Value");
                }
            }
        });
    }

}