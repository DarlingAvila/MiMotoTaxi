package activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.darling.mimototaxi.R;

import client.RegistroActivity2;
import driver.RegisterDriverActivity;


public class SelectOptionAuthenticActivity2 extends AppCompatActivity {

    Toolbar mToolbar;
    Button mButtonGoToLogin;
    Button mButtonGoToRegister;
    SharedPreferences mPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_authentic2);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Seleccionar una opción");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonGoToLogin = findViewById(R.id.btnGoToLogin);
        mButtonGoToRegister = findViewById(R.id.btnGoToRegister);

        mButtonGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
        mButtonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
                
            }
        });

        mPref = getApplicationContext().getSharedPreferences(  "typeUser", MODE_PRIVATE);


    }

    public void goToLogin() {
        Intent intent = new Intent(SelectOptionAuthenticActivity2.this, LoginActivity2.class);
        startActivity(intent);
    }

    public void goToRegister() {
        String typeUser = mPref.getString("user","");
        if (typeUser.equals("client")){

            Intent intent = new Intent(SelectOptionAuthenticActivity2.this, RegistroActivity2.class);
            startActivity(intent);

        }
        else{
            Intent intent = new Intent(SelectOptionAuthenticActivity2.this, RegisterDriverActivity.class);
            startActivity(intent);
        }

    }

}