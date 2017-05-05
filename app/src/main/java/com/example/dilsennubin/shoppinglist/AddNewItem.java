package com.example.dilsennubin.shoppinglist;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.UUID;

public class AddNewItem extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String option = intent.getStringExtra("selectedOption");

        switch (option) {
            case "add":
                displayAddOption();
                break;
            case "edit":
                displayEditOption();
                break;
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


    private void displayAddOption() {
        Button button = (Button) findViewById(R.id.AddItemToList);
        button.setText(R.string.add_item_to_list_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler myDataBase = new DBHandler(AddNewItem.this);
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

                    finish();
                }
            }
        });
    }

    private void displayEditOption() {
        Button button = (Button) findViewById(R.id.AddItemToList);
        button.setText(R.string.add_item_to_list_edit_button);

        Intent intent = getIntent();
        String beforeName = intent.getStringExtra("name");
        String beforeQuantity = intent.getStringExtra("quantity");

        EditText editProductNameInput  = (EditText) findViewById(R.id.productNameInput);
        EditText editQuantityInput = (EditText) findViewById(R.id.quantityInput);

        editProductNameInput.setText(beforeName);
        editProductNameInput.setSelection(editProductNameInput.getText().length());
        editQuantityInput.setText(beforeQuantity);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler myDataBase = new DBHandler(AddNewItem.this);
                myDataBase.getWritableDatabase();

                Intent intent = getIntent();
                String id = intent.getStringExtra("id");

                EditText editProductNameInput  = (EditText) findViewById(R.id.productNameInput);
                EditText editQuantityInput = (EditText) findViewById(R.id.quantityInput);

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

                    finish();
                }

            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }


    @Override
    protected void onStop() {
        super.onStop();
        hideKeyboard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyboard();
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
