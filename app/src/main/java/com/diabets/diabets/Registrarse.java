package com.diabets.diabets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class Registrarse extends AppCompatActivity {


    String TipoID = "";
    TabHost th,tabHost;
    String name, pass, type, nombre, apellido, fechaT, nombreG, apellidoG, userG,pasG, estado, fechaG,telefonoG,mailG, radioG,TipoG,ciudadG,provinciaG;
    RadioButton r1, r2;
    public Integer number,numberCiudad,numberTipo;

    private static final String TAG = "MainActivity";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    List<String> TipoUser = new ArrayList<String>();
    List<String> ProvinciaList = new ArrayList<String>();
    List<String> ciudadLis = new ArrayList<String>();



Button btnValidar, btnFallo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);



        r1 = (RadioButton) findViewById(R.id.radioButton);
        r2=(RadioButton)findViewById(R.id.radioButton2);

        th = (TabHost)findViewById(R.id.tabprincipal);
        //th.getTabWidget().getChildAt(0).setVisibility(View.GONE);


        th.setup();


        TabHost.TabSpec specs = th.newTabSpec("Tag2");

        specs.setContent(R.id.tab1);
        specs.setIndicator("Cuenta");
        th.addTab(specs);


        specs = th.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("Personal");
        th.addTab(specs);

        specs = th.newTabSpec("tag3");
        specs.setContent(R.id.tab3);
        specs.setIndicator("Contacto");
        th.addTab(specs);

        th.getTabWidget().getChildTabViewAt(0).setEnabled(false);
        th.getTabWidget().getChildTabViewAt(1).setEnabled(false);
        th.getTabWidget().getChildTabViewAt(2).setEnabled(false);

        if (r1.isChecked()==true) {
            //código
            Log.d("Radio","Buttom1 IF");
        } else
        if (r2.isChecked()==true) {
            Log.d("Radio","Buttom2 IF");
            // código
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        mDisplayDate = (TextView) findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Registrarse.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://diabets.life/wsdi/obtenerCliente.php", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                Log.d("OnStart: ", "OK");
                btnFallo = (Button) findViewById(R.id.guardar);
                btnFallo.setEnabled(false);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                String data= new String(response);
                Log.d("onSuccess: ", ""+ data);

                try{
                    JSONObject jsonArray = new JSONObject(data);
                    //JSONArray jsonArray = new JSONArray(data);
                    String texto = "";
                    Log.d("JSON ARRAY", jsonArray.toString());

                    for (int i = 0; i < jsonArray.getJSONArray("metas").length(); i++) {
                        Log.d("VV", jsonArray.getJSONArray("metas").get(i).toString());
                        JSONObject temp = jsonArray.getJSONArray("metas").getJSONObject(i);
                        TipoUser.add(temp.getString("nombre"));



                    }
                    final Spinner spinner = (Spinner) findViewById(R.id.tipoUsuario);

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, TipoUser);
                    dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                    spinner.setAdapter(dataAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                          @Override
                                                          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                              Registrarse.this.numberTipo = spinner.getSelectedItemPosition() + 1;
                                                          }

                                                          @Override
                                                          public void onNothingSelected(AdapterView<?> adapterView) {

                                                          }
                                                      });





                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("Mensaje", "Nooooooooooooooooooooooooooooooooooooo LLEGARGON LOS DATOS");
                }
                btnFallo = (Button) findViewById(R.id.guardar);
                btnFallo.setEnabled(true);

                Log.d("Success", "");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

                Toast.makeText(getBaseContext(), "Por Favor Conéctese a internet para Regístrese1", Toast.LENGTH_SHORT).show();


                btnValidar = (Button) findViewById(R.id.btnFinal);
                btnValidar.setEnabled(false);


            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                Log.d("onRetry: ", "OK");
            }
        });
        //////////////////////////////////// LLamada Servicio Provincia///////////////////////////////////////////////////////////////
        AsyncHttpClient client2 = new AsyncHttpClient();
        client2.get("http://diabets.life/wsdi/obtenerProvincia.php", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                Log.d("OnStart: ", "OK");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                String data= new String(response);
                Log.d("onSuccess: ", ""+ data);

                try{
                    JSONObject jsonArray2 = new JSONObject(data);
                    //JSONArray jsonArray = new JSONArray(data);
                    String texto = "";
                    Log.d("JSON ARRAY", jsonArray2.toString());

                    for (int i = 0; i < jsonArray2.getJSONArray("metas").length(); i++) {
                        Log.d("VV", jsonArray2.getJSONArray("metas").get(i).toString());
                        JSONObject temp = jsonArray2.getJSONArray("metas").getJSONObject(i);
                        ProvinciaList.add(temp.getString("nombre"));

                        Log.d("data ProvinciaList", ProvinciaList.get(i));


                    }

                    final Spinner spinnerP = (Spinner) findViewById(R.id.etProvincia);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, ProvinciaList);
                    dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                    spinnerP.setAdapter(dataAdapter);
                    spinnerP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ciudadLis.clear();
                           // carbos.clear();
                            Registrarse.this.number = spinnerP.getSelectedItemPosition() + 1;
                            Log.d("data listaCiudad", number.toString());

                            //////////////////////////////////////////////////////
                            AsyncHttpClient clientC = new AsyncHttpClient();
                            clientC.get("http://diabets.life/wsdi/obtenerCiudad.php?id=" + Registrarse.this.number, new AsyncHttpResponseHandler() {
                                @Override
                                public void onStart() {
                                    Log.d("OnStart: ", "OK");
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                                    String data= new String(response);
                                    Log.d("onSuccess: ", ""+ data);
                                    Log.d("LLEGO HASTA CIUDAD: ", ""+ data);

                                    try{
                                        JSONObject jsonArrayC = new JSONObject(data);
                                        //JSONArray jsonArray = new JSONArray(data);
                                        String texto = "";
                                        Log.d("JSON ARRAY", jsonArrayC.toString());

                                        for (int i = 0; i < jsonArrayC.getJSONArray("metas").length(); i++) {
                                            Log.d("VV", jsonArrayC.getJSONArray("metas").get(i).toString());
                                            JSONObject temp = jsonArrayC.getJSONArray("metas").getJSONObject(i);
                                            ciudadLis.add(temp.getString("nombre"));

                                            Log.d("tiposUSER", ciudadLis.get(i));


                                        }
                                        final Spinner spinnerC = (Spinner) findViewById(R.id.etCiudad);


                                        ArrayAdapter<String> dataAdapterC = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, ciudadLis);
                                        dataAdapterC.setDropDownViewResource(R.layout.spinner_dropdown);
                                        spinnerC.setAdapter(dataAdapterC);
                                        spinnerC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                Registrarse.this.numberCiudad = spinnerC.getSelectedItemPosition() + 1;
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });


                                    }catch (Exception e){
                                        e.printStackTrace();
                                        Log.d("Mensaje", "Nooooooooooooooooooooooooooooooooooooo LLEGARGON LOS DATOS");
                                    }

                                    Log.d("Success", "");
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                    Toast.makeText(getBaseContext(), "Por Favor Conéctese a internet para Regístrese", Toast.LENGTH_SHORT).show();
                                    btnValidar = (Button) findViewById(R.id.btnFinal);
                                    btnValidar.setEnabled(false);
                                }

                                @Override
                                public void onRetry(int retryNo) {
                                    // called when request is retried
                                    Log.d("onRetry: ", "OK");
                                }
                            });

                            ////////////////////////////////////////////////////////
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("Mensaje", "Nooooooooooooooooooooooooooooooooooooo LLEGARGON LOS DATOS");
                }

                Log.d("Success", "");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getBaseContext(), "Por Favor Conéctese a internet para Regístrese", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                Log.d("onRetry: ", "OK");
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////



    }
    public void onRadioButtonClicked(View view) {

        // Is the button now checked?

        boolean checked = ((RadioButton) view).isChecked();

        // hacemos un case con lo que ocurre cada vez que pulsemos un botón

        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    radioG="1";
                    Log.d("Radio1",radioG);


                    break;
            case R.id.radioButton2:
                if (checked)
                    radioG="2";
                    Log.d("Radio2",radioG);
                    break;
        }
    }

    public void irSegundoRegistro(View v){

        TextView name1 = (TextView) findViewById(R.id.txtUsuario);
        TextView pass1 = (TextView) findViewById(R.id.txtContrasena);
        //TextView type1 = (TextView) findViewById(R.id.txtTipoUsuario);
        name = name1.getText().toString();
        pass = pass1.getText().toString();
       // type = type1.getText().toString();
        if (name.equals("") || pass.equals("")) {
            Toast.makeText(this, "Ha dejado campos vacios",Toast.LENGTH_LONG).show();


        }else {
            th.setCurrentTab(1);
            th.getTabWidget().getChildTabViewAt(0).setEnabled(true);
            th.getTabWidget().getChildTabViewAt(1).setEnabled(true);
        }


    }
    public void irTercerRegistro(View v) {
        TextView nombres = (TextView) findViewById(R.id.nombres);
        TextView apellidos = (TextView) findViewById(R.id.apellidos);
        TextView fecha = (TextView) findViewById(R.id.tvDate);
        RadioButton r1 = (RadioButton)findViewById(R.id.radioButton);
        RadioButton r2 = (RadioButton)findViewById(R.id.radioButton2);


        nombre = nombres.getText().toString();
        apellido = apellidos.getText().toString();
        fechaT = fecha.getText().toString();
        //radioB1 = r1.getText().toString();


        if (nombre.equals("") || apellido.equals("")|| fechaT.equals("")) {
             if( r1.isChecked()==false || r2.isChecked()==false){
                 Toast.makeText(this, "Ha dejado campos vacios otra vez D:",
                         Toast.LENGTH_LONG).show();
            }
        }else {
            th.getTabWidget().getChildTabViewAt(0).setEnabled(true);
            th.getTabWidget().getChildTabViewAt(1).setEnabled(true);
            th.getTabWidget().getChildTabViewAt(2).setEnabled(true);
            th.setCurrentTab(2);
        }

    }



    public void guardarRegistro(View v){
        Toast.makeText(v.getContext(), "Registro Completado", Toast.LENGTH_SHORT).show();

        ///////////////////////////////////////////////////////////////////////////
        TextView dato1 = (TextView) findViewById(R.id.nombres);
        TextView dato2 = (TextView) findViewById(R.id.apellidos);
        TextView dato3 = (TextView) findViewById(R.id.txtUsuario);
        TextView dato4 = (TextView) findViewById(R.id.txtContrasena);
        TextView dato5 = (TextView) findViewById(R.id.tvDate);
        TextView dato6 = (TextView) findViewById(R.id.etTelefono);
        TextView dato7 = (TextView) findViewById(R.id.etCorreo);
/*
        if(number.toString() == null || numberCiudad.toString()==null || numberTipo.toString()==null){
            Toast.makeText(v.getContext(), "Error en el registro, llene todos los campos y conéctese a internet", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(v.getContext(), "Registro Completado :/", Toast.LENGTH_SHORT).show();}

*/

        nombreG = dato1.getText().toString();
        apellidoG = dato2.getText().toString();
        userG = dato3.getText().toString();
        pasG = dato4.getText().toString();
        fechaG = dato5.getText().toString();
        telefonoG = dato6.getText().toString();
        mailG = dato7.getText().toString();
        provinciaG = number.toString();
        ciudadG = numberCiudad.toString();
        TipoG = numberTipo.toString();




        Log.d("usuario", nombreG);
        Log.d("clave", apellidoG);
        Log.d("userG", userG);
        Log.d("pasG", pasG);
        Log.d("fechaG", fechaG);
        Log.d("telefonoG", telefonoG);
        Log.d("mailG", mailG);

        Log.d("numeroCiudadTraido", numberCiudad.toString());
        Log.d("numeroTipoTraido", numberTipo.toString());


        AsyncHttpClient client = new AsyncHttpClient();




        client.get("http://www.diabets.life/wsdi/registrar.php?" +
                "nombre="+nombreG+"&apellido="+apellidoG+"&user="+userG+"&contrasena="+pasG+"&fecha="+fechaG+"&telefono="+telefonoG+"&mails="+mailG+"&sexo="+radioG+"&provincia="+provinciaG+"&ciudad="+ciudadG+"&tipo="+TipoG,
                new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Log.d("OnStart: ", "OK");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String data= new String(response);
                Log.d("onSuccess: ", ""+ data);
                Log.d("Success", "");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getBaseContext(), "Por Favor Conéctese a internet para Regístrese", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                Log.d("onRetry: ", "OK");
            }
        });


        ////////////////////////////////////////////////////////////////////////////



        Intent int5 = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(int5);
    }



}
