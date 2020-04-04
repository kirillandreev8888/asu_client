package com.example.asu.RowDataGateway;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class User extends RowDataGatewayBase {

    public int _id;
    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public void insert(){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", this.username);
        contentValues.put("password", this.password);

        db.insert("User", null, contentValues);
    }

    @Override
    public void update() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", this.username);
        contentValues.put("password", this.password);
        db.update("User", contentValues, "_id = ?", new String[] {String.valueOf(this._id)});
    }

    @Override
    public void delete() {
        db.delete("User", "_id = ?", new String[]{String.valueOf(this._id)});
    }

    public static List<User> findAll(){
        List<User> users = new ArrayList<>();

        Cursor cursor = db.query("User", null, null, null, null, null, null);

        if (cursor.moveToFirst()){
            int iid = cursor.getColumnIndex("_id");
            int iusername = cursor.getColumnIndex("username");
            int ipassword = cursor.getColumnIndex("password");

            do {
                User temp = new User(
                        cursor.getString(iusername),
                        cursor.getString(ipassword)
                );
                temp._id = cursor.getInt(iid);
                users.add(temp);
            }while (cursor.moveToNext());
        }else
            users = null;

        cursor.close();
        return users;
    }
}
