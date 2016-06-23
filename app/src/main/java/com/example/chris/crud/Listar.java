package com.example.chris.crud;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Listar extends AppCompatActivity {
    private ListView listView;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        listView = (ListView) findViewById(R.id.L_Productos);
        getJSON();
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Listar.this,"Preparando Datos","Espere...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                MostrarProducto();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Conexion.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void MostrarProducto(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Conexion.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String Tag_id = jo.getString(Conexion.TAG_ID);
                String Tag_nombre = jo.getString(Conexion.TAG_NOMBRE);
                String Tag_Descripcion = jo.getString(Conexion.TAG_DESCRIPCION);
                String Tag_CodigoBarra = jo.getString(Conexion.TAG_CODIGOBARRA);
                String Tag_Estado = jo.getString(Conexion.TAG_ESTADO);

                String id = "ID: "+ Tag_id;
                String Nombre = "Producto: " + Tag_nombre;
                String Descripcion = "Descripcion :"+ Tag_Descripcion;
                String CodigoBarra = "Codigo de Barra: "+ Tag_CodigoBarra;
                String Estado = "Estado: "+ Tag_Estado;


                HashMap<String,String> producto = new HashMap<>();
                producto.put(Conexion.TAG_ID,Tag_id);
                producto.put(Conexion.TAG_NOMBRE,Nombre);
                producto.put(Conexion.TAG_DESCRIPCION,Descripcion);
                producto.put(Conexion.TAG_CODIGOBARRA,CodigoBarra);
                producto.put(Conexion.TAG_ESTADO,Estado);

                list.add(producto);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Listar.this, list, R.layout.item_lista_productos,
                new String[]{Conexion.TAG_NOMBRE,Conexion.TAG_DESCRIPCION,Conexion.TAG_CODIGOBARRA,Conexion.TAG_ESTADO},
                new int[]{R.id.TextNombre,R.id.TextDescripcion,R.id.TextCodigoBarra,R.id.TextEstado});

        listView.setAdapter(adapter);
    }
}
