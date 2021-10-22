package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.project1.R;

public class welcomeActivity extends AppCompatActivity {
AppCompatButton btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btn1 =findViewById(R.id.btn1);
        btn2 =findViewById(R.id.btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(welcomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(welcomeActivity.this,DangkyActivity.class);
                startActivity(intent);
            }
        });

    }
}