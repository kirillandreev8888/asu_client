package com.example.asu.RowDataGateway;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Setting extends RowDataGatewayBase {

    public int _id;
    public String name;
    public String value;

    public Setting() {
    }

    public Setting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void insert() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", this.name);
        contentValues.put("value", this.value);

        db.insert("Setting", null, contentValues);
    }

    @Override
    public void update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", this.name);
        contentValues.put("value", this.value);
        db.update("Setting", contentValues, "_id = ?", new String[]{String.valueOf(this._id)});
    }

    @Override
    public void delete() {
        db.delete("Setting", "_id = ?", new String[]{String.valueOf(this._id)});
    }

    public static void deleteAll() {
        db.delete("Setting", null, null);
    }

    public static Setting findOne(String name) {

        Cursor cursor = db.query("Setting", null, "name = ?", new String[]{name}, null, null, null);
        Setting temp = new Setting();
        if (cursor.moveToFirst()) {
            int iid = cursor.getColumnIndex("_id");
            int iname = cursor.getColumnIndex("name");
            int ivalue = cursor.getColumnIndex("value");
            do {
                temp._id = cursor.getInt(iid);
                temp.name = cursor.getString(iname);
                temp.value = cursor.getString(ivalue);
            } while (cursor.moveToNext());
        } else
            temp = null;

        cursor.close();
        return temp;
    }
}
