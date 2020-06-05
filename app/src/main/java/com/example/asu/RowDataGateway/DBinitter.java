package com.example.asu.RowDataGateway;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asu.RowDataGateway.Lesson;
import com.example.asu.RowDataGateway.RowDataGatewayBase;

public class DBinitter extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "asuDB";


    public DBinitter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        RowDataGatewayBase.setDb(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Lesson (" +
                "_id integer primary key," +
                "name text," +
                "classroom text," +
                "type text," +
                "time integer," +
                "day integer" +
                ")"
        );

        db.execSQL("create table User (" +
                "_id integer primary key," +
                "username text," +
                "password text" +
                ")"
        );

        db.execSQL("create table Setting (" +
                "_id integer primary key," +
                "name text unique," +
                "value text" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Lesson");
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Setting");

        onCreate(db);
    }

}
