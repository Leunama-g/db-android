package com.ashenafi.contentprovdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class dbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contact.db";
    public static final String TABLE_NAME = "ex_table_1";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_PHONE = "PHONE";

    Context context;
    public dbHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String SQLCreate = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " TEXT, "  + COL_NAME + " TEXT, " + COL_EMAIL + " TEXT, " + COL_PHONE + " TEXT);";
        db.execSQL(SQLCreate);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String SQLDelete = "DROP TABLE " + TABLE_NAME;
        db.execSQL(SQLDelete);
        onCreate(db);
    }
    public void saveData(String id,String name, String email, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        long result = db.insert(TABLE_NAME,null, values);
        if(result == -1)
            Toast.makeText(context.getApplicationContext(), "Failed to store data.",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context.getApplicationContext(), "Saved successfully.",
                    Toast.LENGTH_SHORT).show();
    }
    public Cursor readData(String id){
        SQLiteDatabase db = getWritableDatabase();
        String query;
        if(id.equals("-1")){
            query = "SELECT * FROM " + TABLE_NAME;
        }
        else{
            query = "SELECT * FROM " + TABLE_NAME + "WHERE " + COL_ID + " = " + id;
        }

        Cursor c = db.rawQuery(query,null);
        return c;
    }

    public void editData(String id,String name, String email, String phone ){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL_NAME + " = "+ name + " , "+ COL_EMAIL +" = "+email+" ,  " + COL_PHONE + " = " + phone + " WHERE " +  COL_ID + " = " + id ;
        db.rawQuery(query, null).close();
    }

    public Cursor searchName(String name){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_NAME + " = " + name;
        String[] cols = new String[]{COL_ID, COL_NAME,COL_EMAIL, COL_PHONE};
        Cursor c = db.query(TABLE_NAME, cols, "NAME = ?", new String[]{name}, null,null,null);
        return c;
    }

    public void delete(String id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + id;
        String[] cols = new String[]{COL_ID, COL_NAME,COL_EMAIL, COL_PHONE};
        db.query(TABLE_NAME, cols, "ID = !", new String[]{id}, null,null,null);
    }


}
