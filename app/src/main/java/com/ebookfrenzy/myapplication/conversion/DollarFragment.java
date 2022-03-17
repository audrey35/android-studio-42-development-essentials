package com.ebookfrenzy.myapplication.conversion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebookfrenzy.myapplication.databinding.FragmentDollarBinding;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DollarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DollarFragment extends Fragment {

    private FragmentDollarBinding binding;
    private ConversionViewModel cViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DollarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DollarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DollarFragment newInstance(String param1, String param2) {
        DollarFragment fragment = new DollarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get reference to the MainViewModel instance
        cViewModel = new ViewModelProvider(this).get(ConversionViewModel.class);

        // setting the onClickListener for the convertButton
        binding.convertButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // set ViewModel's dollarText amount with current value in dollarText view
                cViewModel.setAmount(String.format(Locale.ENGLISH,"%s",
                        binding.dollarText.getText()));
            }
        });

        // Inflate the layout for this fragment
        binding = FragmentDollarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}