package com.kttss.counter.checker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.ListView;

import com.kttss.counter.data.DbHandler;
import com.kttss.counter.util.Globals;
import com.kttss.counter.util.Logger;
import com.kttss.counter.util.SqlQuery;
import com.kttss.counter.util.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import io.sentry.util.Util;


public class CounterService {
    private DbHandler db;

    public CounterService(Context context) {
        db=new DbHandler(context);
    }

    public List<Counter> loadAll() {
        List<Counter> entity=new ArrayList<>();
        Cursor cursor=db.read(SqlQuery.READ_ALL_COUNTER);
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false)
        {
            int ID=cursor.getInt(cursor.getColumnIndex(SqlQuery.COUNTER_ID));
            String NAME=cursor.getString(cursor.getColumnIndex(SqlQuery.COUNTER_NAME));
            String DATE=cursor.getString(cursor.getColumnIndex(SqlQuery.COUNTER_DATE));
            java.util.Date dt=new java.util.Date();
            try {
                dt= Globals.DATE_FORMAT.parse(DATE);
            }catch (ParseException e){
                Logger.ERROR(e);
            }
            Double LAP=cursor.getDouble(cursor.getColumnIndex(SqlQuery.COUNTER_LAP));
            Double VALUE=cursor.getDouble(cursor.getColumnIndex(SqlQuery.COUNTER_VALUE));
            cursor.moveToNext();
            entity.add(new Counter(ID,NAME,dt,LAP,VALUE));
        }
        return entity;
    }

    public Counter load(int id) {
        Counter entity=null;
        Cursor cursor=db.read(SqlQuery.LOAD_COUNTER(id));
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false)
        {
            int ID=cursor.getInt(cursor.getColumnIndex(SqlQuery.COUNTER_ID));
            String NAME=cursor.getString(cursor.getColumnIndex(SqlQuery.COUNTER_NAME));
            String DATE=cursor.getString(cursor.getColumnIndex(SqlQuery.COUNTER_DATE));
            java.util.Date dt=new java.util.Date();
            try {
                dt= Globals.DATE_FORMAT.parse(DATE);
            }catch (ParseException e){
                Logger.ERROR(e);
            }
            Double LAP=cursor.getDouble(cursor.getColumnIndex(SqlQuery.COUNTER_LAP));
            Double VALUE=cursor.getDouble(cursor.getColumnIndex(SqlQuery.COUNTER_VALUE));
            cursor.moveToNext();
            entity=new Counter(ID,NAME,dt,LAP,VALUE);
        }
        return entity;
    }

    public Counter load(String key) {
        Counter entity=null;
        Cursor cursor=db.read(SqlQuery.LOAD_COUNTER(key));
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false)
        {
            int ID=cursor.getInt(cursor.getColumnIndex(SqlQuery.COUNTER_ID));
            String NAME=cursor.getString(cursor.getColumnIndex(SqlQuery.COUNTER_NAME));
            String DATE=cursor.getString(cursor.getColumnIndex(SqlQuery.COUNTER_DATE));
            java.util.Date dt=new java.util.Date();
            try {
                dt= Globals.DATE_FORMAT.parse(DATE);
            }catch (ParseException e){
                Logger.ERROR(e);
            }
            Double LAP=cursor.getDouble(cursor.getColumnIndex(SqlQuery.COUNTER_LAP));
            Double VALUE=cursor.getDouble(cursor.getColumnIndex(SqlQuery.COUNTER_VALUE));
            cursor.moveToNext();
            entity=new Counter(ID,NAME,dt,LAP,VALUE);
        }
        return entity;
    }

    public Long create(Counter entity) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(SqlQuery.COUNTER_NAME,entity.getName());
        contentValues.put(SqlQuery.COUNTER_DATE,Globals.DATE_FORMAT.format(entity.getDate()));
        contentValues.put(SqlQuery.COUNTER_LAP,entity.getLap());
        contentValues.put(SqlQuery.COUNTER_VALUE,entity.getValue());
        return db.insert(SqlQuery.COUNTER_TABLE_NAME,contentValues);
    }

    public int update(Counter entity) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(SqlQuery.COUNTER_NAME,entity.getName());
        contentValues.put(SqlQuery.COUNTER_DATE,Globals.DATE_FORMAT.format(entity.getDate()));
        contentValues.put(SqlQuery.COUNTER_LAP,entity.getLap());
        contentValues.put(SqlQuery.COUNTER_VALUE,entity.getValue());
        return db.update(SqlQuery.COUNTER_TABLE_NAME,contentValues,SqlQuery.COUNTER_ID+"="+entity.getId());
    }


    public int delete(Counter entity) {
        return db.delete(SqlQuery.COUNTER_TABLE_NAME,SqlQuery.COUNTER_ID+"="+entity.getId());
    }
}

