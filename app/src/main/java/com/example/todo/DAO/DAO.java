package com.example.todo.DAO;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DAO {

    protected SQLiteOpenHelper baseHelper = null;
    protected SQLiteDatabase db =null;

    public DAO(SQLiteOpenHelper baseHelper) {
        this.baseHelper = baseHelper;
    }

    public SQLiteDatabase open() {
        db = baseHelper.getWritableDatabase();
        return db;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
