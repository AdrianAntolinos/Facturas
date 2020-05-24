package com.example.appfacturas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity2 extends AppCompatActivity {

    EditText etdescripcion, etbase, etiva, ettotal, etfechafactura, etfechavencimiento;
    Button btcrear, btvolver;
    String numero, nombreficherobinario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String id = BuildConfig.APPLICATION_ID;
        etdescripcion = (EditText) findViewById(R.id.etdescripcion);
        etbase = (EditText) findViewById(R.id.etbase);
        etiva = (EditText) findViewById(R.id.etiva);
        ettotal = (EditText) findViewById(R.id.ettotal);
        etfechafactura = (EditText) findViewById(R.id.etfechafactura);
        etfechavencimiento = (EditText) findViewById(R.id.etfechavencimiento);
        ///
        String cif = getIntent().getStringExtra("cif");
        numero = getIntent().getStringExtra("numero");
        String razon = getIntent().getStringExtra("razon");
        ///
        btcrear = (Button) findViewById(R.id.btcrear);
        btvolver = (Button) findViewById(R.id.btborrar);

    }


    public void crearFicheroBinario() {

        //ME PASO LAS 3 PRIMERAS VARIABLES DEL PRIMER LAYOUT
        String cif = getIntent().getStringExtra("cif");
        numero = getIntent().getStringExtra("numero");
        String razon = getIntent().getStringExtra("razon");
        String descripcion = etdescripcion.getText().toString();
        String base = etbase.getText().toString();
        String iva = etiva.getText().toString();
        String total = ettotal.getText().toString();
        String fechafactura = etfechafactura.getText().toString();
        String fechavencimiento = etfechavencimiento.getText().toString();
        double basedouble = Double.parseDouble(base);
        double ivadouble = Double.parseDouble(iva);
        double totaldouble = Double.parseDouble(total);

        Date ffactura = stringToDate(fechafactura, null);
        Date fvencimiento = stringToDate(fechavencimiento, null);

        Factura factura = new Factura(cif, razon, numero, descripcion, basedouble, ivadouble, totaldouble, ffactura, fvencimiento);

        nombreficherobinario = "factura" + numero + ".bin";
        FileOutputStream fos = null;
        ObjectOutputStream salida = null;

        try {
            fos = openFileOutput(nombreficherobinario, Context.MODE_PRIVATE);
            salida = new ObjectOutputStream(fos);

            salida.writeObject(factura);

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


    public void borrar(View view) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(Activity2.this);

        alerta.setMessage("¿Desea borrar la factura?")
                .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Activity2.this, MainActivity.class);
                startActivity(intent);
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
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
            //aquí va el sharedpreferences();
            //antes del alertdialog



            AlertDialog.Builder alerta = new AlertDialog.Builder(Activity2.this);

            alerta.setMessage("¿Desea enviar la factura por email?")
                    .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    crearFicheroBinario();
                    enviarMail();
                    dialog.cancel();


                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            crearFicheroBinario();
                            Toast.makeText(getApplicationContext(), "Factura guardada correctamente", Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }
                    });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("Factura generada");
            titulo.show();

        }

    }

    public void enviarMail() {

        File file = new File(getFilesDir(), nombreficherobinario);
        //Uri uridelfile = Uri.fromFile(file);

        Uri uridelfile = FileProvider.getUriForFile(this,
            BuildConfig.APPLICATION_ID+".fileprovider", file);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
         emailIntent.setType("message/rfc822");


        String destinatarios[] = {""};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, destinatarios);

        emailIntent.putExtra(Intent.EXTRA_STREAM, uridelfile);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, nombreficherobinario);
        startActivity(Intent.createChooser(emailIntent, "Elige aplicacion de correo..."));

    }


    public static Date stringToDate(String fechaEnString, String formato) {
        if (fechaEnString == null) {
            return null;
        }
        if (formato == null) {
            formato = "dd/MM/yyyy";
        }
 
        Date fechaenjava = null;
        SimpleDateFormat miFormato2 = new SimpleDateFormat(formato);
        try {
            fechaenjava = miFormato2.parse(fechaEnString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaenjava;
    }


}
