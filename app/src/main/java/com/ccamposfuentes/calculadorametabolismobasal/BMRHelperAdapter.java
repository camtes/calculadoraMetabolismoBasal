package com.ccamposfuentes.calculadorametabolismobasal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccamposfuentes on 7/11/15.
 */
public class BMRHelperAdapter {
    static BMROpenHelper helper;

    public BMRHelperAdapter(Context context) {
        helper = new BMROpenHelper(context);
    }

    public List<BMR> getHistory() {
        List<BMR> bmr = new ArrayList();

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + BMROpenHelper.BMR_TABLE, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                bmr.add(new BMR(
                        cursor.getString(cursor.getColumnIndex(BMROpenHelper.DATE_COLUMN)),
                        cursor.getDouble(cursor.getColumnIndex(BMROpenHelper.BMR_COLUMN)),
                        cursor.getDouble(cursor.getColumnIndex(BMROpenHelper.CALORIES_TABLE)),
                        cursor.getDouble(cursor.getColumnIndex(BMROpenHelper.LOST_CALORIES_TABLE)),
                        cursor.getDouble(cursor.getColumnIndex(BMROpenHelper.GAIN_CALORIES_TABLE))
                ));
            }
        }

        return bmr;
    }

    public boolean insertBMR(BMR bmr) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentvalues = new ContentValues();
        contentvalues.put(BMROpenHelper.DATE_COLUMN, bmr.getDate());
        contentvalues.put(BMROpenHelper.BMR_TABLE, String.valueOf(bmr.getBMR()));
        contentvalues.put(BMROpenHelper.CALORIES_TABLE, String.valueOf(bmr.getCalories()));
        contentvalues.put(BMROpenHelper.LOST_CALORIES_TABLE, String.valueOf(bmr.getLostCalories()));
        contentvalues.put(BMROpenHelper.GAIN_CALORIES_TABLE, String.valueOf(bmr.getGainCalories()));

        long result = db.insert(BMROpenHelper.BMR_TABLE, null, contentvalues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    class BMROpenHelper extends SQLiteOpenHelper {

        private static final String DB_NAME = "bmr.db";
        private static final int DATABASE_VERSION = 1;

        public static final String BMR_TABLE = "bmr";

        public static final String ID_COLUMN = "_id";
        public static final String DATE_COLUMN = "date";
        public static final String BMR_COLUMN = "bmr";
        public static final String CALORIES_TABLE = "calories";
        public static final String LOST_CALORIES_TABLE = "lostCalories";
        public static final String GAIN_CALORIES_TABLE = "gainCalories";


        public static final String CREATE_TABLE_BMR = "CREATE TABLE "
                + BMR_TABLE + "("+ID_COLUMN+" INTEGER PRIMARY KEY, "
                + DATE_COLUMN+" TEXT, "+BMR_COLUMN+" TEXT,"
                + CALORIES_TABLE +" TEXT, "+ LOST_CALORIES_TABLE +" TEXT, "
                + GAIN_CALORIES_TABLE + " TEXT)";

        public BMROpenHelper(Context context) {
            super(context, DB_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_BMR);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + BMR_TABLE);
            onCreate(db);
        }
    }
}
