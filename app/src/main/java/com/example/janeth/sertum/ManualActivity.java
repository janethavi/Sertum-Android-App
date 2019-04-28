package com.example.janeth.sertum;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import java.io.OutputStream;
import java.io.IOException;
import android.view.MotionEvent;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;
import java.util.UUID;
import java.lang.String;
import com.google.firebase.auth.FirebaseAuth;
public class ManualActivity extends AppCompatActivity  {
    ImageButton Up;
    ImageButton Down;
    ImageButton Left;
    ImageButton Right;
    ImageButton Stop;
    ImageButton Exit;
    String command;
    private Button Options;

    private OutputStream outputStream;
    public BluetoothDevice device;
    public BluetoothSocket socket;
    private final String DEVICE_ADDRESS = "98:D3:32:21:0F:0C"; //MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    BluetoothAdapter bluetoothAdapter;
    ProgressDialog progressDialog;
    private static final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        Up = (ImageButton) findViewById(R.id.btnUp);
        Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward();
            }
        });
        Down = (ImageButton) findViewById(R.id.btnDown);
        Down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reverse();
            }
        });
        Left = (ImageButton) findViewById(R.id.btnLeft);
        Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left();
            }
        });
        Right = (ImageButton) findViewById(R.id.btnRight);
        Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right();
            }
        });
        Stop = (ImageButton) findViewById(R.id.btnStop);
        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Options = (Button) findViewById(R.id.btnOptions);
        Options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ManualActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });
        Exit=(ImageButton)findViewById(R.id.imgbtnExit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent (ManualActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void forward(){
        Up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "1";

                    try
                    {
                        outputStream.write(command.getBytes());; //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "5";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }
                return false;
            }

        });}
    public void reverse(){
       Down.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    command = "4";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    command = "5";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {

                    }

                }
                return false;
            }
        });

    }
    private void left()
    {Left.setOnTouchListener(new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                command = "3";

                try
                {
                    outputStream.write(command.getBytes());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else if(event.getAction() == MotionEvent.ACTION_UP)
            {
                command = "5";
                try
                {
                    outputStream.write(command.getBytes());
                }
                catch(IOException e)
                {

                }

            }
            return false;
        }
    });

    }
    private void right()
    {
        Right.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    command = "2";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    command = "5";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });
    }

    private void stop()
    {
        Stop.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    command = "5";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    command = "5";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }
}