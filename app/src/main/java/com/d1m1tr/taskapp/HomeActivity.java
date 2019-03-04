package com.d1m1tr.taskapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.d1m1tr.taskapp.model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private FloatingActionButton penBtn;

    //Firebase
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    //RecyclerView
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Task App");

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();

        String uId = mUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("TaskNote").child(uId);

        mDatabase.keepSynced(true);

        //RecyclerView
        recyclerview = findViewById(R.id.resycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(layoutManager);

        penBtn = findViewById(R.id.pen_btn);

        penBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myDialog = new AlertDialog.Builder(HomeActivity.this);

                LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);

                View myView = inflater.inflate(R.layout.inputfireld, null);

                myDialog.setView(myView);

                final AlertDialog dialog = myDialog.create();

                final EditText title = myView.findViewById(R.id.title);
                final EditText note = myView.findViewById(R.id.note);

                Button saveButton = myView.findViewById(R.id.btn_save);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String mTitle = title.getText().toString().trim();
                        String mNote = note.getText().toString().trim();

                        if(TextUtils.isEmpty(mTitle)){

                            title.setError("Required Field..");
                            return;

                        }
                        if(TextUtils.isEmpty(mNote)){

                            note.setError("Required Field..");
                            return;

                        }

                        String id = mDatabase.push().getKey();
                        String date = DateFormat.getDateInstance().format(new Date());

                        Data data = new Data(mTitle, mNote, date, id);

                        mDatabase.child(id).setValue(data);

                        Toast.makeText(getApplicationContext(), "Creating task..", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

    }

    @Override
    protected void onStart(){

        super.onStart();

        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>

                (

                        Data.class,
                        R.layout.item_data,
                        MyViewHolder.class,
                        mDatabase

                ) {
            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, Data model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setNote(model.getNote());
                viewHolder.setDate(model.getDate());

            }
        };

        recyclerview.setAdapter(adapter);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View myView;

        public MyViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
        }

        public void setTitle(String title){

            TextView mTitle = myView.findViewById(R.id.data_title);
            mTitle.setText(title);

        }

        public void setNote(String note){

            TextView mNote = myView.findViewById(R.id.data_note);
            mNote.setText(note);

        }

        public void setDate(String date){

            TextView mDate = myView.findViewById(R.id.date);
            mDate.setText(date);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){

            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

        }

        return  super.onOptionsItemSelected(item);

    }


}
