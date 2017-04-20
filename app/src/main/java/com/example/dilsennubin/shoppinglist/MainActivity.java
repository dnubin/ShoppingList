package com.example.dilsennubin.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DBHandler myDataBase;
    private ListView productsListView;
    private ProductListAdapter adapter;
    private List<Product> myProductsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openAndGetDataFromDataBase();
        displayProductsFromDataBase();
        myDataBase.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        openAndGetDataFromDataBase();
        displayProductsFromDataBase();
        myDataBase.close();
    }

    public void addButtonClicked(View view) {
        Intent i = new Intent(this, AddNewItem.class);
        startActivity(i);
    }

    private void openAndGetDataFromDataBase() {
        myDataBase = new DBHandler(this);
        myDataBase.getWritableDatabase();
        myProductsList = myDataBase.getProductsFromDB();
    }

    private void displayProductsFromDataBase() {
        adapter = new ProductListAdapter(getApplicationContext(), myProductsList);
        productsListView = (ListView) findViewById(R.id.shopList);
        if (productsListView != null) {
            productsListView.setAdapter(adapter);
        }
    }


}
