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

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button registerButton;

    private TextView login_text;

    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mDialog = new ProgressDialog(this);

        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        registerButton = findViewById(R.id.reg_btn);

        login_text = findViewById(R.id.login_txt);

        login_text.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if(TextUtils.isEmpty(mEmail)){

                    email.setError("Please, enter your email");
                    return;

                }
                if(TextUtils.isEmpty(mPass)){

                    password.setError("Please, enter a password");
                    return;

                }

                mDialog.setMessage("Loading...");
                mDialog.show();;

                mAuth.createUserWithEmailAndPassword(mEmail, mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                            mDialog.dismiss();

                        }
                        else{

                            Toast.makeText(getApplicationContext(), "There was a problem", Toast.LENGTH_LONG).show();
                            mDialog.dismiss();

                        }

                    }
                });

            }
        });

    }
}
