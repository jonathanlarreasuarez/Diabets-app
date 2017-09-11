package com.diabets.diabets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import cz.msebera.android.httpclient.Header;



public class LoginActivity extends AppCompatActivity {

    String nombre, contrasena, estado, idUser, estadoL,nombreL, nombreUser, passUser, idUserL,nombreUserL,passUserL;
    int contador,totalMostrar;
    Button btnInvitado;

    Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       ///////////////////////////////////////////////
        SharedPreferences sharpref = getPreferences(context.MODE_PRIVATE);
        //String valor = sharpref.getString("MiDato","No hay dato");
        Integer contadorF = sharpref.getInt("count",0);
        btnInvitado = (Button)  findViewById(R.id.button3);
        if(contadorF>3){
            btnInvitado.setVisibility(View.INVISIBLE);
        }

/////////////////////////////////////////////////////////////////
    }

    public void ingresoInvitado(View v){
        ///////////////////////////////////////
        SharedPreferences sharpref = getPreferences(context.MODE_PRIVATE);
        //String valor = sharpref.getString("MiDato","No hay dato");
        Integer valor = sharpref.getInt("count",0);
        Integer nuevoValor;
        contador = valor+1;
        //////////////////////////////////////////
        //SharedPreferences sharpref = getPreferences(context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharpref.edit();
        //editor.putString("count", etDato.getText().toString());
        editor.putInt("count",contador);
        editor.commit();
       // Toast.makeText(getApplicationContext(), "Dato guardado : "+valor,Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "Dato guardado : "+contador,Toast.LENGTH_LONG).show();

        ////////////////////////////////////////////////////
        btnInvitado = (Button)  findViewById(R.id.button3);
        //contador--;
        //////////////////////////////////  //////////////////

        if(contador == 1){
            totalMostrar = 2;
            Toast.makeText(getBaseContext(), "Le quedan "+totalMostrar+" ingresos como invitado", Toast.LENGTH_SHORT).show();
        } else if(contador == 2){
            totalMostrar = 1;
            Toast.makeText(getBaseContext(), "Le queda "+totalMostrar+" ingreso como invitado", Toast.LENGTH_SHORT).show();
        } else if(contador == 3){
            totalMostrar = 0;
            Toast.makeText(getBaseContext(), "Le quedan "+totalMostrar+" ingresos como invitado", Toast.LENGTH_SHORT).show();
        }


        if (contador >= 4){
            Toast.makeText(getBaseContext(), "Registrese para seguir usando la aplicación", Toast.LENGTH_SHORT).show();

            btnInvitado.setVisibility(View.INVISIBLE);
        }else {

          //  Toast.makeText(getBaseContext(), "Le queda "+contador+" ingreso como invitado", Toast.LENGTH_SHORT).show();
            Boolean varInvi = true;
            Intent int2 = new Intent(getApplicationContext(), MainActivity.class);
            int2.putExtra("variableInvitado", varInvi);
            startActivity(int2);
        }
    }

    public void irRegistro(View v) {
        Intent int1 = new Intent(getApplicationContext(), Registrarse.class);
        startActivity(int1);
    }


    public void validarLogin(View v){
        TextView name = (TextView) findViewById(R.id.nombreUsuario);
        final TextView pass = (TextView) findViewById(R.id.claveUsuario);

        nombre = name.getText().toString();
        contrasena = pass.getText().toString();

        Log.d("usuario", nombre);
        Log.d("clave", contrasena);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.diabets.life/wsdi/login.php?nombre="+nombre+"&contrasena="+contrasena, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
                Log.d("OnStart: ", "OK");
                Toast.makeText(getBaseContext(), "Cargando por favor Espere", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String data= new String(response);
                Log.d("onSuccess: ", ""+ data);
                //JSONObject text = new JSONObject();
                try{
                    JSONObject jsonArray = new JSONObject(data);
                    String texto = "";
                    Log.d("JSON ARRAY", jsonArray.toString());
                    Log.d("JSON ARRAY de objeto", jsonArray.toString());
                    for (int i = 0; i < jsonArray.getJSONArray("metas").length(); i++) {
                        Log.d("VVVVV", jsonArray.getJSONArray("metas").get(i).toString());
                       JSONObject temp = jsonArray.getJSONArray("metas").getJSONObject(i);
                        idUser = temp.getString("id");
                        nombreUser = temp.getString("nombre_usuario");
                        passUser = temp.getString("contraseña");

                    }
                    estado = jsonArray.get("estado").toString();
                        try {
                            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(
                                    "user.txt", Activity.MODE_PRIVATE));
                            archivo.write(data.toString());
                            Log.d("DATA", "LLEGO HASTA AQUI");
                            archivo.flush();
                            archivo.close();

                        } catch (IOException e) {

                        }
                    Toast.makeText(getBaseContext(), "Guardado", Toast.LENGTH_SHORT).show();
                    ///////////////////////////////////////////////////////////
                    Log.d("DATA METASSSSSSSSSSS", idUser);
                        if (estado == "1"){

                            Boolean varInvi = false;

                           // Intent int2 = new Intent(getApplicationContext(), MainActivity.class);

                            Intent int1 = new Intent(getApplicationContext(), MainActivity.class);
                            int1.putExtra("variableInvitado", varInvi);
                            startActivity(int1);

                        }else {

                        }

                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("Mensaje", "No lo puedo convertir a json");
                    Toast.makeText(getBaseContext(), "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                }
                Log.d("Success", "");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("onFailure: ", "OK");

                try
                {
                    BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("user.txt")));

                    String texto = fin.readLine();
                    JSONObject jsonArray2 = new JSONObject(texto);
                    Log.d("JSON ARRAY", jsonArray2.toString());


                    for (int i = 0; i < jsonArray2.getJSONArray("metas").length(); i++) {
                        Log.d("VVVVV", jsonArray2.getJSONArray("metas").get(i).toString());
                        JSONObject temp = jsonArray2.getJSONArray("metas").getJSONObject(i);
                        idUserL = temp.getString("id");
                        nombreUserL = temp.getString("nombre_usuario");
                        passUserL = temp.getString("contraseña");

                    }



                    Log.d("JSON ARRAY nombreUserL", nombreUserL);
                    Log.d("JSON nombreUserL nombre", nombre);
                   // estadoL = jsonArray2.get("estado").toString();
                    //nombreL = jsonArray2.get("metas").toString();
                    //String ok = jsonArray2.getJSONObject("id").toString();


                    /*for (int i=0; i < nombreL.length(); i++){
                        String id1 = nombreL.ge

                }*/

                    if (nombre.equals(nombreUserL)){
                        Intent int1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(int1);

                    }else {
                        Toast.makeText(getBaseContext(), "Por Favor Conéctese a internet o Regístrese", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("diskeLeerNombre: ", nombreL);
                    Log.d("diskeLeer: ", texto);
                    Log.d("diskeLeerestado: ", estadoL);
                    fin.close();
                }
                catch (Exception ex)
                {
                    Log.e("Ficheros", "Error al leer fichero desde memoria interna");
                }
                //Intent int1 = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(int1);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                Log.d("onRetry: ", "OK");
            }
        });




        //Intent int1 = new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(int1);
    }



}
