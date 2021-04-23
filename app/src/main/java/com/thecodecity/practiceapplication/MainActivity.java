package com.thecodecity.practiceapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thecodecity.practiceapplication.Model.Users;
import com.thecodecity.practiceapplication.Prevalent.Prevalent;

public class MainActivity extends AppCompatActivity {
   private EditText txtusername, txtpassword;
    Button btn_login, btn_signup;
private ProgressDialog loadingBar;
private String parentDbNode="Users";
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
        Log.i("tag","Entered login user method");
        String userName= txtusername.getText().toString();
String password = txtpassword.getText().toString();
if(TextUtils.isEmpty(userName)){
    Toast.makeText(this, "Enter username", Toast.LENGTH_SHORT).show();
}
else if(TextUtils.isEmpty(password)){
    Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show();
}
else{
  /*  loadingBar.setTitle("Login Account");
    loadingBar.setMessage("Please wait, while we are checking the credentials.");
    loadingBar.setCanceledOnTouchOutside(false);
    loadingBar.show();
    */

    AllowAcessToAccount(userName,password);
}
    }

    private void AllowAcessToAccount(final String userName, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDbNode).child(userName).exists()){
Users userData = snapshot.child(parentDbNode).child(userName).getValue(Users.class);
 if(userData.getName().equals(userName)){
    if(userData.getPassword().equals(password)){
       //  Toast.makeText(MainActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
        //  loadingBar.dismiss();
        Prevalent.currentOnlineUser=userData;
        startActivity(new Intent(getApplicationContext(),SendingEvent.class)) ;


/*
        if(userData.getRole().equals("Customer")){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class)) ;
            Toast.makeText(MainActivity.this, "I'm a customer", Toast.LENGTH_SHORT).show();
        }
        else if(userData.getRole().equals("Retailer")){
            Toast.makeText(MainActivity.this, "I'm a retailer", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),RetailerCategoryActivity.class)) ;
        }
        else{
            startActivity(new Intent(getApplicationContext(), SendingEvent.class));
            Toast.makeText(MainActivity.this, "I'm a wholesaler", Toast.LENGTH_SHORT).show();

        }
        */


    }
    else{
      //  loadingBar.dismiss();
        Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
    }
 }
                }
                else{
                    Toast.makeText(MainActivity.this,"Invalid user name",Toast.LENGTH_SHORT).show();
                 //   loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}