package com.societypay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Servicesu extends AppCompatActivity {
    String services[] = {"Doctor","plumber","liftman","cleaningperons","AcCleaner"};
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicesu);
        list= (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,services);
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Servicesu.this, DetailsServicesu.class);
                intent.putExtra("number", "" + position);
                startActivity(intent);

            }
        });
    }
}
