package com.example.gasan.myapplication.fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gasan.myapplication.R;
import com.example.gasan.myapplication.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HubungiFragment extends Fragment {

    View rootView;
    ProgressDialog pDialog;
    EditText inputnama, inputnohp, inputemail, kolomPesan;
    Button submit;

    private String URL_NEW_CATEGORY = "http://10.0.3.2/input_hubungi.php";

    public HubungiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_hubungi, container, false);
        getActivity().setTitle(getString(R.string.hubungi_kami));

        inputnama = (EditText) rootView.findViewById(R.id.inputnama);
        inputnohp = (EditText) rootView.findViewById(R.id.inputnohp);
        inputemail = (EditText) rootView.findViewById(R.id.inputemail);
        kolomPesan = (EditText) rootView.findViewById(R.id.kolomPesan);

        submit = (Button) rootView.findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                new HubungiFragment.SaveData().execute();
            }
        });

        return rootView;
    }



    private class SaveData extends AsyncTask<String, String, String> {

        boolean isNewCategoryCreated = false;

        String kolomPesanParam      = kolomPesan.getText().toString();
        String inputNamaParam       = inputnama.getText().toString();
        String inputNoHpParam       = inputnohp.getText().toString();
        String inputEmailParam      = inputemail.getText().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(rootView.getContext());
            pDialog.setMessage("Menyimpan Data.");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... arg) {


            // Preparing post params
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nama", inputNamaParam));
            params.add(new BasicNameValuePair("no_handphone", inputNoHpParam));
            params.add(new BasicNameValuePair("email", inputEmailParam));
            params.add(new BasicNameValuePair("pesan", kolomPesanParam));

            ServiceHandler serviceClient = new ServiceHandler();

            String json = serviceClient.makeServiceCall(URL_NEW_CATEGORY,
                    ServiceHandler.POST, params);

            Log.d("Create Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    boolean error = jsonObj.getBoolean("error");
                    // checking for error node in json
                    if (!error) {
                        // new category created successfully
                        isNewCategoryCreated = true;
                    } else {
                        Log.e("Create Category Error: ", "> " + jsonObj.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }


}
