package com.example.gasan.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gasan.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GaransiFragment extends Fragment {


    public GaransiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(R.string.garansi);
        return inflater.inflate(R.layout.fragment_garansi, container, false);
    }

}
