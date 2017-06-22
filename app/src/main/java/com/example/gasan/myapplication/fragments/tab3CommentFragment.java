package com.example.gasan.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gasan.myapplication.R;
import com.example.gasan.myapplication.activities.*;


public class tab3CommentFragment extends Fragment implements View.OnClickListener{

    View rootView;
    Button b1, b2, b3, b4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_comment, container, false);

        b1 = (Button) rootView.findViewById(R.id.buttonSteamWash);
        b2 = (Button) rootView.findViewById(R.id.buttonTestimoni);
        b3 = (Button) rootView.findViewById(R.id.buttonIsuPalsu);
        b4 = (Button) rootView.findViewById(R.id.buttonGreenPalsu);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSteamWash:
                Intent SteamWash = new Intent(v.getContext(), SteamWashActivity.class);
                startActivity(SteamWash);
                break;
            case R.id.buttonTestimoni:
                Intent Testimoni = new Intent(v.getContext(), TestimoniActivity.class);
                startActivity(Testimoni);
                break;
            case R.id.buttonIsuPalsu:
                Intent IsuPalsu = new Intent(v.getContext(), IsuPalsuActivity.class);
                startActivity(IsuPalsu);
                break;
            case R.id.buttonGreenPalsu:
                Intent GreenPalsu = new Intent(v.getContext(), GreenPalsuActivity.class);
                startActivity(GreenPalsu);
                break;
        }
    }

}
