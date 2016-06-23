package com.example.chris.crud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText EditNombre;
    private EditText EditDescripcion;
    private EditText EditCodigoBarras;
    private RadioButton disponible;

    private RadioButton nodisponible;
    private RadioGroup grupo;

    private Button registrar;
    private Button volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_producto);

        EditNombre = (EditText)findViewById(R.id.CajaNombre);
        EditDescripcion = (EditText)findViewById(R.id.CajaDescripcion);
        EditCodigoBarras = (EditText)findViewById(R.id.CajaCodigoBarras);

        disponible = (RadioButton)findViewById(R.id.rbtnDisponible);
        nodisponible = (RadioButton)findViewById(R.id.rbtnNoDisponible);
        grupo = (RadioGroup) findViewById(R.id.radioGroup);

        registrar = (Button)findViewById(R.id.btnRegistrar);
        volver = (Button)findViewById(R.id.btnVolver);

        registrar.setOnClickListener(this);
        volver.setOnClickListener(this);

        Limpiar();
    }
    private void Limpiar(){
        EditNombre.setText("");
        EditDescripcion.setText("");
        EditCodigoBarras.setText("");
        disponible.setChecked(false);
        disponible.setChecked(false);
    }
    private void AddProducto(){

        final String nombre = EditNombre.getText().toString().trim();
        final String descripcion = EditDescripcion.getText().toString().trim();
        final String codigobarras = EditCodigoBarras.getText().toString().trim();
        final String estado;

        if(grupo.getCheckedRadioButtonId()== R.id.rbtnDisponible){
            estado = "Disponible";
        }else{
            estado = "No Disponible";
        }

        class AddProducto extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Registrando...","Espere...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Conexion.KEY_PRO_NOMBRE,nombre);
                params.put(Conexion.KEY_PRO_DESC,descripcion);
                params.put(Conexion.KEY_PRO_CODBA,codigobarras);
                params.put(Conexion.KEY_PRO_EST,estado);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Conexion.URL_ADD_PRO, params);
                return res;
            }
        }

        AddProducto ap = new AddProducto();
        ap.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == registrar){

            if(EditNombre.getText().length() == 0 || EditDescripcion.getText().length()==0 || EditCodigoBarras.getText().length()==0 )
            {
                Toast.makeText(MainActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
            }
            else{
                AddProducto();
                Limpiar();
            }
        }
        if(v == volver){
            Intent intent = new Intent(MainActivity.this, Listar.class);
            startActivity(intent);
        }

    }
}
