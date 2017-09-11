package com.diabets.diabets;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    float glucosaActual, carbosCons, ratioS,sensibilidadS, rangoS, correcion, total, ch, totalCarb ;
    String estado;
    boolean Sesion;
    TextView valor1,valor2,valor3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /////////////////////////////////////////////
        try{
            SesionInvitado();
        }catch (Exception e){

        }


        ////////////////////////////////////////
        final TextView txu = (TextView) findViewById(R.id.total);

        //Bundle datos = new Bundle();
        String carbo = "";
        try {
            txu.setText((String) getIntent().getExtras().get("Carbos"));
        }catch (
                Exception e
                ){

        }


        //////////////////////////////////////
        Log.d("Mensaje", "Hola");
        Button boton = (Button) findViewById(R.id.btnlista);
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Boolean varInvi = true;


                Intent int1 = new Intent(getApplicationContext(), lista.class);
                int1.putExtra("variableInvitado", Sesion);
                startActivity(int1 );
            }
        });
        /////////////////////////////////////

        Button botona = (Button) findViewById(R.id.calcular);
        botona.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /* TextView textViewas = (TextView) findViewById(R.id.numcal);
                textViewas.setVisibility(View.VISIBLE);
                TextView textView2 = (TextView) findViewById(R.id.textcal);
                textView2.setVisibility(View.VISIBLE);*/
                if (txu.getText().toString().equals("")){
                    txu.setText("0");
                }

                TextView txu = (TextView) findViewById(R.id.total);
                TextView txtBolus = (TextView) findViewById(R.id.txtBC);
                TextView txtCarbos = (TextView) findViewById(R.id.txtCH);
                TextView textView = (TextView) findViewById(R.id.numcal);
                TextView bolusCorreccion = (TextView) findViewById(R.id.numBC);
                TextView bolusCarbos = (TextView) findViewById(R.id.numCH);
                TextView glucosa = (TextView) findViewById(R.id.input_nombre);
                TextView carbos = (TextView) findViewById(R.id.input_apellido2);
                TextView ratio = (TextView) findViewById(R.id.edit3);
                TextView sensibilidad = (TextView) findViewById(R.id.edit4);
                TextView rango = (TextView) findViewById(R.id.edit5);
                totalCarb = Float.parseFloat(txu.getText().toString());
                glucosaActual = Integer.parseInt(glucosa.getText().toString());
                carbosCons = Integer.parseInt(carbos.getText().toString());
                ratioS = Integer.parseInt(ratio.getText().toString());
                sensibilidadS = Integer.parseInt(sensibilidad.getText().toString());
                rangoS = Integer.parseInt(rango.getText().toString());
                correcion = ((glucosaActual - rangoS)/sensibilidadS);
                ch = (carbosCons +totalCarb) / ratioS;
                total =  correcion + ch;
                bolusCorreccion.setText(String.format("%.2f", correcion));
                bolusCarbos.setText(String.format("%.2f", ch));
                textView.setText(String.format("%.2f", total));
                textView.setVisibility(View.VISIBLE);
                bolusCorreccion.setVisibility(View.VISIBLE);
                bolusCarbos.setVisibility(View.VISIBLE);
                txtBolus.setVisibility(View.VISIBLE);
                txtCarbos.setVisibility(View.VISIBLE);

            }
        });


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btnnavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menuB = bottomNavigationView.getMenu();
        MenuItem menuItem = menuB.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId() )
                {
                    case R.id.calculadora:
                        //Intent int3 = new Intent(getApplicationContext(), MainActivity.class);
                        //Intent int3 = new Intent(MainActivity.this,MainActivity.class);
                        //startActivity(int3);
                        break;
                    case R.id.perfil:
                        //Intent int4 = new Intent(getApplicationContext(), registro.class);
                        Intent int4 = new Intent(MainActivity.this,registro.class);
                        startActivity(int4);
                        startActivity(new Intent(getBaseContext(), registro.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                        break;
                    case R.id.Glosario:
                        Intent int5 = new Intent(MainActivity.this,glosario.class);
                        startActivity(int5);
                        startActivity(new Intent(getBaseContext(), glosario.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                        break;



                }

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {

            case R.id.salir:
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void SesionInvitado(){

        Boolean var = getIntent().getExtras().getBoolean("variableInvitado");
        Log.d("Boolean Invitado", var.toString());
        Sesion=var;
        if (Sesion == true){
            Toast.makeText(getBaseContext(), "Ingreso Invitado", Toast.LENGTH_SHORT).show();

            bottomNavigationView = (BottomNavigationView) findViewById(R.id.btnnavigation);
            bottomNavigationView.setVisibility(View.INVISIBLE);

        }else {
            Toast.makeText(getBaseContext(), "No Ingreso Invitado", Toast.LENGTH_SHORT).show();
        }


    }







}

