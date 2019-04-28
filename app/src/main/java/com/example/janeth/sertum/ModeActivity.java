package com.example.janeth.sertum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ModeActivity extends AppCompatActivity {
    private Button Auto;
    private Button Manual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);

        Auto =(Button)findViewById(R.id.btnAuto);
        Manual =(Button)findViewById(R.id.btnManual);

        Auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextAuto();
            }
        });

        Manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextManual();
            }
        });
    }
    private void nextAuto(){
        Intent intent = new Intent (ModeActivity.this, AutoActivity.class);
        startActivity(intent);
    }
    private void nextManual(){
        Intent intent = new Intent (ModeActivity.this, ManualActivity.class);
        startActivity(intent);
    }
}