package com.example.appfacturas;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Activity2 extends AppCompatActivity {

    EditText etdescripcion, etbase, etiva, ettotal, etfechafactura, etfechavencimiento;
    Button btcrear, btvolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etdescripcion = (EditText) findViewById(R.id.etdescripcion);
        etbase = (EditText) findViewById(R.id.etbase);
        etiva = (EditText) findViewById(R.id.etiva);
        ettotal = (EditText) findViewById(R.id.ettotal);
        etfechafactura = (EditText) findViewById(R.id.etfechafactura);
        etfechavencimiento = (EditText) findViewById(R.id.etfechavencimiento);

        btcrear = (Button) findViewById(R.id.btcrear);
        btvolver = (Button) findViewById(R.id.btvolver);

    }


    public void crearFicheroBinario() {

        //ME PASO LAS 3 PRIMERAS VARIABLES DEL PRIMER LAYOUT
        String codigo = getIntent().getStringExtra("codigo");
        String numero = getIntent().getStringExtra("numero");
        String razon = getIntent().getStringExtra("razon");
        String descripcion = etdescripcion.getText().toString();
        String base = etbase.getText().toString();
        String iva = etiva.getText().toString();
        String total = ettotal.getText().toString();
        String fechafactura = etfechafactura.getText().toString();
        String fechavencimiento = etfechavencimiento.getText().toString();

        FileOutputStream fos = null;
        DataOutputStream salida = null;


        try {

            fos = new FileOutputStream("factura.bin");
            salida = new DataOutputStream(fos);

            salida.writeUTF(codigo);
            salida.writeUTF(numero);
            salida.writeUTF(razon);
            salida.writeUTF(descripcion);
            salida.writeUTF(base);
            salida.writeUTF(iva);
            salida.writeUTF(total);
            salida.writeUTF(fechafactura);
            salida.writeUTF(fechavencimiento);


        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (salida != null) {
                    salida.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void volver(View view) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(Activity2.this);

        alerta.setMessage("Si vuelve tendrá que empezar la factura de nuevo.")
                .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Activity2.this, MainActivity.class);
                startActivity(intent);
            }
        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog titulo = alerta.create();
        titulo.setTitle("¿Desea volver?");
        titulo.show();


    }

    public void generarFactura(View view) {

        if (etdescripcion.getText().length() == 0 || etbase.getText().length() == 0 || etiva.getText().length() == 0
                || ettotal.getText().length() == 0 || etfechafactura.getText().length() == 0 || etfechavencimiento.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(Activity2.this);

            alerta.setMessage("¿Desea enviar la factura por email?")
                    .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String ficheroBinario = "factura.bin";
                    Uri uri = Uri.fromFile(new
                            File(Environment.getExternalStorageDirectory().getAbsolutePath() + "\\factura.bin", ficheroBinario));
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("application/bin");
                    i.putExtra(Intent.EXTRA_SUBJECT, ficheroBinario);
                    i.putExtra(Intent.EXTRA_STREAM,  uri);
                    startActivity(i);
                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            crearFicheroBinario();
                            Toast.makeText(getApplicationContext(), "Factura guardada correctamente", Toast.LENGTH_LONG).show();
                            //finish();
                        }
                    });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("Factura generada");
            titulo.show();

        }

    }


}
