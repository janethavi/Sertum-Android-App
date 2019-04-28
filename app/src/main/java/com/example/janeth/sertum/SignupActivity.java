package com.example.janeth.sertum;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
private EditText mEmail;
private EditText mPassword;
private Button mReg;
private FirebaseAuth firebaseAuth;

private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth=FirebaseAuth.getInstance();

        mEmail=(EditText) findViewById(R.id.etEmail);
        mPassword=(EditText) findViewById(R.id.etPassword);
        mReg=(Button)findViewById(R.id.btnSignup);
        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        progressDialog=new ProgressDialog(this);
    }

    private void registerUser(){
        String email=mEmail.getText().toString().trim();
        String password=mPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Registering User..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            validatingEmail();
                            FirebaseAuth.getInstance().signOut();
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),"Registeration Failed",Toast.LENGTH_SHORT).show();
                            mEmail.setText("");
                            mPassword.setText("");
                        }
                    }
                });
    }
    public void validatingEmail(){
       FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null ){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignupActivity.this,"Email verification sent",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(SignupActivity.this,"Email verification sending failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
