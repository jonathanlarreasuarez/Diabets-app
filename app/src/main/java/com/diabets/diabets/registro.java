package com.diabets.diabets;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import cz.msebera.android.httpclient.Header;

public class registro extends AppCompatActivity {

    TabHost th;
    String ratioL, sensibilidadL, rangominL, rangomaxL, pesoL, alturaL, userID, estado, nombreID, apellidoID, telefonoID, correoID;
    TextView txtRatio, txtsensibilidad , txtrangomin, txtrangomax, txtaltura, txtpeso, txtNombre, txtApellido, txtTelefono, txtEmail;
    Button miBoton,miBoton2,miBotonP,miBotonGP;



    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        th = (TabHost)findViewById(R.id.tabprincipal);
        th.setup();

        TabHost.TabSpec specs = th.newTabSpec("Tag2");
        specs.setContent(R.id.tab1);
        specs.setIndicator("Médicos");
        th.addTab(specs);

        specs = th.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("Personales");
        th.addTab(specs);

        specs = th.newTabSpec("tag3");
        specs.setContent(R.id.tab3);
        specs.setIndicator("Contacto");
        //th.addTab(specs);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btnnavigation);
       // bottomNavigationView.enableShiftingMode(false);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menuB = bottomNavigationView.getMenu();
        MenuItem menuItem = menuB.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId() )
                {
                    case R.id.calculadora:

                        Intent int3 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(int3);
                        startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                        break;
                    case R.id.perfil:
                        //Intent int4 = new Intent(getApplicationContext(), registro.class);
                        //Intent int4 = new Intent(registro.this,registro.class);
                        //startActivity(int4);
                        break;
                    case R.id.Glosario:
                        Intent int5 = new Intent(registro.this,glosario.class);
                        startActivity(int5);
                        startActivity(new Intent(getBaseContext(), glosario.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                        break;


                }


                return true;
            }
        });

        ////////////////////////////////////////////////////////////////
        try
        {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("user.txt")));

            String texto = fin.readLine();
            JSONObject jsonArray2 = new JSONObject(texto);
            Log.d("JSON ARRAY NEW ACT", jsonArray2.toString());




            for (int i = 0; i < jsonArray2.getJSONArray("metas").length(); i++) {
                Log.d("VVVVV", jsonArray2.getJSONArray("metas").get(i).toString());
                JSONObject temp = jsonArray2.getJSONArray("metas").getJSONObject(i);
                userID = temp.getString("id");
                ratioL = temp.getString("ratio");
                sensibilidadL = temp.getString("sensibilidad");
                rangominL = temp.getString("rango_min");
                rangomaxL = temp.getString("rango_max");
                pesoL = temp.getString("peso");
                alturaL = temp.getString("altura");

                nombreID = temp.getString("nombre");
                apellidoID = temp.getString("apellido");
                telefonoID = temp.getString("telefono");
                correoID = temp.getString("email");

            }


             txtRatio = (TextView) findViewById(R.id.rimet1);
             txtsensibilidad = (TextView) findViewById(R.id.rimet2);
             txtrangomin = (TextView) findViewById(R.id.edit6);
             txtrangomax = (TextView) findViewById(R.id.rimet3);
             txtaltura = (TextView) findViewById(R.id.rimet4);
             txtpeso = (TextView) findViewById(R.id.rimet5);

            txtNombre = (TextView) findViewById(R.id.brimet1);
            txtApellido= (TextView) findViewById(R.id.brimet2);
            txtTelefono = (TextView) findViewById(R.id.brimet6);
            txtEmail = (TextView) findViewById(R.id.brimet7);


            ////////////////////////////////////////////////////////
            txtRatio.setText(ratioL);
            txtsensibilidad.setText(sensibilidadL);
            txtrangomin.setText(rangominL);
            txtrangomax.setText(rangomaxL);
            txtaltura.setText(pesoL);
            txtpeso.setText(alturaL);
            txtNombre.setText(nombreID);
            txtApellido.setText(apellidoID);
            txtTelefono.setText(telefonoID);
            txtEmail.setText(correoID);
            ///////////////////////////////////////////////
            txtRatio.setEnabled(false);
            txtsensibilidad.setEnabled(false);
            txtrangomin.setEnabled(false);
            txtrangomax.setEnabled(false);
            txtaltura.setEnabled(false);
            txtpeso.setEnabled(false);

            txtNombre.setEnabled(false);
            txtApellido.setEnabled(false);
            txtTelefono.setEnabled(false);
            txtEmail.setEnabled(false);
            ///////////////////////////////////////////////
            miBoton = (Button) findViewById(R.id.guardarM);
            miBoton.setEnabled(false);

            miBotonP = (Button) findViewById(R.id.guardarP);
            miBotonP.setEnabled(false);

            ////////////////////////////////////////////

            Log.d("JSON ARRAY ratioL", ratioL);
            Log.d("JSON  sensibilidadL", sensibilidadL);
            Log.d("JSON ARRAY rangominL", rangominL);
            Log.d("JSON rangomaxL ", rangomaxL);
            Log.d("JSON ARRAY pesoL", pesoL);
            Log.d("JSON alturaL", alturaL);

            fin.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }

    }


    public void editar(View v){
        txtRatio.setEnabled(true);
        txtsensibilidad.setEnabled(true);
        txtrangomin.setEnabled(true);
        txtrangomax.setEnabled(true);
        txtaltura.setEnabled(true);
        txtpeso.setEnabled(true);

        miBoton = (Button) findViewById(R.id.guardarM);
        miBoton.setEnabled(true);

    }
    public void editarP(View v){
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtEmail.setEnabled(true);

        miBotonP = (Button) findViewById(R.id.guardarP);
        miBotonP.setEnabled(true);


    }


    public void irInfoPersonal(View v){

        Toast.makeText(v.getContext(), "Datos Guardados", Toast.LENGTH_SHORT).show();
        th.setCurrentTab(1);
    }
    public void irInfoContacto (View v){
        th.setCurrentTab(2);
    }

    public void GuardarTodo(View v) {
        miBoton2 = (Button) findViewById(R.id.cancelarM);
        miBoton2.setEnabled(true);


        Log.d("FuncionuserID Update", userID);



        TextView txtRatioU = (TextView) findViewById(R.id.rimet1);
        TextView txtsensibilidadU = (TextView) findViewById(R.id.rimet2);
        TextView txtrangominU = (TextView) findViewById(R.id.edit6);
        TextView txtrangomaxU = (TextView) findViewById(R.id.rimet3);
        TextView txtalturaU = (TextView) findViewById(R.id.rimet4);
        TextView txtpesoU = (TextView) findViewById(R.id.rimet5);


        TextView txtNombreU = (TextView) findViewById(R.id.brimet1);
        TextView txtApellidoU = (TextView) findViewById(R.id.brimet2);
        TextView txtTelefonoU = (TextView) findViewById(R.id.brimet6);
        TextView txtEmailU = (TextView) findViewById(R.id.brimet7);


        String ratioU = txtRatioU.getText().toString();
        String sensiU = txtsensibilidadU.getText().toString();
        String ranmiU = txtrangominU.getText().toString();
        String ranmaU = txtrangomaxU.getText().toString();
        String alturaU = txtalturaU.getText().toString();
        String pesoU = txtpesoU.getText().toString();


        String nombreU = txtNombreU.getText().toString();
        String apellidoU = txtApellidoU.getText().toString();
        String telefonoU = txtTelefonoU.getText().toString();
        String emailU = txtEmailU.getText().toString();

        Log.d("FuncionuserID Update",ratioU);
        Log.d("FuncionuserID Update",sensiU);
        Log.d("FuncionuserID Update",ranmiU);
        Log.d("FuncionuserID Update",ranmaU);
        Log.d("FuncionuserID Update",alturaU);
        Log.d("FuncionuserID Update",pesoU);
        Log.d("FuncionuserID nombreU",nombreU);
        Log.d("FuncionuserID apellidoU",apellidoU);
        Log.d("FuncionuserID telefonoU",telefonoU);
        Log.d("FuncionuserID emailU",emailU);




        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://diabets.life/wsdi/actualizardatos.php?id="+userID+"&ratio="+ratioU+"&sensibilidad="+sensiU+"&rango_min="+ranmiU+"&rango_max="+ranmaU+"&peso="+alturaU+"&altura="+pesoU, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                Log.d("OnStart: ", "OK update");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String data= new String(response);
                Log.d("onSuccess ke: ", ""+ data);


                Toast.makeText(getBaseContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                Log.d("Success", "");
                txtRatio.setEnabled(false);
                txtsensibilidad.setEnabled(false);
                txtrangomin.setEnabled(false);
                txtrangomax.setEnabled(false);
                txtaltura.setEnabled(false);
                txtpeso.setEnabled(false);



                AsyncHttpClient client = new AsyncHttpClient();
                client.get("http://diabets.life/wsdi/obtenerUsuario.php?id="+userID, new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        // called before request is started
                        Log.d("OnStart: ", "OK update");
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                        // called when response HTTP status is "200 OK"
                        String data= new String(response);
                        Log.d("onSuccess ke: ", ""+ data);
                        try {
                            Log.d("DATA", "GRABA DE NUEVO ONCREATE");
                            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("user.txt", Activity.MODE_PRIVATE));
                            archivo.write(data.toString());
                            Log.d("DATA", "LLEGO HASTA AQUI UPDATE");
                            archivo.flush();
                            archivo.close();

                        } catch (IOException e) {
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.d("onFailure: ", "OK");
                        Toast.makeText(getBaseContext(), "fallo actualizados", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                        Log.d("onRetry: ", "OK");
                    }
                });

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("onFailure: ", "OK");
                Toast.makeText(getBaseContext(), "fallo actualizados, por favor conéctese a internet", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                Log.d("onRetry: ", "OK");
            }
        });


       /* Intent int5 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(int5);*/
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
}
