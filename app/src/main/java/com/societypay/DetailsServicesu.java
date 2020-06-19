package com.societypay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsServicesu extends AppCompatActivity {

    TextView textView,textView1,textView2;
    String profession[] = {"Doctor","plumber","liftman","cleaningperons","AcCleaner"};
    String contact[] = {"8963254170","9852475215","7658421389","9856412379","8564972314"};
    String name[] = {"Dr Nikhil Shah","Rahul Thakor","Nilesh Govani","Raju","Nikhil Sharma"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_servicesu);
        textView = (TextView) findViewById(R.id.profession);
        textView1 = (TextView) findViewById(R.id.contact);
        textView2 = (TextView) findViewById(R.id.name);

        setContent();
    }
    private void setContent(){
        String number = getIntent().getStringExtra("number");
        switch (number){
            case "0":
                textView.setText(profession[0]);
                textView1.setText(contact[0]);
                textView2.setText(name[0]);
                break;

            case "1":
                textView.setText(profession[1]);
                textView1.setText(contact[1]);
                textView2.setText(name[1]);
                break;

            case "2":
                textView.setText(profession[2]);
                textView1.setText(contact[2]);
                textView2.setText(name[2]);
                break;

            case "3":
                textView.setText(profession[3]);
                textView1.setText(contact[3]);
                textView2.setText(name[3]);
                break;

            case "4":
                textView.setText(profession[4]);
                textView1.setText(contact[4]);
                textView2.setText(name[4]);
                break;
        }
    }
}
