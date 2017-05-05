package com.example.dilsennubin.shoppinglist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DBHandler myDataBase;
    private List<Product> myProductsList;
    ListView productsListView;
    AlertDialog alertDialog;

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
        i.putExtra("selectedOption", "add");
        startActivity(i);
    }

    private void openAndGetDataFromDataBase() {
        myDataBase = new DBHandler(this);
        myDataBase.getWritableDatabase();
        myProductsList = myDataBase.getProductsFromDB();
    }

    private void displayProductsFromDataBase() {
        ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(), myProductsList);
        productsListView = (ListView) findViewById(R.id.shopList);
        if (productsListView != null) {
            productsListView.setAdapter(adapter);
            productsListView.setClickable(true);

            productsListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> a, View v, final int position, final long id) {

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertBuilder.setMessage(R.string.popup_message);

                    alertBuilder.setPositiveButton(R.string.popup_edit_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), AddNewItem.class);
                            i.putExtra("selectedOption", "edit");
                            i.putExtra("id", myProductsList.get(position).getId());
                            i.putExtra("name", myProductsList.get(position).getProductName());
                            i.putExtra("quantity", myProductsList.get(position).getQuantity());
                            startActivity(i);
                        }
                    });

                    alertBuilder.setNegativeButton(R.string.popup_no_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    alertDialog = alertBuilder.create();

                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(final DialogInterface dialog) {
                            customizeDialog(dialog);
                        }
                    });

                    alertDialog.show();

                }
            });
        }
    }

    private void customizeDialog(final DialogInterface dialog) {
        Window view = ((AlertDialog)dialog).getWindow();
        view.setBackgroundDrawableResource(R.color.colorAccent);

        Button positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setTextColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
        positiveButton.setTypeface(Typeface.DEFAULT_BOLD);
        positiveButton.invalidate();

        Button negativeButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setTextColor(MainActivity.this.getResources().getColor(R.color.colorAccent));
        negativeButton.setTypeface(Typeface.DEFAULT_BOLD);
        negativeButton.invalidate();
    }

}


