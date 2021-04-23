package com.thecodecity.practiceapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPPActivity extends AppCompatActivity {
    private EditText InputPhoneNumber, InputPassword, EnterOtp;
    private Button LoginButton, SendOtp, VerifyOtp;
    private ProgressDialog loadingBar;
    private TextView Customer, Retailer, Wholesaler, ForgotPassword;
    private ImageView tick;
    private FirebaseAuth mAuth;
    private String parentDbName = "Customer", verificationId, verificationStatus="Verified" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_p);
        LoginButton = findViewById(R.id.login_btn);
        SendOtp = findViewById(R.id.send_otp);
        VerifyOtp = findViewById(R.id.verify_otp);
        InputPassword = findViewById(R.id.login_password_input);
        InputPhoneNumber = findViewById(R.id.login_phone_number_input);
        EnterOtp = findViewById(R.id.enter_OTP);
         Customer = findViewById(R.id.Customer);
        Retailer = findViewById(R.id.Retailer);
         Wholesaler = findViewById(R.id.Wholesaler);
        ForgotPassword = findViewById(R.id.forget_password_link);
        tick = findViewById(R.id.tick);

      loadingBar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        SendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(InputPhoneNumber.getText().toString())))  {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    String phone = "+91" + InputPhoneNumber.getText().toString();
                    sendVerificationCode(phone);
                    SendOtp.setVisibility(View.INVISIBLE);
                    VerifyOtp.setVisibility(View.VISIBLE);
                }
                else {
                    // if the text field is not empty we are calling our
                    // send OTP method for getting OTP from Firebase.
                    Toast.makeText(OTPPActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        VerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(TextUtils.isEmpty(EnterOtp.getText().toString()))) {
                    verifyCode(EnterOtp.getText().toString());
                    Intent intent=new Intent(getApplicationContext(),SendingEvent.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(OTPPActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(OTPPActivity.this, "OTP verification Successful", Toast.LENGTH_SHORT).show();
                            VerifyOtp.setVisibility(View.INVISIBLE);
                            tick.setVisibility(View.VISIBLE);
                            verificationStatus= "Verified";
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(OTPPActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            Toast.makeText(OTPPActivity.this,"OTP Sent to the given Number", Toast.LENGTH_SHORT).show();
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edit text field.
                EnterOtp.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(OTPPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
}