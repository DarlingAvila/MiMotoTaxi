package client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.darling.mimototaxi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;
import includes.MyToolbar;
import models.Client;
import providers.AuthProvider;
import providers.ClientProvider;

public class RegistroActivity2 extends AppCompatActivity {


    SharedPreferences mPref;

    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;

    //vistas

    Button mButtonRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputPassword;

    AlertDialog mDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        MyToolbar.show(this, "Registrar usuario",true);
        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();
        mDialog = new SpotsDialog.Builder().setContext(RegistroActivity2.this).setMessage("Espere un momento").build();

        mPref = getApplicationContext().getSharedPreferences(  "typeUser", MODE_PRIVATE);
        mButtonRegister= findViewById(R.id.btnRegister);
        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputName = findViewById(R.id.textInputName);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRegister();
                
            }
        });

    }

    private void clickRegister() {
        final String name = mTextInputName.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if(password.length() >=6 ){
                mDialog.show();
               register(name,email,password);
            }
        else {

            Toast.makeText(this,"La contraseña debe tener al menos 6 caracteres",Toast.LENGTH_SHORT).show();
        }
    }
    else{
            Toast.makeText(this, "Ingrese datos en los campos", Toast.LENGTH_SHORT).show();

        }
    }
    void  register(final  String name, final String email, String password){

        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mDialog.hide();
                if(task.isSuccessful()){
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Client client = new Client(id, name, email);
                    create(client);
                }
                else{
                    Toast.makeText(RegistroActivity2.this , "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void create(Client client){
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //Toast.makeText(RegistroActivity2.this,"El registro se realizo correctamente",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistroActivity2.this, MapClientActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(RegistroActivity2.this,"No se pudo crear el cliente",Toast.LENGTH_SHORT).show();


                }
            }
        });


    }

    /*
    private void saveUser(String id, String name, String email) {

        String selectedUser= mPref.getString( "user", " ");
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        if(selectedUser.equals("driver")){

            mDatabase.child("User").child("driver").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegistroActivity2.this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegistroActivity2.this,"Fallo el registro",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else  if(selectedUser.equals("client")){
            mDatabase.child("User").child("client").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(RegistroActivity2.this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegistroActivity2.this,"Fallo el registro",Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }


    }

     */
}

