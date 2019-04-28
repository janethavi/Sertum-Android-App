package com.example.janeth.sertum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class AutoActivity extends AppCompatActivity  {
    Switch simpleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        Switch simpleSwitch = (Switch) findViewById(R.id.swtOnSertum);

//        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//            public void OnCheckedChanged(CompoundButton compoundButton, boolean b){
//
//            }
//        }
    }


    }

