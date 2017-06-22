package com.example.gasan.myapplication.fragments;


import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.gasan.myapplication.JustifiedTextView;
import com.example.gasan.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TentangFragment extends Fragment {

    View rootView;
    private JustifiedTextView mJTv, mJTv2, mJTv3;

    public TentangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tentang, container, false);
        getActivity().setTitle(getString(R.string.tentang));
        mJTv=(JustifiedTextView) rootView.findViewById(R.id.paragraf1);
        mJTv.setText(getResources().getString(R.string.paragraf_1));
        mJTv.setAlignment(Paint.Align.LEFT);

        mJTv2=(JustifiedTextView) rootView.findViewById(R.id.paragraf2);
        mJTv2.setText(getResources().getString(R.string.paragraf_2));
        mJTv2.setAlignment(Paint.Align.LEFT);

        mJTv3=(JustifiedTextView) rootView.findViewById(R.id.paragraf3);
        mJTv3.setText(getResources().getString(R.string.paragraf_3));
        mJTv3.setAlignment(Paint.Align.LEFT);

        Typeface fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        TextView fontAwesomeEmailIcon = (TextView) rootView.findViewById(R.id.font_awesome_email_icon);
        fontAwesomeEmailIcon.setTypeface(fontAwesomeFont);
        TextView fontAwesomeFaxIcon = (TextView) rootView.findViewById(R.id.font_awesome_fax_icon);
        fontAwesomeFaxIcon.setTypeface(fontAwesomeFont);
        TextView fontAwesomePhoneIcon = (TextView) rootView.findViewById(R.id.font_awesome_phone_icon);
        fontAwesomePhoneIcon.setTypeface(fontAwesomeFont);
        return rootView;
    }

}
