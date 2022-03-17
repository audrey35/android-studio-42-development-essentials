package com.ebookfrenzy.myapplication.conversion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebookfrenzy.myapplication.databinding.FragmentEuroBinding;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EuroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EuroFragment extends Fragment {

    private FragmentEuroBinding binding;
    private ConversionViewModel cViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EuroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EuroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EuroFragment newInstance(String param1, String param2) {
        EuroFragment fragment = new EuroFragment();
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

        // set euroText with value from view model, which comes from dollar fragment
        binding.euroText.setText(String.format(Locale.ENGLISH,"%.2f",
                cViewModel.getResult()));

        // Inflate the layout for this fragment
        binding = FragmentEuroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}