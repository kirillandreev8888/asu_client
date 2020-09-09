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
    public String teacher;

    public Lesson(String name, String classroom, String type, int time, int day, String teacher) {
        this.name = name;
        this.classroom = classroom;
        this.type = type;
        this.time = time;
        this.day = day;
        this.teacher = teacher;
    }

    public Lesson() {
    }

    //requests

    @Override
    public void insert() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", this.name);
        contentValues.put("classroom", this.classroom);
        contentValues.put("type", this.type);
        contentValues.put("time", this.time);
        contentValues.put("day", this.day);
        contentValues.put("teacher", this.teacher);

        db.insert("Lesson", null, contentValues);
    }

    @Override
    public void update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", this.name);
        contentValues.put("classroom", this.classroom);
        contentValues.put("type", this.type);
        contentValues.put("time", this.time);
        contentValues.put("day", this.day);
        contentValues.put("teacher", this.teacher);

        db.update("Lesson", contentValues, "_id = ?", new String[]{String.valueOf(this._id)});
    }

    @Override
    public void delete() {
        db.delete("Lesson", "_id = ?", new String[]{String.valueOf(this._id)});
    }

    public static void deleteAll() {
        db.delete("Lesson", null, null);
    }

    public static List<Lesson> findAll() {
        List<Lesson> lessons = new ArrayList<>();

        Cursor cursor = db.query("Lesson", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int iid = cursor.getColumnIndex("_id");
            int iname = cursor.getColumnIndex("name");
            int iclassroom = cursor.getColumnIndex("classroom");
            int itype = cursor.getColumnIndex("type");
            int itime = cursor.getColumnIndex("time");
            int iday = cursor.getColumnIndex("day");
            int iteacher = cursor.getColumnIndex("teacher");

            do {
                Lesson temp = new Lesson(
                        cursor.getString(iname),
                        cursor.getString(iclassroom),
                        cursor.getString(itype),
                        cursor.getInt(itime),
                        cursor.getInt(iday),
                        cursor.getString(iteacher)
                );
                temp._id = cursor.getInt(iid);
                lessons.add(temp);
            } while (cursor.moveToNext());
        } else
            lessons = null;

        cursor.close();
        return lessons;
    }

    public static List<Lesson> findByDay(int day) {
        List<Lesson> lessons = new ArrayList<>();

        Cursor cursor = db.query("Lesson", null, "day = ?", new String[]{String.valueOf(day)}, null, null, null);

        if (cursor.moveToFirst()) {
            int iid = cursor.getColumnIndex("_id");
            int iname = cursor.getColumnIndex("name");
            int iclassroom = cursor.getColumnIndex("classroom");
            int itype = cursor.getColumnIndex("type");
            int itime = cursor.getColumnIndex("time");
            int iday = cursor.getColumnIndex("day");
            int iteacher = cursor.getColumnIndex("teacher");

            do {
                Lesson temp = new Lesson(
                        cursor.getString(iname),
                        cursor.getString(iclassroom),
                        cursor.getString(itype),
                        cursor.getInt(itime),
                        cursor.getInt(iday),
                        cursor.getString(iteacher)
                );
                temp._id = cursor.getInt(iid);
                lessons.add(temp);
            } while (cursor.moveToNext());
        } else
            lessons = null;

        cursor.close();
        return lessons;
    }
}
