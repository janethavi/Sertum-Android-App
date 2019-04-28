package com.example.janeth.sertum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class TypeActivity extends AppCompatActivity {
    private Button Cleaning;
    private Button Serving;
    Switch TurnOn;
    public TextView Select;
    public ImageView Broom;
    public ImageView Tray;

    private BluetoothAdapter  bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public BluetoothDevice device;
    public BluetoothSocket socket;
    public OutputStream outputStream;
    private final String DEVICE_ADDRESS = "98:D3:32:21:0F:0C"; //MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_ENABLE_BT=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        init();
    }
    public void init(){
        Cleaning=(Button)findViewById(R.id.btnCleaning);
        Serving=(Button)findViewById(R.id.btnServing);
        TurnOn=(Switch)findViewById(R.id.swtOnSertum);
        Select=(TextView)findViewById(R.id.tvType);
        Broom=(ImageView)findViewById(R.id.ivBroom);
        Tray=(ImageView)findViewById(R.id.ivTray);


        Cleaning.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              nextActivity();
          }
        });
        Serving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (TypeActivity.this, ServingActivity.class);
                startActivity(intent);
            }
        });
        TurnOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Visible(Cleaning,Serving,Select,Broom,Tray);
                    BTinit() ;
                    BTconnect();
                }
            else{
                    bluetoothAdapter.disable();
                    Invisible(Cleaning,Serving,Select, Broom, Tray);
            }
        }
    });
}
    private void nextActivity(){
        Intent intent = new Intent (TypeActivity.this, ModeActivity.class);
        startActivity(intent);
    }
    public void Invisible(Button Cleaning,Button Serving,TextView Select,ImageView Broom,ImageView Tray){
        Cleaning.setVisibility(View.INVISIBLE);
        Serving.setVisibility(View.INVISIBLE);
        Select.setVisibility(View.INVISIBLE);
        Broom.setVisibility(View.INVISIBLE);
        Tray.setVisibility(View.INVISIBLE);
    }
    public void Visible(Button Cleaning,Button Serving,TextView Select,ImageView Broom, ImageView Tray){
        Cleaning.setVisibility(View.VISIBLE);
        Serving.setVisibility(View.VISIBLE);
        Select.setVisibility(View.VISIBLE);
        Broom.setVisibility(View.VISIBLE);
        Tray.setVisibility(View.VISIBLE);
    }
    public boolean BTinit()
    {
        boolean found = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) //Checks if the device supports bluetooth
        {
            Toast.makeText(getApplicationContext(), "Device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled()) //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter,REQUEST_ENABLE_BT);
            try
            {
                Thread.sleep(2500);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty()) //Checks for paired bluetooth devices
        {
            Toast.makeText(getApplicationContext(), "Please pair the device first", Toast.LENGTH_SHORT).show();
        }
        else
        {
            for(BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
    public boolean BTconnect()
    {
        boolean connected = true;
        try
        {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID); //Creates a socket to handle the outgoing connection
            socket.connect();
            Toast.makeText(getApplicationContext(),
                    "Connection to bluetooth device successful", Toast.LENGTH_LONG).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            connected = false;
        }
        if(connected)
        {
            try
            {
                outputStream = socket.getOutputStream(); //gets the output stream of the socket
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        return connected;
    }
}