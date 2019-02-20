package com.d1m1tr.taskapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView signup;

    private EditText email;
    private EditText pass;
    private Button btnLogin;

    //Firebase...
    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){

            startActivity(new Intent(getApplicationContext(),HomeActivity.class));

        }

        mDialog = new ProgressDialog(this);

        signup = findViewById(R.id.signup_txt);

        email = findViewById(R.id.email_login);
        pass = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.login_btn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mEmail = email.getText().toString().trim();
                String mPass = pass.getText().toString().trim();

                if(TextUtils.isEmpty(mEmail)){

                    email.setError("Please, enter your email.");
                    return;

                }

                if(TextUtils.isEmpty(mPass)){

                    pass.setError("Please, enter a password.");
                    return;

                }

                mDialog.setMessage("Loading...");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(mEmail, mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            mDialog.dismiss();

                        }
                        else{

                            Toast.makeText(getApplicationContext(), "There was a problem. Try again.", Toast.LENGTH_LONG).show();
                            mDialog.dismiss();

                        }

                    }
                });

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

            }
        });

    }
}
