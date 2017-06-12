package com.example.gasan.myapplication.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gasan.myapplication.R;
import com.example.gasan.myapplication.ServiceHandler;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestimoniActivity extends AppCompatActivity {
    ProgressDialog pDialog;
    EditText inputnama, inputnohp, inputemail, kolomTestimoni;
    Button submit;

    private String URL_NEW_CATEGORY = "http://10.0.3.2/input_testimoni.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testimoni);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputnama = (EditText) findViewById(R.id.inputnamaTestimoni);
        inputnohp = (EditText) findViewById(R.id.inputnohpTestimoni);
        inputemail = (EditText) findViewById(R.id.inputemailTestimoni);
        kolomTestimoni = (EditText) findViewById(R.id.kolomTestimoni);

        ImageButton back = (ImageButton) findViewById(R.id.imageButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(TestimoniActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submit  = (Button) findViewById(R.id.buttonSubmitSteamWash);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputnama.getText().toString().trim().isEmpty()) {
                    Toast.makeText(v.getContext().getApplicationContext(), "Nama Masih Kosong", Toast.LENGTH_LONG).show();
                } else if (inputnohp.getText().toString().trim().isEmpty()) {
                    Toast.makeText(v.getContext().getApplicationContext(), "Nomor HP Masih Kosong", Toast.LENGTH_LONG).show();
                } else if (inputemail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(v.getContext().getApplicationContext(), "Email Masih Kosong", Toast.LENGTH_LONG).show();
                } else if (kolomTestimoni.getText().toString().trim().isEmpty()) {
                    Toast.makeText(v.getContext().getApplicationContext(), "Testimoni Masih Kosong", Toast.LENGTH_LONG).show();
                } else {
                    new SaveData().execute();
                }
            }
        });

    }


    private class SaveData extends AsyncTask<String, String, String> {

        boolean isNewCategoryCreated = false;

        String kolomTestimoniParam  = kolomTestimoni.getText().toString();
        String inputNamaParam       = inputnama.getText().toString();
        String inputNoHpParam       = inputnohp.getText().toString();
        String inputEmailParam      = inputemail.getText().toString();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TestimoniActivity.this);
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
            params.add(new BasicNameValuePair("testimoni", kolomTestimoniParam));

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
