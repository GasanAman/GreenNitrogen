package com.example.gasan.myapplication.fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.gasan.myapplication.R;


public class tab2PromoFragment extends Fragment {


    public tab2PromoFragment() {
        // Required empty public constructor
    }

    View rootView;
    WebView webPromo;
    ProgressDialog prDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_promo, container, false);
        webPromo = (WebView)rootView.findViewById(R.id.webpromo);
        WebSettings ws2 = webPromo.getSettings();
        ws2.setJavaScriptEnabled(true);
        webPromo.loadUrl("https://green-nitrogen.com/website/info_mobile/promo.html");
        webPromo.setWebViewClient(new LihatAplikasi());
        return rootView;
    }

    private class LihatAplikasi extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            // TODO: Implement this method
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            prDialog = new ProgressDialog(getActivity());
            prDialog.setMessage("Harap Menunggu ...");
            prDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (prDialog != null) {
                prDialog.dismiss();
            }
        }
    }
}