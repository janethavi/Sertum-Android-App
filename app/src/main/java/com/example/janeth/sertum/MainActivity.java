package com.example.janeth.sertum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private TextView mNope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.etEmail);
        mPassword = (EditText) findViewById(R.id.etPassword);
        mLogin = (Button) findViewById(R.id.btnSignin);
        mNope=(TextView)findViewById(R.id.tvNope);
        progressDialog=new ProgressDialog(this);

        mLogin.setOnClickListener(this);
        mNope.setOnClickListener(this);

    }
    private void userLogin(){
        String Email=mEmail.getText().toString().trim();
        String Password= mPassword.getText().toString().trim();

        if(TextUtils.isEmpty(Email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Verifying Your details.");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        progressDialog.dismiss();
                        try{
                            if(user.isEmailVerified()){
                                startActivity(new Intent(getApplicationContext(),TypeActivity.class));
                                finish();
                                Toast.makeText(getApplicationContext(), "WELCOME TO SERTUM", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Check Email and Password", Toast.LENGTH_LONG).show();
                                FirebaseAuth.getInstance().signOut();
                            }
                        }
                        catch (NullPointerException e){
                            Toast.makeText(getApplicationContext(), "Failed to Login", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void userRegister(){
        Intent intent = new Intent (MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
       if(v==mLogin){
           userLogin();
       }
       if(v==mNope){
           userRegister();
       }
    }
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this,TypeActivity.class));
        }
    }
}