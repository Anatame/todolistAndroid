package com.example.yolaman.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.yolaman.model.Item;
import com.example.yolaman.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;

    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME,  null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BABY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_BABY_ITEM + " INTEGER,"
                + Constants.KEY_COLOR + " TEXT,"
                + Constants.KEY_QTY_NUMBER + " INTEGER,"
                + Constants.KEY_ITEM_SIZE + " INTEGER,"
                + Constants.KEY_DATE_NAME + " LONG);";

        db.execSQL(CREATE_BABY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME );

        onCreate(db);
    }

    public void addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.KEY_BABY_ITEM, item.getItemName());
        values.put(Constants.KEY_COLOR, item.getItemColor());
        values.put(Constants.KEY_QTY_NUMBER, item.getItemQuantity());
        values.put(Constants.KEY_ITEM_SIZE, item.getItemSize());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());

        //Insert row
        db.insert(Constants.TABLE_NAME, null, values);
        Log.d("DBHandler", "added item");
    }

    public Item getItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_BABY_ITEM,
                        Constants.KEY_COLOR,
                        Constants.KEY_QTY_NUMBER,
                        Constants.KEY_ITEM_SIZE,
                        Constants.KEY_DATE_NAME},
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        assert cursor != null;

        item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_BABY_ITEM)));
        item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
        item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));
        item.setItemSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));

        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());

        item.setDataItemAdded(formattedDate);

        return item;
    }

    //GET ALL THE ITEMS
    public List<Item> getAllItems(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> itemsList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_BABY_ITEM,
                        Constants.KEY_COLOR,
                        Constants.KEY_QTY_NUMBER,
                        Constants.KEY_ITEM_SIZE,
                        Constants.KEY_DATE_NAME},
                null, null, null, null,
                Constants.KEY_DATE_NAME + " DESC");

        if(cursor.moveToFirst()){
            do {
                Item item = new Item();

                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_BABY_ITEM)));
                item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));
                item.setItemSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)));

                DateFormat dateFormat = DateFormat.getDateTimeInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME))).getTime());

                item.setDataItemAdded(formattedDate);

                itemsList.add(item);

            } while (cursor.moveToNext());
        }

        return itemsList;
    }

    //Todo: Add updateItem
    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_BABY_ITEM, item.getItemName());
        values.put(Constants.KEY_COLOR, item.getItemColor());
        values.put(Constants.KEY_QTY_NUMBER, item.getItemQuantity());
        values.put(Constants.KEY_ITEM_SIZE, item.getItemSize());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());//timestamp of the system

        //update row
        return db.update(Constants.TABLE_NAME, values,
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});

    }

    //Todo: Add Delete Item
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});

        //close
        db.close();

    }

    //Todo: getItemCount
    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }

}
