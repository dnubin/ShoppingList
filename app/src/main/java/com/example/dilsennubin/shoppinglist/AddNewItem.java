package com.example.dilsennubin.shoppinglist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import java.util.UUID;

public class AddNewItem extends AppCompatActivity {

    private DBHandler myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addItemToList(View view) {

        myDataBase = new DBHandler(this);
        myDataBase.getWritableDatabase();

        EditText productNameInput  = (EditText) findViewById(R.id.productNameInput);
        EditText quantityInput = (EditText) findViewById(R.id.quantityInput);

        String id = UUID.randomUUID().toString();
        String newProductName = productNameInput.getText().toString();
        String newQuantity = quantityInput.getText().toString();

        if (newQuantity.equals("")) {
            newQuantity = "1";
        }

        if (newProductName.equals("")) {
            Toast.makeText(getApplicationContext(), "Please add a product", Toast.LENGTH_LONG).show();
        } else {
            Product newProduct = new Product(id, newProductName, newQuantity, false);
            myDataBase.addProductToDB(newProduct);

            View view1 = this.getCurrentFocus();
            if (view1 != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            }

            finish();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
