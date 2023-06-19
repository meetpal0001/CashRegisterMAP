package com.week1.cashregisterpart1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RestockActivity extends AppCompatActivity {


    private AlertDialog.Builder builder;
    Product selectedProduct;

    String productName;
    int productQuantity;
    double productPrice;

    ProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        ListView listView=findViewById(R.id.stock_listview);

        List<Product> productList = MainActivity.productList;
        Button OK=findViewById(R.id.buttonOk);
        Button back=findViewById(R.id.buttonBack);
        EditText num=findViewById(R.id.num);
        TextView name=findViewById(R.id.ProductName);
        TextView qty=findViewById(R.id.Quantity);
        TextView price=findViewById(R.id.ProductPrice);

        adapter = new ProductAdapter(this, productList);
        listView.setAdapter(adapter);

        builder = new AlertDialog.Builder(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected product from the adapter
                selectedProduct = adapter.getItem(position);

                // Display the product details
                productName = selectedProduct.getName();
                productQuantity = selectedProduct.getQuantity();
                productPrice = selectedProduct.getPrice();
                name.setText(productName);
                qty.setText(String.valueOf(productQuantity));
                price.setText(String.valueOf(productPrice));


            }
        });
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num.getText().toString().equals("") || selectedProduct == null)
                    Toast.makeText(RestockActivity.this, "Please Select Product and Enter Product Number", Toast.LENGTH_SHORT).show();



                else {
                    String userInput=num.getText().toString();
                    //Setting message manually and performing action on button click
                    builder.setMessage("Do you want to confirm this stock?\nYour new Stock will increase by " + userInput)
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    selectedProduct.setQuantity(productQuantity + Integer.parseInt(userInput));
                                    adapter.updateData();
                                    MainActivity.refresh();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button (Do nothing)
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Confirm New Stock");
                    alert.show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}