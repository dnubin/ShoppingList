package com.example.dilsennubin.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

        System.out.println("dupa ce ref text view max nr lines este: " + productName.getMaxLines());

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

                    SQLiteDatabase myDB = myContext.openOrCreateDatabase("myProducts.db", Context.MODE_PRIVATE, null);
                    String id = myProductsList.get(index).getId();
                    myDB.execSQL("DELETE FROM products WHERE id = \"" + id + "\"");
                    myDB.close();

                    myProductsList.remove(index.intValue());
                    notifyDataSetChanged();
                }
            });


        checkBox.setOnCheckedChangeListener(
            new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (checkBox.isChecked()) {
                        Integer index = (Integer) checkBox.getTag();
                        myProductsList.get(index).setChecked(true);
                        productName.setTextColor(Color.GRAY);
                        quantity.setTextColor(Color.GRAY);

                        updateDataBase(index);
                    }
                    else {
                        Integer index = (Integer) checkBox.getTag();
                        myProductsList.get(index).setChecked(false);
                        productName.setTextColor(Color.parseColor("#ff009688"));
                        quantity.setTextColor(Color.parseColor("#ff009688"));

                        updateDataBase(index);
                    }
                }
            });


        productName.setOnClickListener(new View.OnClickListener() {
            boolean isTextViewClicked = false;
            @Override
            public void onClick(View view) {

                System.out.println("clikced on products: " + productName.getText());
                System.out.println("max number of lines: " + productName.getMaxLines());
                System.out.println("is text view clicked " + isTextViewClicked);

                if(isTextViewClicked){
                    System.out.println("am intrat pe if ");
                    productName.setMaxLines(1);
                    System.out.println("dupa if noul max mr of lines " + productName.getMaxLines());
                    productName.setSingleLine(true);
                    isTextViewClicked = false;
                } else {
                    System.out.println("am intrat pe else ");
                    productName.setMaxLines(3);
                    System.out.println("dupa else noul max mr of lines " + productName.getMaxLines());
                    productName.setSingleLine(false);
                    isTextViewClicked = true;
                }
                notifyDataSetChanged();
            }
        });

         return v;
    }

    public void updateDataBase(Integer index) {
        SQLiteDatabase myDB = myContext.openOrCreateDatabase("myProducts.db", Context.MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        values.put("id", myProductsList.get(index).getId());
        values.put("productName", myProductsList.get(index).getProductName());
        values.put("quantity", myProductsList.get(index).getQuantity());
        values.put("isChecked", myProductsList.get(index).isChecked());

        String where = "id=?";
        String[] whereArgs = new String[] {String.valueOf(myProductsList.get(index).getId())};
        myDB.update("products", values, where, whereArgs);

        myDB.close();
    }
}
