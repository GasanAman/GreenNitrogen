package com.example.gasan.myapplication.activities;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.gasan.myapplication.R;
import com.example.gasan.myapplication.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AreaOutletActvity extends AppCompatActivity   {

    private static String link_url = "http://green-nitrogen.com/nitrogen_android/web/view_area_outlet";
    private static final String cIdWilayah = "id_wilayah";
    private static final String cNoWilayah = "no_wilayah";
    private static final String cNamaWilayah = "nama_wilayah";
    private static final String cUrl = "url";
    ListView listView;

    ArrayList<HashMap<String, String>> daftar_listOutlet = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_outlet_actvity);

        android.support.v7.widget.Toolbar toolbar1 = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Section Green Nitrogen Palsu");
        listView = (ListView) findViewById(R.id.listview);
//        ImageView back = (ImageView) findViewById(R.id.imageButtonArea);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////                Intent intent = new Intent(AreaOutletActvity.this, MainActivity.class);
//                startActivity(new Intent(AreaOutletActvity.this, MainActivity.class));
//                finish();
//            }
//        });
        new GetArea().execute();
    }

    public class GetArea extends AsyncTask<String, String, String>
    {
        ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(AreaOutletActvity.this);
            pDialog.setMessage("Harap Menunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override

        protected String doInBackground(String... arg0) {

            ServiceHandler jParser = new ServiceHandler();
            String json = jParser.makeServiceCall(link_url, ServiceHandler.GET);
            try {
                JSONObject jsonObj = new JSONObject(json);
                JSONArray hasil = jsonObj.getJSONArray("wilayah");
                for (int i = 0; i < hasil.length(); i++) {

                    JSONObject c = hasil.getJSONObject(i);

                    String id_wilayah       = c.getString("id_wilayah").trim();
                    String no_wilayah       = c.getString("no_wilayah").trim();
                    String nama_wilayah     = c.getString("nama_wilayah").trim();
                    String url_wilayah      = c.getString("url").trim();

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("id_wilayah", id_wilayah);
                    map.put("no_wilayah", no_wilayah);
                    map.put("nama_wilayah", nama_wilayah);
                    map.put("url",url_wilayah);
                    daftar_listOutlet.add(map);

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



    public void adapter_listview() {

        ListAdapter adapter = new SimpleAdapter(this, daftar_listOutlet,
                R.layout.list_item_area,
                new String[] { cNamaWilayah, cNoWilayah}, new int[] {
                R.id.cNamaAreaLayout, R.id.kode});


        listView.setAdapter(adapter);
//        ListView lv = listView.get();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kode = ((TextView) view.findViewById(R.id.kode)).getText().toString();
                String cNamaOutletListView = ((TextView) view.findViewById(R.id.cNamaAreaLayout)).getText().toString();

                Intent in = new Intent(AreaOutletActvity.this, MapsActivity.class);
                in.putExtra("id_outlet_intent", kode);
                in.putExtra("nama_outlet_intent", cNamaOutletListView);
                startActivity(in);

            }
        });
    }

}
