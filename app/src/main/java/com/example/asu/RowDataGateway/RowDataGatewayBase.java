package com.example.asu.RowDataGateway;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

public abstract class RowDataGatewayBase {
    protected static SQLiteDatabase db;

    public static void setDb(SQLiteDatabase db) {
        RowDataGatewayBase.db = db;
    }

    public abstract void insert();
    public abstract void update();
    public abstract void delete();

    @Override
    public String toString() {
        Class c = this.getClass();
        Field[] fs = c.getDeclaredFields();
        String res = "";
        for (Field f: fs) {
            if (f.getType().getSimpleName()=="int"||f.getType().getName()=="java.lang.String") {
                try {
                    res = res + f.getName().toString() + ": " + f.get(this) + "; ";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return res;
    }
}
