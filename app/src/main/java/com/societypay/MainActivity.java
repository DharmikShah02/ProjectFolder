package com.societypay;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    /*private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +                   // at least 1 digit
                    "(?=.*[a-z])" +                   // at least 1 lower case letter
                    "(?=.*[A-Z])" +                   // at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +              // at least 1 special character
                    "(?=\\S+$)" +                     // no white spaces
                    ".{6,}" +                 // at least 6 character
                    "$");*/
    EditText textinputno;
    // EditText textinputpass;
    Button login, register;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    private EditText mPhoneNumberField;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhoneNumberField = (EditText) findViewById(R.id.phoneno);


        //Validation
        textinputno = findViewById(R.id.phoneno);
        //textinputpass=findViewById(R.id.pass);
        login = findViewById(R.id.LoginBtn);
        register = findViewById(R.id.RegisterBtn);
        mAuth = FirebaseAuth.getInstance();

        //to retrvie mobile no
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document("MobileNo");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d("DocumentSnapshotData", "DocumentSnapshot data:" + document.getData());

                    } else {
                        Log.d("NoSuchDocument", "No such document");
                    }
                } else {
                    Log.d("GetFailed", "get failed with ", task.getException());
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {
                        mVerificationInProgress = false;
                        signInWithPhoneAuthCredential(credential);

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        mVerificationInProgress = false;
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            mPhoneNumberField.setError("Invalid phone number.");
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        mVerificationId = verificationId;
                        mResendToken = token;
                    }

                };
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1 = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(int1);
            }
        });

    }

    public void login() {
        if (!validateNumber()) {
            textinputno.setError("Please Enter A Valid Number");
        }

        //else if(!validatePassword()){
        //  textinputpass.setError("Password too weak");
        //}

        else {
            //Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_LONG).show();
            //Intent int2 = new Intent(MainActivity.this,Dashboard.class);
            //startActivity(int2);
            Intent int3 = new Intent(MainActivity.this, VerifyPhone.class);
            int3.putExtra("phonenumber", textinputno.getText().toString().trim());
            startActivity(int3);
        }
    }

    private boolean validateNumber() {
        boolean valid = true;

        String mobileInput = textinputno.getText().toString().trim();
        if (mobileInput.isEmpty() || mobileInput.length() != 13) {
            textinputno.setError("Enter Valid Mobile Number");
            valid = false;
            //Log.e("error in",textinputno);
        } else {
            textinputno.setError(null);
        }
        return valid;
    }


    /*private boolean validatePassword(){
        String passwordInput = textinputpass.getText().toString().trim();

        if(passwordInput.isEmpty()){
            textinputpass.setError("Field can't be empty");
            return false;
        }

        else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            textinputpass.setError("Password too weak");
            return false;
        }

        else {
            textinputpass.setError(null);
            return true;
        }
    }*/

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Intent int1;
                            int1 = new Intent(MainActivity.this, Dashboard.class);
                            startActivity(int1);
                        }
                    }
                });
    }

}
