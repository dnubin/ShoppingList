package com.example.dilsennubin.shoppinglist;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myProducts.db";
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "productName";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_IS_CHECKED = "isChecked";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " TEXT," +
                COLUMN_PRODUCT_NAME + " TEXT," +
                COLUMN_QUANTITY + " TEXT," +
                COLUMN_IS_CHECKED + " BOOLEAN " + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProductToDB(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, product.getId());
        values.put(COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_IS_CHECKED, product.isChecked());

        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public ArrayList<Product> getProductsFromDB() {
        ArrayList<Product> productsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        try
        {
            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    product.setId(cursor.getString(0));
                    product.setProductName(cursor.getString(1));
                    product.setQuantity(cursor.getString(2));
                    boolean checked;
                    checked = cursor.getString(3).equals("1");
                    product.setChecked(checked);

                    productsList.add(product);
                } while (cursor.moveToNext());
            }
        }
        catch (SQLiteException e)
        {
            Log.d("SQL Error", e.getMessage());
            return null;
        }
        finally
        {
            cursor.close();
            db.close();
        }
        return productsList;
    }

    public int updateDataBase(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, product.getId());
        values.put(COLUMN_PRODUCT_NAME, product.getProductName());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_IS_CHECKED, product.isChecked());

        return db.update("products", values, "id" + " = ?", new String[] { product.getId() });
    }

    public void deleteProduct(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM products where id='"+ id +"'";

        database.execSQL(deleteQuery);
    }


}
