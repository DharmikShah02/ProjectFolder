package com.societypay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.BackingStoreException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Transaction extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText Name,Contact,Amount,Flatno;
    String TextName,TextContact,TextAmount,TextFlatNo;
    Button Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        //Checkout.setKeyID("<YOUR_KEY_ID>");
        //for preload the page then user not to wait for payment
        Checkout.preload(getApplicationContext());

        Name = findViewById(R.id.Payername);
        Contact = findViewById(R.id.PayerContactNo);
        Amount = findViewById(R.id.PayerAmount);
        Flatno = findViewById(R.id.PayerFlatNo);
        Back=findViewById(R.id.btndashboard);
        Button btnpay = (Button) findViewById(R.id.Pay);
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
                //gettext from edittext
                 TextName=Name.getText().toString().trim();
                 TextContact=Contact.getText().toString().trim();
                 TextAmount=Amount.getText().toString().trim();
                 TextFlatNo=Flatno.getText().toString().trim();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Transaction.this,Dashboard.class);
                startActivity(intent);
            }
        });
    }

    public void startPayment() {
        final Activity activity=this;
        final Checkout checkout=new Checkout();

        JSONObject object=new JSONObject();
        try {
            //all name and value ,description,currency declared here in try catch block through json object
            object.put("name","Society Pay");
            object.put("description","Maintainence Payment");
            object.put("image",""+R.drawable.applogo);
            object.put("currency","INR");
            object.put("amount","80000");//this value will be myltiplied by 100."500" = INR 5.00

            //prefill iteams .the iteams will be prefilled by default this value will be in box
            JSONObject prefill=new JSONObject();
            prefill.put("email","dharmikshah090@gmail.com");
            prefill.put("contact","9428504246");
            object.put("prefill",prefill);

            checkout.open(activity,object);
        } catch (JSONException e) {
            Toast.makeText(this, "Exception:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //for payment successfull or not
    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "PAYMENT SUCESSFUL", Toast.LENGTH_SHORT).show();


            /*Intent int1=new Intent(this,Reciept.class);
            int1.putExtra("TextName",TextName);
            int1.putExtra("TextAmount",TextAmount);
            int1.putExtra("TextFlatNo",TextFlatNo);
            int1.putExtra("TextContact",TextContact);
            startActivity(int1);*/

        // Access a Cloud Firestore instance from your Activity
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> userdata = new HashMap<>();
        userdata.put("Name", Name.getText().toString().trim());
        userdata.put("Flat_no", Flatno.getText().toString().trim());
        userdata.put("MobileNo",Contact.getText().toString().trim());
        userdata.put("Amount",Amount.getText().toString().trim());

        db.collection("UserDataR")
                .add(userdata)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Data Successfully Entered",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "PAYMENT UNSUCESSFUL", Toast.LENGTH_SHORT).show();
    }
    public void verify()
    {
        if(!validateName()){
            Name.setError("At Least 3 Character");
        }
        else if (!validateamount()){
            Amount.setError("Please Enter Amount");
        }
        else if(!validateflatno()){
            Flatno.setError("Enter Flat No");
        }
        else if(!validatecontact()){
            Contact.setError("Enter Mobile Number");
        }
        else{
            startPayment();
        }
    }
    private boolean validateName(){
        boolean valid = true;
        String userInput = Name.getText().toString().trim();

        if (userInput.isEmpty() || userInput.length() < 3) {
            Name.setError("at least 3 characters");
            valid = false;
            //Log.e("error in",userInput);
        } else {
            Name.setError(null);
        }
        return valid;
    }
    private boolean validateamount(){
        boolean valid=true;
        String userAmount=Amount.getText().toString().trim();
        if (userAmount.isEmpty()) {
            Amount.setError("Amount Will Not Be Empty");
            valid = false;

        } else {
            Amount.setError(null);
        }
        return valid;
    }
    private boolean validateflatno(){
        boolean check=true;

        String flatNo=Flatno.getText().toString().trim();
        if (flatNo.isEmpty()){
            Flatno.setError("Enter FlatNo");
            check=false;
        }
        else {
            Flatno.setError(null);
        }
        return check;
    }
    private boolean validatecontact(){
        boolean valid =true;

        String mobileInput = Contact.getText().toString().trim();
        if (mobileInput.isEmpty() || mobileInput.length()!=10) {
            Contact.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            Contact.setError(null);
        }
        return valid;
    }
}
