package com.example.ashish.firebasenotes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword extends AppCompatActivity {
    EditText mail;
    Button reset_password;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activty);

        firebaseAuth =FirebaseAuth.getInstance();
        mail=findViewById(R.id.mail);
        reset_password=findViewById(R.id.reset_password);
        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    firebaseAuth.sendPasswordResetEmail(mail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(resetPassword.this, "check your email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(resetPassword.this,Login.class));
                            }else{
                                Toast.makeText(resetPassword.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            }
        });

       /* final ProgressDialog progressDialog = new ProgressDialog(resetPassword.this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();

            }
        }, 5000);*/

    }
}
