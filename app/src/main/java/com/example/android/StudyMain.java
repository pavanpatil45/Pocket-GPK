package com.example.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class StudyMain extends AppCompatActivity {
    public Button b1;
    public Button b2;
    public Button b3;
    public Button b4;
    public ImageButton b5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_main);


        b1 = findViewById(R.id.pdfbutton);
        b2 = findViewById(R.id.pptbutton);
        b3 = findViewById(R.id.docbutton);
        b4 = findViewById(R.id.xlsbutton);
        b5 = findViewById(R.id.exambutton);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(StudyMain.this, PDFActivity.class);
                startActivity(intent1);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(StudyMain.this, PPTActivity.class);
                startActivity(intent2);
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(StudyMain.this, DOCActivity.class);
                startActivity(intent3);
            }
        });


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(StudyMain.this, XLSActivity.class);
                startActivity(intent4);
            }
        });




        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(StudyMain.this, ExamsActivity.class);
                startActivity(intent5);
            }
        });



    }}