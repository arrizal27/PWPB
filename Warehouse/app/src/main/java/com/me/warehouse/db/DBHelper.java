package com.me.warehouse.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.me.warehouse.Model.Product;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private	static final int DATABASE_VERSION =	5;
    private	static final String	DATABASE_NAME = "warehoos";
    private	static final String TABLE_PRODUCTS = "products";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "item_name";
    private static final String COLUMN_DESC = "item_desc";
    private static final String COLUMN_QUAN = "item_quantity";
    private static final String KEY_DATE = "datetime";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String	CREATE_Product_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT, " +COLUMN_DESC + " TEXT, " + COLUMN_QUAN + " INTEGER, " + KEY_DATE + " DATE "+")";
        db.execSQL(CREATE_Product_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public ArrayList<Product> listProduct(){
        String sql = "select * from " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Product> storeProduct = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                String quantity = cursor.getString(3);
                String date = cursor.getString(4);
                storeProduct.add(new Product(id, name, desc, quantity,date));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeProduct;
    }

    public void addProduct(Product Product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, Product.getName());
        values.put(COLUMN_DESC, Product.getDesc());
        values.put(COLUMN_QUAN, Product.getQuantity());
        values.put(KEY_DATE, Product.getDate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
    }

    public void updateProduct(Product Product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, Product.getName());
        values.put(COLUMN_DESC, Product.getDesc());
        values.put(COLUMN_QUAN, Product.getQuantity());
        values.put(KEY_DATE, Product.getDate());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PRODUCTS, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(Product.getId())});
    }

    public Product findProduct(String name){
        String query = "Select * FROM "	+ TABLE_PRODUCTS + " WHERE " + COLUMN_NAME + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Product Product = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            int id = Integer.parseInt(cursor.getString(0));
            String ProductName = cursor.getString(1);
            String ProductDesc = cursor.getString(2);
            String ProductQuan = cursor.getString(3);
            String ProductDate = cursor.getString(4);
            Product = new Product(id, ProductName, ProductDesc, ProductQuan,ProductDate);
        }
        cursor.close();
        return Product;
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}
