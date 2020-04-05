package com.societypay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Transaction extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText Name,Contact,Amount,Flatno;
    String TextName,TextContact,TextAmount,TextFlatNo;
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
        Button btnpay = (Button) findViewById(R.id.Pay);
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPayment();

                //gettext from edittext
                 TextName=Name.getText().toString().trim();
                 TextContact=Contact.getText().toString().trim();
                 TextAmount=Amount.getText().toString().trim();
                 TextFlatNo=Flatno.getText().toString().trim();
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


            Intent int1=new Intent(this,Reciept.class);
            int1.putExtra("TextName",TextName);
            int1.putExtra("TextAmount",TextAmount);
            int1.putExtra("TextFlatno",TextFlatNo);
            int1.putExtra("TextContact",TextContact);
            startActivity(int1);

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "PAYMENT UNSUCESSFUL", Toast.LENGTH_SHORT).show();
    }
}
