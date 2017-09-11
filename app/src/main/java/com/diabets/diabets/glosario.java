package com.diabets.diabets;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class glosario extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glosario);



        //////////////////////////////////////////
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.btnnavigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menuB = bottomNavigationView.getMenu();
        MenuItem menuItem = menuB.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


               switch (item.getItemId() )
                {
                    case R.id.calculadora:

                        Intent int6 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(int6);
                        startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();

                        break;
                    case R.id.perfil:
                        //Intent int4 = new Intent(getApplicationContext(), registro.class);
                        Intent int4 = new Intent(glosario.this,registro.class);
                        startActivity(int4);
                        startActivity(new Intent(getBaseContext(), registro.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        finish();
                        break;
                    case R.id.Glosario:
                        //Intent int5 = new Intent(glosario.this,glosario.class);
                        //startActivity(int5);
                        break;


                }
                return true;
            }
        });
        //////////////////////////////////////////
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
