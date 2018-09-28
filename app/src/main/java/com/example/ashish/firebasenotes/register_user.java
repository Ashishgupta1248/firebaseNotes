package com.example.ashish.firebasenotes;

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

public class register_user extends AppCompatActivity {
    EditText name,lastname,email,phonenumber,password;
    FirebaseAuth firebaseAuth;
    Button register;
    TextView already_Regsiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        phonenumber = findViewById(R.id.phonenumber);
        password = findViewById(R.id.password);
        register = findViewById(R.id.Register);
        firebaseAuth = FirebaseAuth.getInstance();
        already_Regsiter=findViewById(R.id.already_Regsiter);
        already_Regsiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(register_user.this,Login.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userregister();
            }


        });
    }
    private void userregister() {
        String mail=email.getText().toString();
        String pass=password.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(register_user.this, "Registratoin Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(register_user.this,MainActivity.class));
                    finish();
                }else
                {
                    Toast.makeText(register_user.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
