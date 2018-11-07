package com.vladimirov.etsy.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String name = "myDB";
    private static String nameTable = "myTable";
    private static int version = 1;

    public DatabaseHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + nameTable + " ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " title TEXT, "
                + " price TEXT, "
                + " currency TEXT, "
                + " description TEXT, "
                + " image TEXT " + ") ;" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
