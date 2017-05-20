package com.example.rakib.tourmate.WeatherUpdate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masum on 19-May-17.
 */

public class DBHelper extends SQLiteOpenHelper {

    //Database Info

    private static final String DATABASE_NAME = "WeatherDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "weather_data";

    //Row Name

    private static final String ROW_ID = "id";
    private static final String ROW_DATE = "date";
    private static final String ROW_MIN_TEMP = "min_temp";
    private static final String ROW_MAX_TEMP = "max_temp";
    private static final String ROW_HUMIDITY ="humidity";
    private static final String ROW_MAIN_TEXT ="main_text";
    private static final String ROW_DESCRIPTION = "description";
    private static final String ROW_ICON_TEXT = "icon";



    // Database creation.

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Table Creation

        String CREATE_WEATHERDATA_TABLE = "CREATE TABLE " + TABLE_NAME +
                " ( " +
                ROW_ID + " INTEGER PRIMARY KEY, " +
                ROW_DATE+ " TEXT, " +
                ROW_MIN_TEMP + " TEXT, " +
                ROW_MAX_TEMP + " TEXT, " +
                ROW_HUMIDITY + " TEXT, " +
                ROW_MAIN_TEXT + " TEXT, " +
                ROW_DESCRIPTION + " TEXT, " +
                ROW_ICON_TEXT + " TEXT " +
                " ) ";

        System.out.println("Test" +CREATE_WEATHERDATA_TABLE);

        db.execSQL(CREATE_WEATHERDATA_TABLE);
    }

    //Table Alteration.

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    //Data Inesrtion.

    public void insertUserDetail(WeatherData weatherData) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ROW_DATE, weatherData.getDt());
            values.put(ROW_MIN_TEMP, weatherData.getMin());
            values.put(ROW_MAX_TEMP, weatherData.getMax());
            values.put(ROW_HUMIDITY, weatherData.getHumidity());
            values.put(ROW_MAIN_TEXT, weatherData.getMain());
            values.put(ROW_DESCRIPTION, weatherData.getDescription());
            values.put(ROW_ICON_TEXT, weatherData.getIcon());

            db.insertOrThrow(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Test  "+e.toString());
        } finally {
            System.out.println("Test  ");
            db.endTransaction();
        }
    }


    //Get Data From Table.

    public List<WeatherData> getAllUser() {
        java.util.List<WeatherData> usersdetail = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    String date = cursor.getString(cursor.getColumnIndex(ROW_DATE));

                    System.out.println("Test  "+date);
                    String min_temp = cursor.getString(cursor.getColumnIndex(ROW_MIN_TEMP));
                    String max_temp = cursor.getString(cursor.getColumnIndex(ROW_MAX_TEMP));
                    String humidity = cursor.getString(cursor.getColumnIndex(ROW_HUMIDITY));
                    String main = cursor.getString(cursor.getColumnIndex(ROW_MAIN_TEXT));
                    String desc = cursor.getString(cursor.getColumnIndex(ROW_DESCRIPTION));
                    String icon = cursor.getString(cursor.getColumnIndex(ROW_ICON_TEXT));

                    WeatherData userData = new WeatherData(date,min_temp,max_temp,humidity,main,desc,icon);

                    usersdetail.add(userData);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println("Test  "+e.toString());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return usersdetail;

    }
}