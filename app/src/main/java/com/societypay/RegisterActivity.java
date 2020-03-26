package com.societypay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +                   // at least 1 digit
                    "(?=.*[a-z])" +                   // at least 1 lower case letter
                    "(?=.*[A-Z])" +                   // at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +              // at least 1 special character
                    "(?=\\S+$)" +                     // no white spaces
                    ".{6,}" +                 // at least 6 character
                    "$");
    EditText inputtextname;
    EditText inputtextpass;
    EditText inputtextmobileno;
    EditText inputtextflatno;
    Button btnsignup;
    String TAG=RegisterActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private EditText mPhoneNumberField;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnsignup= findViewById(R.id.signup);
        mPhoneNumberField = (EditText) findViewById(R.id.phoneno);
        mAuth = FirebaseAuth.getInstance();
        inputtextname=findViewById(R.id.rname);
        inputtextpass=findViewById(R.id.rpass);
        inputtextmobileno=findViewById(R.id.MobileNo);
        inputtextflatno=findViewById(R.id.rflatno);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register();
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
                // Access a Cloud Firestore instance from your Activity
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Name", inputtextname.getText().toString().trim());
                user.put("Flat_no", inputtextflatno.getText().toString().trim());
                user.put("MobileNo",inputtextmobileno.getText().toString().trim());

                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Not Registered",Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });

    }

    //validations
    public void register(){
        if(!validateName()){
            inputtextname.setError("At Least 3 Character");
        }


        else if(!validateMobile()){
            inputtextmobileno.setError("Enter Valid Mobile Number");
        }

        else if(!validatePassword()){
            inputtextpass.setError("Password too weak");
        }
        else if(!validateFlatno()){
            inputtextflatno.setError("Enter Flat No");
        }

        else{
            Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_LONG).show();
            Intent int2 = new Intent(RegisterActivity.this,VerifyPhone.class);
            int2.putExtra("phonenumber",inputtextmobileno.getText().toString().trim());
            startActivity(int2);
            finish();
        }
    }


    private boolean validateName(){
        boolean valid = true;
        String userInput = inputtextname.getText().toString().trim();

        if (userInput.isEmpty() || userInput.length() < 3) {
            inputtextname.setError("at least 3 characters");
            valid = false;
            //Log.e("error in",userInput);
        } else {
            inputtextname.setError(null);
        }
        return valid;
    }

    public boolean validatePassword(){
        String passwordInput = inputtextpass.getText().toString().trim();


        if(passwordInput.isEmpty()){
            inputtextpass.setError("Field can't be empty");
            return false;
        }

        else if(!PASSWORD_PATTERN.matcher(passwordInput).matches()){
            inputtextpass.setError("Password too weak");
            return false;
        }

        else {
            inputtextpass.setError(null);
            return true;
        }
    }

    private boolean validateMobile(){
        boolean valid =true;

        String mobileInput = inputtextmobileno.getText().toString().trim();
        if (mobileInput.isEmpty() || mobileInput.length()!=13) {
            inputtextmobileno.setError("Enter Valid Mobile Number");
            valid = false;
            //Log.e("error in",mobileInput);
        } else {
            inputtextmobileno.setError(null);
        }
        return valid;
    }

    private boolean validateFlatno(){
        boolean check=true;

        String flatNo=inputtextmobileno.getText().toString().trim();
        if (flatNo.isEmpty()){
            inputtextmobileno.setError("Enter FlatNo");
            check=false;
        }
        else {
            inputtextmobileno.setError(null);
        }
        return check;
    }

    //validation complete
    private void VerificationNumber(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Intent int1;
                            int1=new Intent(RegisterActivity.this,Dashboard.class);
                            startActivity(int1);
                        }
                    }
                });
    }
}
