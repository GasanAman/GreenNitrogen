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

/**
 * A simple {@link Fragment} subclass.
 */
public class AwardFragment extends Fragment {


    public AwardFragment() {
        // Required empty public constructor
    }
    View rootView;
    ProgressDialog prDialog;
    private WebView webAward;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_award, container, false);
        getActivity().setTitle(getString(R.string.award));
        webAward = (WebView) rootView.findViewById(R.id.webaward);
        WebSettings ws1 = webAward.getSettings();
        ws1.setJavaScriptEnabled(true);
        webAward.loadUrl("https://green-nitrogen.com/website/info_mobile/award.html");
        webAward.setWebViewClient(new LihatAplikasi());

        return rootView;
    }



    private class LihatAplikasi extends WebViewClient
    {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
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
