package com.thecodecity.practiceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText txtusername, txtpassword;
    Button btn_login, btn_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtusername = (EditText) findViewById(R.id.UserNameLoginEditText);
        txtpassword = (EditText) findViewById(R.id.PasswordLoginEditText);
        btn_login = (Button) findViewById(R.id.LoginButton);
        btn_signup =(Button) findViewById(R.id.RegistrationButton);

    }

    public void btn_register(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }

    public void LoginUser(View view) {
    }
}