package com.example.dilsennubin.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;


import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private DBHandler myDataBase;
    private ListView productsListView;
    private ProductListAdapter adapter;
    private List<Product> myProductsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataBase = new DBHandler(this);
        myDataBase.getWritableDatabase();
        myProductsList = myDataBase.getProductsFromDB();
        adapter = new ProductListAdapter(getApplicationContext(), myProductsList);
        productsListView = (ListView) findViewById(R.id.shopList);
        if (productsListView != null) {
            productsListView.setAdapter(adapter);
        }
        myDataBase.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 &&  resultCode == Activity.RESULT_OK) {
            String newProductName = data.getStringExtra("newTypedProductName");
            String newQuantity = data.getStringExtra("newTypedQuantity");
            String id = UUID.randomUUID().toString();

            Product newProduct = new Product(id, newProductName, newQuantity, false);
            myProductsList.add(newProduct);
            myDataBase.addProductToDB(newProduct);
            adapter.notifyDataSetChanged();
            productsListView.setSelection(adapter.getCount() - 1);
            myDataBase.close();
        }
    }

    public void addButtonClicked(View view) {
        Intent i = new Intent(this, AddNewItem.class);
        startActivityForResult(i, 0);
    }

//    public void deleteProduct(View v) {
//        ImageButton deleteButton = (ImageButton) v.findViewById(R.id.deleteButton);
//        Integer deleteButtonId = deleteButton.getId();
//        Product deletedProduct = myProductsList.get(deleteButtonId);
//        myProductsList.remove(deletedProduct);
//        adapter.notifyDataSetChanged();
//        myDataBase.removeProductsFromDB(deleteButtonId);
//        myDataBase.close();
//
//    }


}
