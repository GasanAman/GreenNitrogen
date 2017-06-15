package com.example.gasan.myapplication.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gasan.myapplication.JustifiedTextView;
import com.example.gasan.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class KerjaSamaBisnisFragment extends Fragment {

    View rootView;
    Bitmap bitmap;
    ImageView imageView;
    JustifiedTextView mJTv;
    int success;
    int PICK_IMAGE_REQUEST = 1;

    public KerjaSamaBisnisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_kerja_sama_bisnis, container, false);
        getActivity().setTitle(R.string.kerja_sama_bisnis);
        LayoutInflater inflater1 = getLayoutInflater(null);
        View alertLayout = inflater1.inflate(R.layout.alert_dialog_kerjasama_bisnis, null);

        mJTv = (JustifiedTextView) alertLayout.findViewById(R.id.justifyTextAlert);
        mJTv.setText(getResources().getString(R.string.info_green_nitrogen));
        mJTv.setAlignment(Paint.Align.LEFT);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(rootView.getContext());
        alertDialog.setTitle(R.string.informasi);
        alertDialog.setIcon(R.drawable.ic_pref_info);
        alertDialog.setView(alertLayout);
        alertDialog.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
        return rootView;
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=null;
        try{
            System.gc();
            temp=Base64.encodeToString(b, Base64.DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
        }catch(OutOfMemoryError e){
            baos=new  ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50, baos);
            b=baos.toByteArray();
            temp=Base64.encodeToString(b, Base64.DEFAULT);
            Log.e("EWN", "Out of memory error catched");
        }
        return temp;
    }

    private void showFileChooser(int req_code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), req_code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {


                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    imageView.setImageBitmap(scaled);

                //mengambil fambar dari Gallery


                //menampilkan gambar yang dipilih dari gallery ke ImageView
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
