package com.week1.cashregisterpart1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView listView=findViewById(R.id.purchased_listview);
        Button back=findViewById(R.id.his_back);

        ArrayList<PurchasedProduct> pur=(ArrayList<PurchasedProduct>)getIntent().getSerializableExtra("pur");

        PurchasedProductAdapter adapter = new PurchasedProductAdapter(this, pur);
        listView.setAdapter(adapter);

        builder = new AlertDialog.Builder(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PurchasedProduct selected = adapter.getItem(position);
                builder.setMessage("Product: " + selected.getName() + "\n Price: " + String.valueOf(selected.getPrice()) + "\n Purchase Date: " + selected.getTimestamp());

                //Creating dialog box
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }
}