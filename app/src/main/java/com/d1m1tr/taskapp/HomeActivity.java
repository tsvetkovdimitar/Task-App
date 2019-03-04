package com.d1m1tr.taskapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private FloatingActionButton penBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Task App");

        penBtn = findViewById(R.id.pen_btn);

        penBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myDialog = new AlertDialog.Builder(HomeActivity.this);

                LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);

                View myView = inflater.inflate(R.layout.inputfireld, null);

                myDialog.setView(myView);

                AlertDialog dialog = myDialog.create();

                myDialog.show();

            }
        });

    }

}
