package com.example.appfacturas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etcif, etrazon, etnumero, etcodigo;
    Button btsiguiente, btaceptar, btcambiar;
    TextView txtcif, txtrazon, txtnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etcif = (EditText)findViewById(R.id.etcif);
        etrazon = (EditText)findViewById(R.id.etrazon);
        etnumero = (EditText)findViewById(R.id.etnumero);
        etcodigo = (EditText)findViewById(R.id.etcodigo);

        btsiguiente = (Button) findViewById(R.id.btsiguiente);
        btaceptar = (Button) findViewById(R.id.btaceptar);
        btcambiar = (Button) findViewById(R.id.btcambiar);

        txtcif = (TextView) findViewById(R.id.txtcif);
        txtrazon = (TextView) findViewById(R.id.txtrazon);
        txtnum = (TextView) findViewById(R.id.txtnum);

        txtcif.setVisibility(View.INVISIBLE);
        txtrazon.setVisibility(View.INVISIBLE);
        txtnum.setVisibility(View.INVISIBLE);
        etcif.setVisibility(View.INVISIBLE);
        etrazon.setVisibility(View.INVISIBLE);
        etnumero.setVisibility(View.INVISIBLE);
        btsiguiente.setVisibility(View.INVISIBLE);

        btcambiar.setEnabled(false);

    }

    public void aceptar (View view){

        if (etcodigo.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Debes introducir un código", Toast.LENGTH_LONG).show();
        }
        else {

            etcodigo.setEnabled(false);
            txtcif.setVisibility(View.VISIBLE);
            txtrazon.setVisibility(View.VISIBLE);
            txtnum.setVisibility(View.VISIBLE);
            etcif.setVisibility(View.VISIBLE);
            etrazon.setVisibility(View.VISIBLE);
            etnumero.setVisibility(View.VISIBLE);
            btsiguiente.setVisibility(View.VISIBLE);
            btcambiar.setEnabled(true);
            etcif.setEnabled(true);
            etrazon.setEnabled(true);
            etnumero.setEnabled(true);
            btsiguiente.setEnabled(true);

        }
    }

    public void cambiar (View view){

        etcodigo.setEnabled(true);
        etcodigo.setText("");
        etcif.setEnabled(false);
        etrazon.setEnabled(false);
        etnumero.setEnabled(false);
        btsiguiente.setEnabled(false);

    }

    public void siguiente (View view){

        if(etcif.getText().length()==0 || etnumero.getText().length()==0 || etrazon.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(MainActivity.this, Activity2.class);
            intent.putExtra("codigo", etcif.getText().toString());
            intent.putExtra("numero", etnumero.getText().toString());
            intent.putExtra("razon", etrazon.getText().toString());
            startActivity(intent);
        }
    }

}
