package activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.darling.mimototaxi.R;
import com.google.firebase.auth.FirebaseAuth;

import client.MapClientActivity;
import driver.MapDriverActivity;

public class MainActivity extends AppCompatActivity {

    Button nButtonIAmClient;
    Button nButtonIAmDriver;

    //para diferenciar si es conducto o no
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPref = getApplicationContext().getSharedPreferences(  "typeUser", MODE_PRIVATE);
        final SharedPreferences.Editor editor = mPref.edit();

        nButtonIAmClient = findViewById(R.id.btnIAmClient);
        nButtonIAmDriver = findViewById(R.id.btnIAmDriver);
        
        nButtonIAmClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString( "user",  "client");
                editor.apply();
                goToSelectAuth();
                
            }
        });

        nButtonIAmDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString( "user", "driver");
                editor.apply();
                goToSelectAuth();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
        String user = mPref.getString("user", "");

            if(user.equals("client")){

                Intent intent = new Intent(MainActivity.this, MapClientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else{

                Intent intent = new Intent(MainActivity.this, MapDriverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            // Toast.makeText(LoginActivity2.this, "El login se realizo exitosamente", Toast.LENGTH_SHORT).show();


        }
    }

    private void goToSelectAuth() {
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthenticActivity2.class);
        startActivity(intent);
    }
}