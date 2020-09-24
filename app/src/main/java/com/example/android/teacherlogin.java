package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class teacherlogin extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String item;
    String message;
    Toolbar mToolbar;
    private static long back_pressed;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherlogin);
        Spinner spinner2 = findViewById(R.id.spinner2);


        //to get username from login page
        Bundle bundle1 = getIntent().getExtras();
        message = bundle1.getString("message");
        mToolbar= findViewById(R.id.takeattendancebar);
       // setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(message+"'s Dashboard  - "+date);

        TextView txtView = findViewById(R.id.textView1);
        txtView.setText("Welcome : "+message);
        spinner2.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("CO1I");
        categories.add("CO2I");
        categories.add("CO3I");
        categories.add("CO4I");
        categories.add("CO5I");
        categories.add("CO6I");

        categories.add("ME1I");
        categories.add("ME2I");
        categories.add("ME3I");
        categories.add("ME4I");
        categories.add("ME5I");
        categories.add("ME6I");

        categories.add("CE1I");
        categories.add("CE2I");
        categories.add("CE3I");
        categories.add("CE4I");
        categories.add("CE5I");
        categories.add("CE6I");

        categories.add("ETE1I");
        categories.add("ETE2I");
        categories.add("ETE3I");
        categories.add("ETE4I");
        categories.add("ETE5I");
        categories.add("ETE6I");


        categories.add("EE1I");
        categories.add("EE2I");
        categories.add("EE3I");
        categories.add("EE4I");
        categories.add("EE5I");
        categories.add("EE6I");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void takeAttendanceButton(View v){
        Bundle basket= new Bundle();
        basket.putString("class_selected", item);
        basket.putString("tid", message);


        Intent intent = new Intent(this, takeAttendance.class);
        intent.putExtras(basket);
        startActivity(intent);
    }
    public void  previous_records(View v){
        Bundle basket= new Bundle();
        basket.putString("class_selected", item);
        basket.putString("tid", message);


        Intent intent = new Intent(this, teacher_attendanceSheet.class);
        intent.putExtras(basket);
        startActivity(intent);
    }


    public void logoutTeacher(View view) {
        Intent logoutTeacher=new Intent(teacherlogin.this,LoginActivity.class);
        logoutTeacher.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logoutTeacher);
    }


}
