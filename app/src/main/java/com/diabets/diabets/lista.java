package com.diabets.diabets;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.LineFormatter;

public class lista extends AppCompatActivity {

    Spinner spinner, spinner2;
    public Integer number;
    Float totalCarbos;
    public static Integer contador = 1, c = 0;
    TextView alimentoSeleccionado, carbosSeleccionado, carbosTotales;
    private BottomNavigationView bottomNavigationView;


    List<String> categorias = new ArrayList<String>();
    List<String> alimentos = new ArrayList<String>();
    List<String> carbos = new ArrayList<String>();
    List<LinearLayout> listaAlimentos = new ArrayList<LinearLayout>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        spinner = (Spinner) findViewById(R.id.alimentospinner);
        spinner2 = (Spinner) findViewById(R.id.alimentospinner2);
        alimentoSeleccionado = (TextView) findViewById(R.id.alimento1);
        carbosSeleccionado = (TextView) findViewById(R.id.alimento3);
        carbosTotales = (TextView) findViewById(R.id.alimentostotalnum);


        /////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////


        AsyncHttpClient client = new AsyncHttpClient();
        totalCarbos = Float.valueOf(0);
        client.get("http://www.diabets.life/wsdi/obtenerCategoria.php", new AsyncHttpResponseHandler() {
            //client.get("http://www.diabets.life/wsdi/login.php");
            @Override
            public void onStart() {
                // called before request is started
                Log.d("OnStart: ", "OK");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String data = new String(response);
                Log.d("onSuccess: ", "" + data);

                //JSONObject text = new JSONObject();


                try {
                    JSONObject jsonArray = new JSONObject(data);


                    for (int i = 0; i < jsonArray.getJSONArray("metas").length(); i++) {
                        Log.d("VV", jsonArray.getJSONArray("metas").get(i).toString());
                        JSONObject temp = jsonArray.getJSONArray("metas").getJSONObject(i);
                        categorias.add(temp.getString("nombre"));


                    }
                    Log.d("Hola", "" + categorias.size());


                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, categorias);
                    dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                    spinner.setAdapter(dataAdapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        public void onItemSelected(AdapterView<?> parentView,
                                                   View selectedItemView, int position, long id) {
                            // Object item = parentView.getItemAtPosition(position);
                            alimentos.clear();
                            carbos.clear();
                            lista.this.number = spinner
                                    .getSelectedItemPosition() + 1;


                            AsyncHttpClient client2 = new AsyncHttpClient();
                            contador = 1;
                            client2.get("http://www.diabets.life/wsdi/obtenerAlimentos.php?id=" + lista.this.number, new AsyncHttpResponseHandler() {
                                //client.get("http://www.diabets.life/wsdi/login.php");
                                @Override
                                public void onStart() {
                                    // called before request is started
                                    Log.d("OnStart: ", "OK");
                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                                    // called when response HTTP status is "200 OK"
                                    String data = new String(response);
                                    Log.d("onSuccess: ", "" + data);

                                    //JSONObject text = new JSONObject();


                                    try {
                                        JSONObject jsonArray = new JSONObject(data);


                                        for (int i = 0; i < jsonArray.getJSONArray("metas").length(); i++) {
                                            Log.d("VV", jsonArray.getJSONArray("metas").get(i).toString());
                                            JSONObject temp = jsonArray.getJSONArray("metas").getJSONObject(i);
                                            alimentos.add(temp.getString("nombre"));
                                            carbos.add(temp.getString("carbohidratos_unidad"));
                                            Log.d("Carbos", carbos.get(i));


                                        }
                                        Log.d("Hola", "" + alimentos.size());


                                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, alimentos);
                                        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                                        spinner2.setAdapter(dataAdapter);

                                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                            public void onItemSelected(AdapterView<?> parentView,
                                                                       View selectedItemView, int position, long id) {
                                                // Object item = parentView.getItemAtPosition(position);

                                                lista.this.number = spinner2
                                                        .getSelectedItemPosition() + 1;

                                                Log.d("Producto: ", "" + alimentos.get(lista.this.number - 1));
                                                Log.d("Carbos: ", "" + carbos.get(lista.this.number - 1));
                                                Log.d("posicion", "" + lista.this.number);

                                                if (contador > 1){
                                                    c++;
                                                    LinearLayout linearLayout= (LinearLayout)findViewById(R.id.linea_alimento_linear);

                                                    View child1 = LayoutInflater.from(getApplicationContext()).inflate(
                                                            R.layout.linea_alimento, null);

                                                    TextView alimento1 = (TextView) child1.findViewById(R.id.alimento1);
                                                    TextView alimento2 = (TextView) child1.findViewById(R.id.alimento3);
                                                    final TextView alimento4 = (TextView) child1.findViewById(R.id.alimento4);
                                                    final TextView alimento5 = (TextView) child1.findViewById(R.id.alimento5);
                                                    final TextView alimento6 = (TextView) child1.findViewById(R.id.alimento6);
                                                    final TextView alimentoCant = (TextView) child1.findViewById(R.id.alimento2);
                                                    alimento4.setTag(c);
                                                    alimento5.setTag(c);
                                                    alimento6.setTag(c);
                                                    alimento1.setText(alimentos.get(lista.this.number-1));
                                                    alimento2.setText(carbos.get(lista.this.number-1));
                                                    Float carboSeleccion = Float.parseFloat(alimento2.getText().toString());
                                                    Float alimentosCantidad = Float.parseFloat(alimentoCant.getText().toString());
                                                    carboSeleccion = carboSeleccion * alimentosCantidad;
                                                    Log.d("Multiplicacion", ""+carboSeleccion);
                                                    totalCarbos = (Float)totalCarbos + carboSeleccion;
                                                    carbosTotales.setText(String.format("%.2f", totalCarbos));
                                                    linearLayout.addView(child1);
                                                    listaAlimentos.add(linearLayout);
                                                    alimento4.setOnClickListener(new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View v) {
                                                            LinearLayout r = (LinearLayout) ((ViewParent) alimento4.getParent());
                                                            r.setVisibility(View.GONE);
                                                            TextView alimento2 = (TextView) r.findViewById(R.id.alimento3);
                                                            Float carboSeleccion = Float.parseFloat(alimento2.getText().toString());
                                                            totalCarbos = (Float)totalCarbos - carboSeleccion;
                                                            carbosTotales.setText(String.format("%.2f", totalCarbos));


                                                        }
                                                    });
                                                    alimento5.setOnClickListener(new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View v) {
                                                            LinearLayout r = (LinearLayout) ((ViewParent) alimento5.getParent());
                                                            TextView alimentoCant = (TextView) r.findViewById(R.id.alimento2);
                                                            Float cant = (Float.parseFloat(alimentoCant.getText().toString())) + 1;
                                                            if (cant > 1){
                                                                alimento6.setVisibility(View.VISIBLE);
                                                            }
                                                            alimentoCant.setText(""+cant);
                                                            TextView alimento2 = (TextView) r.findViewById(R.id.alimento3);
                                                            Float carboSeleccion = (Float.parseFloat(alimento2.getText().toString()));
                                                            alimento2.setText(String.format("%.2f", carboSeleccion + (carboSeleccion/(cant-1))));
                                                            Float carboSeleccion2 = (Float.parseFloat(alimento2.getText().toString()));
                                                            totalCarbos = (Float)totalCarbos + carboSeleccion2-carboSeleccion;
                                                            carbosTotales.setText(String.format("%.2f", totalCarbos));


                                                        }
                                                    });
                                                    alimento6.setOnClickListener(new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View v) {
                                                            LinearLayout r = (LinearLayout) ((ViewParent) alimento6.getParent());
                                                            TextView alimentoCant = (TextView) r.findViewById(R.id.alimento2);
                                                            Float cant = (Float.parseFloat(alimentoCant.getText().toString())) -1;
                                                            if (cant == 1){
                                                                alimento6.setVisibility(View.GONE);
                                                            }
                                                            Log.d("CANTIDAD",""+cant);
                                                            alimentoCant.setText(""+cant);

                                                            TextView alimento2 = (TextView) r.findViewById(R.id.alimento3);
                                                            Float carboSeleccion = (Float.parseFloat(alimento2.getText().toString()));
                                                            Log.d("CANTIDAD",""+carboSeleccion);
                                                            alimento2.setText(String.format("%.2f", carboSeleccion - (carboSeleccion/(cant+1))));
                                                            Log.d("CANTIDAD",""+(carboSeleccion - (carboSeleccion/(cant-1))));
                                                            Float carboSeleccion2 = (Float.parseFloat(alimento2.getText().toString()));
                                                            Log.d("CANTIDAD",""+carboSeleccion2);
                                                            totalCarbos = (Float)totalCarbos + carboSeleccion2-carboSeleccion;
                                                            Log.d("CANTIDAD",""+totalCarbos);
                                                            carbosTotales.setText(String.format("%.2f", totalCarbos));


                                                        }
                                                    });


                                                }else{
                                                    contador = contador +1;

                                                }





                                            }

                                            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
                                                Log.d("Hola", "tu");
                                            }

                                        });

                                        //JSONArray jsonNombres = new JSONArray(jsonArray.getJSONArray("metas"));
                                        //Log.d("Hola", jsonNombres.toString());
                                        //JSONArray jsonArray = new JSONArray(data);

                                        //String texto = "";
                                        //Log.d("JSON ARRAY", jsonArray.toString());

                                        //for (int i=0; i < jsonArray.length(); i++){
                                        //texto = jsonArray.getJSONArray(i).getString("metas");


                                        //metas = jsonArray.get("metas").toString();


                                        //usuarios.add(texto);
                                        //Usuario.monedas = new Integer(texto);
                                        //HomeActivity.textViewCantidadMonedas.setText(""+Usuario.monedas);
                                        //}
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d("Mensaje", "No lo puedo convertir a json");
                                    }


                                    //JSONObject jsonObject = new JSONObject();
                                    //jsonObject.getJSONObject("as");
                                    //JSONArray jsonArray = new JSONArray();

                                    Log.d("Success", "");
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                                    // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                    Log.d("onFailure: ", "OK");
                                }

                                @Override
                                public void onRetry(int retryNo) {
                                    // called when request is retried
                                    Log.d("onRetry: ", "OK");
                                }
                            });


                            Log.d("posicion", "" + lista.this.number);

                        }

                        public void onNothingSelected(AdapterView<?> arg0) {// do nothing
                        }

                    });

                    //JSONArray jsonNombres = new JSONArray(jsonArray.getJSONArray("metas"));
                    //Log.d("Hola", jsonNombres.toString());
                    //JSONArray jsonArray = new JSONArray(data);

                    //String texto = "";
                    //Log.d("JSON ARRAY", jsonArray.toString());

                    //for (int i=0; i < jsonArray.length(); i++){
                    //texto = jsonArray.getJSONArray(i).getString("metas");


                    //metas = jsonArray.get("metas").toString();


                    //usuarios.add(texto);
                    //Usuario.monedas = new Integer(texto);
                    //HomeActivity.textViewCantidadMonedas.setText(""+Usuario.monedas);
                    //}
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Mensaje", "No lo puedo convertir a json");
                }


                //JSONObject jsonObject = new JSONObject();
                //jsonObject.getJSONObject("as");
                //JSONArray jsonArray = new JSONArray();

                Log.d("Success", "");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("onFailure: ", "OK");
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                Log.d("onRetry: ", "OK");
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
                        Intent int3 = new Intent(getApplicationContext(), MainActivity.class);
                        //Intent int3 = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(int3);
                        startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                        break;
                    case R.id.perfil:
                        //Intent int4 = new Intent(getApplicationContext(), registro.class);
                        Intent int4 = new Intent(lista.this,registro.class);
                        startActivity(int4);
                        startActivity(new Intent(getBaseContext(), registro.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                        break;
                    case R.id.Glosario:
                        Intent int5 = new Intent(lista.this,glosario.class);
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
public void cancelar(View v){

    Intent int3 = new Intent(getApplicationContext(), MainActivity.class);
    startActivity(int3);
    startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
    finish();

}


    public void guardarCarbos(View v){



        Intent explicit_intent = new Intent(this, MainActivity.class);
        String auxTexto1= carbosTotales.getText().toString();

        explicit_intent.putExtra("Carbos",auxTexto1);

        startActivity(explicit_intent);



    }


}

