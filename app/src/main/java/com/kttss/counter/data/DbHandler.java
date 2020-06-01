package com.kttss.counter.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kttss.counter.util.SqlQuery;

public class DbHandler extends SQLiteOpenHelper {

    public DbHandler(Context context) {
        super(context,  SqlQuery.DATABASE_NAME, null, SqlQuery.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlQuery.COUNTER_TABLE_CREATE_STATEMENT);
        db.execSQL(SqlQuery.CONFIGURATION_CREATE_STATEMENT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(String table, ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.insert(table, null,contentValues);
    }

    public int delete(String table,String clause){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(table,clause,null);
    }

    public int update(String table, ContentValues contentValues, String clause){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.update(table, contentValues,clause,null);
    }

    public Cursor read(String query){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery(query,null);
        return res;
    }
}
