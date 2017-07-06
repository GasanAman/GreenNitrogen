package com.example.gasan.myapplication.fragments;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gasan.myapplication.JustifiedTextView;
import com.example.gasan.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnitKerjaStrategisFragment extends Fragment {

    View rootView;
    JustifiedTextView bs2, cs2, cs3, os2, cos2, ls2;

    public UnitKerjaStrategisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_unit_kerja_strategis, container, false);
        getActivity().setTitle(R.string.unit_kerja_bisnis);
        bs2=(JustifiedTextView) rootView.findViewById(R.id.bussines_strategic2);
        bs2.setText(getResources().getString(R.string.bussiness_strategic2));
        bs2.setAlignment(Paint.Align.LEFT);

        cs2=(JustifiedTextView) rootView.findViewById(R.id.client_strategic2);
        cs2.setText(getResources().getString(R.string.client_strategic2));
        cs2.setAlignment(Paint.Align.LEFT);

        cs3=(JustifiedTextView) rootView.findViewById(R.id.client_strategic3);
        cs3.setText(getResources().getString(R.string.client_strategic3));
        cs3.setAlignment(Paint.Align.LEFT);

        os2=(JustifiedTextView) rootView.findViewById(R.id.operation_strategic2);
        os2.setText(getResources().getString(R.string.operation_strategic2));
        os2.setAlignment(Paint.Align.LEFT);

        cos2=(JustifiedTextView) rootView.findViewById(R.id.controlling_strategic2);
        cos2.setText(getResources().getString(R.string.controlling_strategic2));
        cos2.setAlignment(Paint.Align.LEFT);

        ls2=(JustifiedTextView) rootView.findViewById(R.id.learning_strategic2);
        ls2.setText(getResources().getString(R.string.learning_strategic2));
        ls2.setAlignment(Paint.Align.LEFT);
        return rootView;
    }

}
