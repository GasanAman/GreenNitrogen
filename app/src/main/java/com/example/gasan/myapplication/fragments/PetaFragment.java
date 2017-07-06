package com.example.gasan.myapplication.fragments;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gasan.myapplication.Category;
import com.example.gasan.myapplication.R;
import com.example.gasan.myapplication.ServiceHandler;
import com.example.gasan.myapplication.activities.MapsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PetaFragment extends ListFragment implements AdapterView.OnItemSelectedListener {

    View rootView;

    private static String link_url_area = "https://green-nitrogen.com/web/view_area_outlet";
    private static String link_url_outlet = "https://green-nitrogen.com/web/view_outlet_per_area";
    private static final String cIdOutlet = "id_outlet";
    private static final String cKodeOutlet = "kode";
    private static final String cNoSpbu = "no_spbu";
    private static final String cNamaOutlet = "nama";
    private static final String cLatitude = "latitude";
    private static final String cLongitude = "longitude";

    private Spinner spinnerArea;
    String url, success;

    JSONArray listOutlet = null;
    ArrayList<Category> daftar_listOutlet = new ArrayList<Category>();
    ArrayList<HashMap<String, String>> daftar_listOutlet2 = new ArrayList<HashMap<String, String>>();
    private ProgressDialog pDialog;

    public PetaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_peta, container, false);
        getActivity().setTitle(R.string.peta_outlet_green_nitrogen);
        spinnerArea = (Spinner) rootView.findViewById(R.id.spinnerArea);
//        spinnerArea.setOnItemSelectedListener(this);
        spinnerArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                new GetOutlet().execute();
                if (position !=0) {


                    new GetOutlet().execute();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        new GetArea().execute();
//        new GetOutlet().execute();
        return rootView;
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        lables.add("");
        for (int i = 0; i < daftar_listOutlet.size(); i++) {
            lables.add(daftar_listOutlet.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new
                ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerArea.setAdapter(spinnerAdapter);
    }

    public void adapter_listview() {

        ListAdapter adapter = new SimpleAdapter(rootView.getContext(), daftar_listOutlet2,
                R.layout.list_item_area,
                new String[] { cNamaOutlet, cIdOutlet, cLatitude, cLongitude}, new int[] {
                R.id.cNamaAreaLayout, R.id.kode, R.id.latitude, R.id.longitude});


        setListAdapter(adapter);
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String id_outlet    = ((TextView) view.findViewById(R.id.kode)).getText().toString();
                String nama         = ((TextView) view.findViewById(R.id.cNamaAreaLayout)).getText().toString();
                String latitude     = ((TextView) view.findViewById(R.id.latitude)).getText().toString();
                String longitude    = ((TextView) view.findViewById(R.id.longitude)).getText().toString();
                Intent in           = new Intent(view.getContext(), MapsActivity.class);
                in.putExtra("id_outlet_intent", id_outlet);
                in.putExtra("nama_outlet_area", nama);
                in.putExtra("latitude_intent", latitude);
                in.putExtra("longitude_intent", longitude);
                startActivity(in);

            }
        });
    }

    private class GetArea extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Harap Menunggu..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(link_url_area, ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("wilayah");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            Category cat = new Category(catObj.getInt("id_wilayah"),
                                    catObj.getString("nama_wilayah"));
                            daftar_listOutlet.add(cat);
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
            populateSpinner();
        }

    }

    public class GetOutlet extends AsyncTask<String, String, String>
    {
        ProgressDialog pDialog;
//        String spinnerAreaText = spinnerArea.getSelectedItem().toString();
        String id_area = spinnerArea.getSelectedItem().toString();
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(rootView.getContext());
            pDialog.setMessage("Harap Menunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }
        @Override

        protected String doInBackground(String... arg0) {
            daftar_listOutlet2.clear();
            ServiceHandler jParser = new ServiceHandler();
//            String json = jParser.makeServiceCall(link_url_area, ServiceHandler.GET);
            String json = jParser.makeServiceCall(link_url_outlet+"?id_wilayah="+id_area, ServiceHandler.GET);
            try {
                JSONObject jsonObj = new JSONObject(json);
                JSONArray hasil = jsonObj.getJSONArray("outlet");
                for (int i = 0; i < hasil.length(); i++) {

                    JSONObject c = hasil.getJSONObject(i);

                    String id_outlet = c.getString("id_outlet").trim();
                    String kode      = c.getString("kode").trim();
                    String no_spbu   = c.getString("no_spbu").trim();
                    String nama      = c.getString("nama").trim();
                    String latitude      = c.getString("latitude").trim();
                    String longitude      = c.getString("longitude").trim();
//                    String alamat      = c.getString("alamat").trim();

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("id_outlet", id_outlet);
                    map.put("kode", kode);
                    map.put("no_spbu", no_spbu);
                    map.put("nama",nama);
                    map.put("latitude",latitude);
                    map.put("longitude",longitude);
                    daftar_listOutlet2.add(map);

                    Log.e("ok", " ambil data");

                }

            } catch (Exception e) {
                // TODO: handle exception
                Log.e("erro", "tidak bisa ambil data 1");
            }

            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pDialog.dismiss();
            adapter_listview();
        }

    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}

