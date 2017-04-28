package com.example.dilsennubin.shoppinglist;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;


public class ProductListAdapter extends BaseAdapter {

    private Context myContext;
    private List<Product> myProductsList;
    private DBHandler db;

    public ProductListAdapter(Context myContext, List<Product> myProductsList) {
        this.myContext = myContext;
        this.myProductsList = myProductsList;
    }

    @Override
    public int getCount() {
        return myProductsList.size();
    }

    @Override
    public Object getItem(int position) {
        return myProductsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View v = View.inflate(myContext, R.layout.custom_row, null);
        v.setTag(myProductsList.get(position).getId());
        final TextView productName = (TextView) v.findViewById(R.id.productNameTextView);
        productName.setTag(position);
        final TextView quantity = (TextView) v.findViewById(R.id.quantityTextView);
        quantity.setTag(position);
        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        checkBox.setTag(position);
        ImageButton deleteButton = (ImageButton) v.findViewById(R.id.deleteButton);
        deleteButton.setTag(position);


        productName.setText(myProductsList.get(position).getProductName());
        quantity.setText(String.valueOf(myProductsList.get(position).getQuantity()));

        checkBox.setChecked(myProductsList.get(position).isChecked());
        if (checkBox.isChecked()) {
            productName.setTextColor(Color.GRAY);
            quantity.setTextColor(Color.GRAY);
        } else {
            productName.setTextColor(Color.parseColor("#ff009688"));
            quantity.setTextColor(Color.parseColor("#ff009688"));
        }


        deleteButton.setOnClickListener(
            new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer index = (Integer) v.getTag();
                    db = new DBHandler(myContext);
                    db.getWritableDatabase();
                    db.deleteProduct(myProductsList.get(index).getId());
                    db.close();
                    myProductsList.remove(index.intValue());
                    notifyDataSetChanged();
                }
            });


        checkBox.setOnCheckedChangeListener(
            new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    db = new DBHandler(myContext);
                    db.getWritableDatabase();
                    if (checkBox.isChecked()) {
                        Integer index = (Integer) checkBox.getTag();
                        myProductsList.get(index).setChecked(true);
                        productName.setTextColor(Color.GRAY);
                        quantity.setTextColor(Color.GRAY);

                        db.updateDataBase(myProductsList.get(index));
                    }
                    else {
                        Integer index = (Integer) checkBox.getTag();
                        myProductsList.get(index).setChecked(false);
                        productName.setTextColor(Color.parseColor("#ff009688"));
                        quantity.setTextColor(Color.parseColor("#ff009688"));

                        db.updateDataBase(myProductsList.get(index));
                    }
                    db.close();
                }
            });

         return v;
    }


}
