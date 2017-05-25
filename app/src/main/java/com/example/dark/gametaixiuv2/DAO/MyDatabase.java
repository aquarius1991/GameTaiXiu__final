package com.example.dark.gametaixiuv2.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dark on 5/24/2017.
 */

public class MyDatabase extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "GameXiNgau";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PLAYER="player";
    private static final String PLAYER_ID="id";
    private static final String PLAYER_USER="user";
    private static final String PLAYER_PASS="pass";
    private static final String PLAYER_MONEY="money";

    public static String getTablePlayer() {
        return TABLE_PLAYER;
    }

    public static String getPlayerId() {
        return PLAYER_ID;
    }

    public static String getPlayerUser() {
        return PLAYER_USER;
    }

    public static String getPlayerPass() {
        return PLAYER_PASS;
    }

    public static String getPlayerMoney() {
        return PLAYER_MONEY;
    }

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYER_TABLE = "CREATE TABLE " + TABLE_PLAYER +
                "(" +
                PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PLAYER_USER + " TEXT," +
                PLAYER_PASS + " TEXT,"+
                PLAYER_MONEY + " INTEGER"+
                ")";


        db.execSQL(CREATE_PLAYER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
            onCreate(db);
        }

    }

}
