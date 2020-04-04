package com.example.asu.RowDataGateway;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Lesson extends RowDataGatewayBase {
    public int _id;
    public String name;
    public String classroom;
    public String type;
    public int time;
    public int day;

    public Lesson(String name, String classroom, String type, int time, int day) {
        this.name = name;
        this.classroom = classroom;
        this.type = type;
        this.time = time;
        this.day = day;
    }

    public Lesson() {
    }

    @Override
    public void insert(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", this.name);
        contentValues.put("classroom", this.classroom);
        contentValues.put("type", this.type);
        contentValues.put("time", this.time);
        contentValues.put("day", this.day);

        db.insert("Lesson", null, contentValues);
    }

    @Override
    public void update(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", this.name);
        contentValues.put("classroom", this.classroom);
        contentValues.put("type", this.type);
        contentValues.put("time", this.time);
        contentValues.put("day", this.day);

        db.update("Lesson", contentValues, "_id = ?", new String[] {String.valueOf(this._id)});
    }

    @Override
    public void delete(){
        db.delete("Lesson", "_id = ?", new String[]{String.valueOf(this._id)});
    }

    public static List<Lesson> findAll(){
        List<Lesson> lessons = new ArrayList<>();

        Cursor cursor = db.query("Lesson", null, null, null, null, null, null);

        if (cursor.moveToFirst()){
            int iid = cursor.getColumnIndex("_id");
            int iname = cursor.getColumnIndex("name");
            int iclassroom = cursor.getColumnIndex("classroom");
            int itype = cursor.getColumnIndex("type");
            int itime = cursor.getColumnIndex("time");
            int iday = cursor.getColumnIndex("day");

            do {
                Lesson temp = new Lesson(
                        cursor.getString(iname),
                        cursor.getString(iclassroom),
                        cursor.getString(itype),
                        cursor.getInt(itime),
                        cursor.getInt(iday));
                temp._id = cursor.getInt(iid);
                lessons.add(temp);
            }while (cursor.moveToNext());
        }else
            lessons = null;

        cursor.close();
        return lessons;
    }
}
