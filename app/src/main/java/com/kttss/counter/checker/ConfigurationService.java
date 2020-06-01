package com.kttss.counter.checker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.kttss.counter.data.DbHandler;
import com.kttss.counter.util.SqlQuery;

import java.util.List;



public class ConfigurationService {

    private DbHandler db;


    public ConfigurationService(Context context) {
        db=new DbHandler(context);
    }

    public Config load(int id) {
        Config entity=null;
        Cursor cursor=db.read(SqlQuery.LOAD_CONFIG(id));
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false)
        {
            int ID=cursor.getInt(cursor.getColumnIndex(SqlQuery.CONFIGURATION_ID));
            String KEY=cursor.getString(cursor.getColumnIndex(SqlQuery.CONFIGURATION_KEY));
            String VALUE=cursor.getString(cursor.getColumnIndex(SqlQuery.CONFIGURATION_VALUE));
            cursor.moveToNext();
            entity=new Config(ID,KEY,VALUE);
        }
        return entity;
    }

    public Config load(String key) {
        Config entity=null;
        Cursor cursor=db.read(SqlQuery.LOAD_CONFIG(key));
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false)
        {
            int ID=cursor.getInt(cursor.getColumnIndex(SqlQuery.CONFIGURATION_ID));
            String KEY=cursor.getString(cursor.getColumnIndex(SqlQuery.CONFIGURATION_KEY));
            String VALUE=cursor.getString(cursor.getColumnIndex(SqlQuery.CONFIGURATION_VALUE));
            cursor.moveToNext();
            entity=new Config(ID,KEY,VALUE);
        }
        return entity;
    }


    public void create(Config entity) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(SqlQuery.CONFIGURATION_KEY,entity.getKey());
        contentValues.put(SqlQuery.CONFIGURATION_VALUE,entity.getValue());
        Long res=db.insert(SqlQuery.CONFIGURATION_TABLE_NAME,contentValues);
    }


    public void update(Config entity) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(SqlQuery.CONFIGURATION_KEY,entity.getKey());
        contentValues.put(SqlQuery.CONFIGURATION_VALUE,entity.getValue());
        int res=db.update(SqlQuery.CONFIGURATION_TABLE_NAME,contentValues,SqlQuery.CONFIGURATION_ID+"="+entity.getId());
    }


    public void delete(Config entity) {
        int res=db.delete(SqlQuery.CONFIGURATION_TABLE_NAME,SqlQuery.CONFIGURATION_ID+"="+entity.getId());
    }
}

