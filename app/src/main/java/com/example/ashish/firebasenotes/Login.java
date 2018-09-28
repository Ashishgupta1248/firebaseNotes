package com.example.ashish.firebasenotes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText userEmail ,userPassword;
    Button login;
    TextView register,forgot_password;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userEmail = findViewById(R.id.emailUser);
        userPassword = findViewById(R.id.passwordUser);
        login = findViewById(R.id.button);
        register=findViewById(R.id.register);
        final ProgressDialog progressDialog=new ProgressDialog(Login.this);
        forgot_password=findViewById(R.id.forgot_password);
        firebaseAuth = FirebaseAuth.getInstance();
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,resetPassword.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,register_user.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Loginin in ");
                progressDialog.setMessage("please wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                userLogin();
            }
        });
    }
    private void userLogin() {

        final String email=userEmail.getText().toString();
        final String password=userPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Login.this, "login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this,MainActivity.class));
                    finish();
                }else
                {
                    Toast.makeText(Login.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
