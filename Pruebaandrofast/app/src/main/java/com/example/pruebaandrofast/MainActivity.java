package com.example.pruebaandrofast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nileshp.multiphotopicker.photopicker.activity.PickImageActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mensaje;
    private Button subir, seleccionar;
    private EditText nombre;
    private ProgressDialog dialog = null;
    private JSONObject jsonObject;

    ArrayList<String> listaImagenes;
    ArrayList<String> listaRuta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subir = (Button)findViewById(R.id.btnSubir);
        seleccionar = (Button)findViewById(R.id.btnImagen);
        mensaje = (TextView)findViewById(R.id.txtMensaje);
        nombre = (EditText)findViewById(R.id.etxtNombre);

        seleccionar.setOnClickListener(this);
        subir.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("\n" +"Subiendo imagen...");
        dialog.setCancelable(false);

        jsonObject = new JSONObject();
        listaImagenes = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnImagen:

                Intent mIntent = new Intent(this, PickImageActivity.class);
                mIntent.putExtra(PickImageActivity.KEY_LIMIT_MAX_IMAGE, 60);
                mIntent.putExtra(PickImageActivity.KEY_LIMIT_MIN_IMAGE, 1);
                startActivityForResult(mIntent, PickImageActivity.PICKER_REQUEST_CODE);

                break;
            case R.id.btnSubir:
                dialog.show();

                JSONArray jsonArray = new JSONArray();

                if (listaImagenes.isEmpty()){
                    Toast.makeText(this, "\n" +"Seleccione algunas imágenes primero.", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (String encoded: listaImagenes){
                    jsonArray.put(encoded);
                }
                try {
                    //jsonObject.put(Util.imagenNombre, nombre.getText().toString().trim());
                    jsonObject.put(Util.imagenNombre, "2019/97635");
                    jsonObject.put(Util.imagenLista, jsonArray);
                } catch (JSONException e) {
                    Log.e("JSONObject aqui", e.toString());
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Util.urlUpload, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.e("Mensaje del servidor", jsonObject.toString());
                                dialog.dismiss();
                                mensaje.setText("\n" +"Imágenes cargadas con éxito");
                                Toast.makeText(getApplicationContext(), "Imágenes cargadas con éxito", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("\n" +"Mensaje del servidor", volleyError.toString());
                        Toast.makeText(getApplicationContext(), "\n" +"Se produjo un error", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 200*30000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(this).add(jsonObjectRequest);
                break;
        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (resultCode == -1 && requestCode == PickImageActivity.PICKER_REQUEST_CODE) {
            this.listaRuta = intent.getExtras().getStringArrayList(PickImageActivity.KEY_DATA_RESULT);
            if (this.listaRuta != null && !this.listaRuta.isEmpty()) {
                StringBuilder sb=new StringBuilder("");

                listaImagenes.clear();


                for(int i = 0; i< listaRuta.size(); i++) {


                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),  Uri.fromFile(new File(listaRuta.get(i))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                    listaImagenes.add(encodedImage);


                    sb.append("Foto"+(i+1)+":"+ listaRuta.get(i));
                    sb.append("\n");
                }
                //escribir subir las imagenes.
                mensaje.setText("\n" +"PULSA BOTON SUBIR !!!");
            }
        }
    }
}