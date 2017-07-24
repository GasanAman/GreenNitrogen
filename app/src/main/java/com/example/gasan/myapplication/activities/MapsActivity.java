package com.example.gasan.myapplication.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gasan.myapplication.R;
import com.example.gasan.myapplication.ServiceHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String id_outlet, wilayah_outlet, ambil_id_outlet, nama_outlet, no_spbu, alamat_outlet, latitude_string, longitude_string, nama_outlet_intent;
    Double latitude1, longitude1;
    TextView wilayah, nama, nomor, alamat;
    Button navigateBtn;
    private static String URL_CATEGORIES = "http://green-nitrogen.com/nitrogen_android/web/view_detail_outlet/";
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.judul_peta_activity);

        Intent i = getIntent();
        id_outlet = i.getStringExtra("id_outlet_intent");
        nama_outlet_intent = i.getStringExtra("nama_outlet_area");
        latitude_string = i.getStringExtra("latitude_intent");
        longitude_string = i.getStringExtra("longitude_intent");
        latitude1 = Double.parseDouble(latitude_string);
        longitude1 = Double.parseDouble(longitude_string);
//        latitude = Double.parseDouble(latitude_string);
//        longitude = Double.parseDouble(longitude_string);
//        ImageButton back = (ImageButton) findViewById(R.id.imageButtonMaps);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
////                startActivity(intent);
//                finish();
//            }
//        });

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.informasi);
        alertDialog.setIcon(R.drawable.ic_pref_info);
        alertDialog.setMessage("Jika anda menemukan lokasi outlet Green Nitrogen tidak tepat di SPBU Petamina pada Maps yang ada, mohon informasikan kepada kami melalui menu Hubungi Kami");
        alertDialog.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

        navigateBtn = (Button) findViewById(R.id.navigateBtn);
        navigateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+latitude1+","+longitude1));
                startActivity(intent);
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        new ViewData().execute();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */



    private class ViewData extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MapsActivity.this);
            pDialog.setMessage("Harap Menunggu..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {



            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CATEGORIES+"?id_outlet="+id_outlet,
                    ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("outlet");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            ambil_id_outlet     = catObj.getString("id_outlet").trim();
                            no_spbu             = catObj.getString("no_spbu").trim();
                            nama_outlet         = catObj.getString("nama").trim();
                            wilayah_outlet      = catObj.getString("wilayah").trim();
                            alamat_outlet       = catObj.getString("alamat").trim();
//                            latitude_string     = catObj.getString("latitude").toString();
//                            longitude_string    = catObj.getString("longitude").toString();
                        }
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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // fetching all categories

                    wilayah = (TextView) findViewById(R.id.nama_wilayah);
                    nama = (TextView) findViewById(R.id.nama_outlet);
                    nomor = (TextView) findViewById(R.id.no_spbu);
                    alamat = (TextView) findViewById(R.id.alamat_outlet);

                    wilayah.setText(wilayah_outlet);
                    nama.setText(nama_outlet);

                    nomor.setText(no_spbu);
                    alamat.setText(alamat_outlet);

                    //cStatus.setText(cStatusParam);
                }
            });

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude1, longitude1);
        mMap.addMarker(new MarkerOptions().position(sydney).title(nama_outlet_intent).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_nitrogen)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f));


    }

}
