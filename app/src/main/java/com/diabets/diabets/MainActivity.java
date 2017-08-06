package com.diabets.diabets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;








public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button boton = (Button) findViewById(R.id.btnalimentos);
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent int1 = new Intent(getApplicationContext(), lista.class);
                startActivity(int1 );
            }
        });

        Button botona = (Button) findViewById(R.id.btncalcular);
        botona.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.numcal);
                textView.setVisibility(View.VISIBLE);
                TextView textView2 = (TextView) findViewById(R.id.textcal);
                textView2.setVisibility(View.VISIBLE);
            }
        });





        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btnnavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.calculadora) {
                    Intent int3 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(int3);

                } else if (item.getItemId() == R.id.registro) {
                    Intent int4 = new Intent(getApplicationContext(), registro.class);
                    startActivity(int4);

                }  else if (item.getItemId() == R.id.historial) {
                    Intent int2 = new Intent(getApplicationContext(), historial.class);
                    startActivity(int2);
                }


                return true;
            }
        });


    }
}

