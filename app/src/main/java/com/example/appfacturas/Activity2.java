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

import java.io.File;

public class Activity2 extends AppCompatActivity {

    EditText etdescripcion, etbase , etiva, ettotal, etfechafactura, etfechavencimiento;
    Button btcrear, btvolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        etdescripcion = (EditText)findViewById(R.id.etdescripcion);
        etbase = (EditText)findViewById(R.id.etbase);
        etiva = (EditText)findViewById(R.id.etiva);
        ettotal = (EditText)findViewById(R.id.ettotal);
        etfechafactura = (EditText)findViewById(R.id.etfechafactura);
        etfechavencimiento = (EditText)findViewById(R.id.etfechavencimiento);

        btcrear = (Button) findViewById(R.id.btcrear);
        btvolver = (Button) findViewById(R.id.btvolver);

        String codigo = getIntent().getStringExtra("codigo");
        String numero = getIntent().getStringExtra("numero");
        String razon = getIntent().getStringExtra("razon");

      //  String codigo = getIntent().getStringExtra("codigo");
        //etdescripcion.setText(codigo);
        //etdescripcion.setText(codigo);
    }

   // String codigo = getIntent().getStringExtra("codigo");
    //String codigo = getIntent().getStringExtra("codigo");
   // String numero = getIntent().getStringExtra("numero");
    //String razon = getIntent().getStringExtra("razon");

    public void volver (View view) {

        AlertDialog.Builder alerta = new AlertDialog.Builder (Activity2.this);

        alerta.setMessage("Si vuelve tendrá que empezar la factura de nuevo.")
                .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent= new Intent (Activity2.this, MainActivity.class);
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

    public void generar (View view){

        if(etdescripcion.getText().length()==0 || etbase.getText().length()==0 || etiva.getText().length()==0
        || ettotal.getText().length()==0 || etfechafactura.getText().length()==0 || etfechavencimiento.getText().length()==0){
            Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder (Activity2.this);

            alerta.setMessage("¿Desea enviar la factura por email?")
                    .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent enviar = new Intent (Intent.ACTION_SEND);
                    enviar.setType("text/plain");
                    enviar.putExtra(Intent.EXTRA_SUBJECT, "Factura");
                    enviar.putExtra(Intent.EXTRA_TEXT,"");
                    startActivity(enviar);
                   /* String[] mailto = {""};
                    String pdfname = null;
                    Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CALC/REPORTS/",pdfname ));
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Calc PDF Report");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Hi PDF is attached in this mail. ");
                    emailIntent.setType("application/pdf");
                    emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(emailIntent, "Send email using:"));*/
                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Factura guardada correctamente", Toast.LENGTH_LONG).show();
                        }
                    });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("Factura generada");
            titulo.show();

        }

    }


}
