package com.societypay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Reciept extends AppCompatActivity {
    TextView name,amt,flatno,orderid,contact;
    Button Back;
    FirebaseAuth Fauth;
    FirebaseFirestore Fstore;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept);

        name=findViewById(R.id.txtname);
        amt=findViewById(R.id.txtamt);
        flatno=findViewById(R.id.txtflat);
        orderid=findViewById(R.id.txtoi);
        contact =findViewById(R.id.txtcontact);
        Back=findViewById(R.id.btndashboard);

        /*Intent int1 = getIntent();
        String  TextName= int1.getStringExtra("TextName");
        String  TextAmount=int1.getStringExtra("TextAmount");
        String  TextFlatNo=int1.getStringExtra("TextFlatNo");
        String  TextContact=int1.getStringExtra("TextContact");

        name.setText(TextName);
        amt.setText(TextAmount);
        flatno.setText(TextFlatNo);
        contact.setText(TextContact);*/

        //to retrive data

        Fauth=FirebaseAuth.getInstance();
        Fstore=FirebaseFirestore.getInstance();
        uid=Fauth.getCurrentUser().getUid();
        DocumentReference documentReference=Fstore.collection("UserDataR").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("Name"));
                amt.setText(documentSnapshot.getString("Amount"));
                flatno.setText(documentSnapshot.getString("Flat_no"));
                contact.setText(documentSnapshot.getString("MobileNo"));
            }
        });

       // orderid.setText();
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(Reciept.this,Dashboard.class);
                startActivity(i1);

            }
        });
    }
}
