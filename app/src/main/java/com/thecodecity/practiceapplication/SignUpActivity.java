package com.thecodecity.practiceapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    EditText  txtusername,  txtpassword, txtretypepassword, txtphonenumber;
    RadioButton customerButton, retailorButton, wholesalorButton;
    Button Register;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtusername = (EditText) findViewById(R.id.UsernameEditText);
        txtpassword = (EditText) findViewById(R.id.PasswordEditText);
        Register = (Button) findViewById(R.id.RegistrationButton);
        txtretypepassword = (EditText) findViewById(R.id.RetypePasswordEditText1);
        customerButton= (RadioButton) findViewById(R.id.CustomerButton);
        retailorButton = (RadioButton) findViewById(R.id.RetailerButton);
        wholesalorButton = (RadioButton) findViewById(R.id.WhilesalerButton);
        txtphonenumber = (EditText) findViewById(R.id.PhoneNumberEditText);
        loadingBar = new ProgressDialog(this);
    }

    public void createAccount(View view) {
        String userName = txtusername.getText().toString();
        String password= txtpassword.getText().toString();
        String retypePassword = txtretypepassword.getText().toString();
        String phoneNumber = txtphonenumber.getText().toString();
        String userType="";
        if (customerButton.isChecked()){
            userType="Customer";
        }
        if(retailorButton.isChecked()){
            userType="Retailor";
        }
        if(wholesalorButton.isChecked()){
            userType="Wholesalor";
        }
        if (TextUtils.isEmpty(userName))
        {
            Toast.makeText(this, "Please write your username", Toast.LENGTH_SHORT).show();
        }
      else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(retypePassword))
        {
            Toast.makeText(this, "Please retype password", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneNumber))
        {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidateEmail(phoneNumber,userName,password);
        }

    }

    private void ValidateEmail(final String phone,final String name, final String password) {
       final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.child("Users").child(phone).exists()){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);
                    userdataMap.put("name", name);
                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(SignUpActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(SignUpActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(SignUpActivity.this, "This " + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SignUpActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}