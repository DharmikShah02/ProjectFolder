package com.societypay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RecieptFP extends AppCompatActivity {
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebasefirestore;
    private FirestoreRecyclerAdapter adapter;
    Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept_fp);
        Back = findViewById(R.id.btndashboard);

        firebasefirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestore_list);

        //query
        Query query = firebasefirestore.collection("UserDataR").orderBy("razorPayId").limitToLast(1);
        //RecyclerOptions
        FirestoreRecyclerOptions<RecieptModel> options = new FirestoreRecyclerOptions.Builder<RecieptModel>()
                .setQuery(query, RecieptModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<RecieptModel, RecieptViewHolder>(options) {
            @NonNull
            @Override
            public RecieptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listsingleiteam, parent, false);
                return new RecieptViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(RecieptViewHolder recieptViewHolder, int i, RecieptModel recieptModel) {
                recieptViewHolder.list_name.setText(recieptModel.getName());
                recieptViewHolder.list_amount.setText(recieptModel.getAmount());
                recieptViewHolder.list_contact.setText(recieptModel.getMobileNo());
                recieptViewHolder.list_Flatno.setText(recieptModel.getFlat_no());
            }
        };
        //viewholder
        mFirestoreList.setHasFixedSize(false);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(RecieptFP.this, Dashboard.class);
                startActivity(i1);

            }
        });
    }


    private class RecieptViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name;
        private TextView list_amount;
        private TextView list_contact;
        private TextView list_Flatno;

        public RecieptViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_amount = itemView.findViewById(R.id.list_amount);
            list_contact = itemView.findViewById(R.id.list_contact);
            list_Flatno = itemView.findViewById(R.id.list_Flatno);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
