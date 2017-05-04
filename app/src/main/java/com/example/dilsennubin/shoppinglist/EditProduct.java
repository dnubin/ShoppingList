package com.example.dilsennubin.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


public class EditProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Intent intent = getIntent();
        String beforeName = intent.getStringExtra("name");
        String beforeQuantity = intent.getStringExtra("quantity");

        EditText editProductNameInput  = (EditText) findViewById(R.id.editProductNameInput);
        EditText editQuantityInput = (EditText) findViewById(R.id.editQuantityInput);

        if (editProductNameInput != null) {
            editProductNameInput.setText(beforeName);
            editProductNameInput.setSelection(editProductNameInput.getText().length());
        }
        if (editQuantityInput != null) {
            editQuantityInput.setText(beforeQuantity);
        }

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

    public void editProduct(View view) {

        DBHandler myDataBase = new DBHandler(this);
        myDataBase.getWritableDatabase();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        EditText editProductNameInput  = (EditText) findViewById(R.id.editProductNameInput);
        EditText editQuantityInput = (EditText) findViewById(R.id.editQuantityInput);

        String newProductName = editProductNameInput.getText().toString();
        String newQuantity = editQuantityInput.getText().toString();

        if (newQuantity.equals("")) {
            newQuantity = "1";
        }

        if (newProductName.equals("")) {
            Toast.makeText(getApplicationContext(), "Please add a product", Toast.LENGTH_LONG).show();
        } else {
            Product newProduct = new Product(id, newProductName, newQuantity, false);
            myDataBase.updateDataBase(newProduct);

            View view1 = this.getCurrentFocus();
            if (view1 != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            }

            finish();
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


}
