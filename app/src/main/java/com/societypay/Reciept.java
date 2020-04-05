package com.societypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Reciept extends AppCompatActivity {
    TextView name,amt,flatno,orderid,contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept);

        name=findViewById(R.id.txtname);
        amt=findViewById(R.id.txtamt);
        flatno=findViewById(R.id.txtflan);
        orderid=findViewById(R.id.txtoi);
        contact =findViewById(R.id.txtcontact);

        Intent int1 = getIntent();
        String  TextName= int1.getStringExtra("TextName");
        String  TextAmount=int1.getStringExtra("TextAmount");
        String  TextFlatNo=int1.getStringExtra("TextFlatNo");
        String  TextContact=int1.getStringExtra("TextContact");

        name.setText(TextName);
        amt.setText(TextAmount);
        flatno.setText(TextFlatNo);
        contact.setText(TextContact);
       // orderid.setText();
    }
}
