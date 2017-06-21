package com.example.gasan.myapplication.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gasan.myapplication.JustifiedTextView;
import com.example.gasan.myapplication.R;
import com.example.gasan.myapplication.app.AppController;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class KerjaSamaBisnisFragment extends Fragment  implements AdapterView.OnItemSelectedListener {

    View rootView;
    Bitmap bitmap;
    ImageView imageView;
    JustifiedTextView mJTv;
    int success;
    int PICK_IMAGE_REQUEST = 1;
    private String KEY_IMAGE = "ktp";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SUCCESS = "success";
    String tag_json_obj = "json_obj_req";
    EditText inputNama, inputAlamat, inputNoHP, inputEmail, inputInstansi, inputInfo, inputAlasan, inputMengenal, inputLamaMengenal, inputOutletDikunjungi, inputBandrex, inputUsaha, inputKritik;
    Button uploadKTP, submit;

    private Uri fileUri; // file url to store image/video
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "KTP";

    private String UPLOAD_URL = "http://10.0.3.2/input_kerjasama.php";

    public KerjaSamaBisnisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_kerja_sama_bisnis, container, false);
        getActivity().setTitle(R.string.kerja_sama_bisnis);

        inputNama = (EditText) rootView.findViewById(R.id.inputnamaKerjasama);
        inputAlamat = (EditText) rootView.findViewById(R.id.inputalamatKerjasama);
        inputNoHP = (EditText) rootView.findViewById(R.id.inputnohpKerjasama);
        inputEmail = (EditText) rootView.findViewById(R.id.inputemailKerjasama);
        inputInstansi = (EditText) rootView.findViewById(R.id.inputinstansiKerjasama);
        inputInfo = (EditText) rootView.findViewById(R.id.inputinfo);
        inputAlasan = (EditText) rootView.findViewById(R.id.inputalasan);
        inputMengenal = (EditText) rootView.findViewById(R.id.inputmengenal);
        inputLamaMengenal = (EditText) rootView.findViewById(R.id.input_brp_lama_mengenal);
        inputOutletDikunjungi = (EditText) rootView.findViewById(R.id.input_outlet_dikunjungi);
        inputBandrex = (EditText) rootView.findViewById(R.id.input_bandrex);
        inputUsaha = (EditText) rootView.findViewById(R.id.input_usaha);
        inputKritik = (EditText) rootView.findViewById(R.id.input_kritik_saran);

        uploadKTP = (Button) rootView.findViewById(R.id.buttonUploadFoto);
        submit = (Button) rootView.findViewById(R.id.buttonSubmitKerjaSama) ;

        imageView = (ImageView) rootView.findViewById(R.id.ImageViewKTP) ;

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

        uploadKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add Photo!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item){
                        if (options[item].equals("Take Photo")){
                            cameraIntent(2);
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                            startActivityForResult(intent, 2);
                        } else if (options[item].equals("Choose from Gallery")){
                            showFileChooser(1);
                        } else if (options[item].equals("Cancel")){
                            dialog.dismiss();
                        }


                    }


                });
                builder.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

//                if(cKeterangan.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(getActivity().getApplicationContext(), "Keterangan Harap Disi", Toast.LENGTH_LONG).show();
//                }else{
                uploadImage();


            }
        });
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

    private void cameraIntent(int req_code)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, req_code);
    }

//    private void kosong(){
//        imageView.setImageResource(0);
//        TextView notif =  (TextView) rootView.findViewById(R.id.cNotifSukses);
//        notif.setVisibility(View.VISIBLE);
//        notif.setText(Html.fromHtml(" <b>SELAMAT Anda Berhasil Melaporkan Setoran Slip Bank Transfer</b><br/>" +
//                " <p>Outlet "+cIdOutlet.getText().toString()+" " +
//                " Tanggal "+dTglSetoran.getText().toString()+" " +
//                " Keterangan " +cKeterangan.getText().toString()+"</p>"+
//                " <p><b>Terima Kasih sudah melaporkan , Semoga Setoran Selanjutnya lebih baik dari ini :)</b></p>"
//        ));
//    }

    private void uploadImage(){
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Upload KTP...", "Harap Tunggu...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.d("v Add", jObj.toString());

                                Toast.makeText(getActivity(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

//                                kosong();

                            } else {
                                Toast.makeText(getActivity(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                        //menampilkan toast
                        Toast.makeText(getActivity(),"Kapasitas Gambar Terlalu Besar", Toast.LENGTH_LONG).show();
                        Log.d(TAG, error.getMessage().toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String,String> params = new HashMap<String, String>();

                int nhFotoSlip = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                Bitmap scaledFotoSlip = Bitmap.createScaledBitmap(bitmap, 512, nhFotoSlip, true);


                //menambah parameter yang di kirim ke web servis
                params.put("nama",inputNama.getText().toString().trim());
                params.put("alamat",inputAlamat.getText().toString().trim());
                params.put("no_hp",inputNoHP.getText().toString().trim());
                params.put("email",inputEmail.getText().toString().trim());
                params.put("instansi",inputInstansi.getText().toString().trim());
                params.put("informasi",inputInfo.getText().toString().trim());
                params.put("alasan",inputAlasan.getText().toString().trim());
                params.put("mengenal",inputMengenal.getText().toString().trim());
                params.put("lama_mengenal",inputLamaMengenal.getText().toString().trim());
                params.put("outlet",inputOutletDikunjungi.getText().toString().trim());
                params.put("layanan_bandrex",inputBandrex.getText().toString().trim());
                params.put("tanya_usaha",inputUsaha.getText().toString().trim());
                params.put("kritik",inputKritik.getText().toString().trim());
                params.put(KEY_IMAGE, BitMapToString(scaledFotoSlip));



                //kembali ke parameters
                Log.d(TAG, ""+params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK ) {


                if (requestCode == 1) {
                    //mengambil fambar dari Gallery
                    try {
                        Uri filePath = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                        imageView.setImageBitmap(scaled);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (requestCode == 2) {
                    previewCapturedImage();
//                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//                    bitmap = (Bitmap) data.getExtras().get("data");
//                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//
//                    File destination = new File(android.os.Environment.getExternalStorageDirectory(),
//                            System.currentTimeMillis() + ".jpg");
//                    FileOutputStream fo;
//                    try {
//                        destination.createNewFile();
//                        fo = new FileOutputStream(destination);
//                        fo.write(bytes.toByteArray());
//                        fo.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

//                    imageView.setImageBitmap(bitmap);
//                    onCaptureImageResult(data);
                }
//                else if (requestCode == 2) {
//                    //Ambil Foto
//                    File f = new File(Environment.getExternalStorageDirectory().toString());
//                    for (File temp : f.listFiles()) {
//                        if (temp.getName().equals("temp.jpg")) {
//                            f = temp;
//                            break;
//                        }
//                    }
//                }
                //menampilkan gambar yang dipilih dari gallery ke ImageView

        }
    }

    private void previewCapturedImage() {
        try {
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 5;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
//            int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
            imageView.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(thumbnail);
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}
