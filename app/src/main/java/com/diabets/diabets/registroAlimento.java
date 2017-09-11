package com.diabets.diabets;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class registroAlimento extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alimento);


        //////////////////////////////////////////
    /*    bottomNavigationView = (BottomNavigationView) findViewById(R.id.btnnavigation);
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

                        Intent int6 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(int6);
                        break;
                    case R.id.perfil:
                        //Intent int4 = new Intent(getApplicationContext(), registro.class);
                        Intent int4 = new Intent(registroAlimento.this,registro.class);
                        startActivity(int4);
                        break;
                    case R.id.Glosario:
                        Intent int5 = new Intent(registroAlimento.this,glosario.class);
                        startActivity(int5);
                        break;



                }
                return true;
            }
        });*/

        //////////////////////////////////////////

    }


    public void guardarlista(View view){
        //Textview campo1 = (Textview) findViewById(R.id.editAlimento);
       TextView valor1 = (TextView) findViewById(R.id.editAlimento);
        String ratioU = valor1.getText().toString();

        Log.d("MensajeLisra ", ratioU);



    }
}
