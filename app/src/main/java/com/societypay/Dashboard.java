package com.societypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final ImageView imgtransaction=(ImageView) findViewById(R.id.imageView2);
        final TextView txttransaction=(TextView) findViewById(R.id.textView3);
        final ImageView imgreciept=(ImageView) findViewById(R.id.Reciept);
        final TextView Textrciept=(TextView) findViewById(R.id.RecieptA);
        imgtransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int1;
                int1=new Intent(Dashboard.this,Transaction.class);
                startActivity(int1);
                finish();

            }
        });

        txttransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int2;
                int2=new Intent(Dashboard.this,Transaction.class);
                startActivity(int2);
                finish();

            }
        });
        imgreciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int3=new Intent(Dashboard.this,Reciept.class);
                startActivity(int3);
            }
        });
        Textrciept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int4=new Intent(Dashboard.this,Reciept.class);
                startActivity(int4);
            }
        });
    }
}
