package com.example.dark.gametaixiuv2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.dark.gametaixiuv2.DTO.Player;

/**
 * Created by Dark on 5/24/2017.
 */

public class PlayerDAO {
    SQLiteDatabase db;
    MyDatabase myDataBase;
    Context context;

    public PlayerDAO(Context context) {
        this.context = context;
        myDataBase=new MyDatabase(context);
    }

    public boolean checkUsername(String username){
        db=myDataBase.getReadableDatabase();
        try {
            String sql="SELECT * FROM "+myDataBase.getTablePlayer();
            Cursor cursor=db.rawQuery(sql,null);
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
                if(username.equals(cursor.getString(1))){
                    return true;
                }
            }
        }catch (SQLiteException ex){

        }
        return false;
    }

    public boolean checkLogin(String username,String password){
        db=myDataBase.getReadableDatabase();
        try {
            String sql="SELECT * FROM "+myDataBase.getTablePlayer();
            Cursor cursor=db.rawQuery(sql,null);
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
                if(username.equals(cursor.getString(1))){
                    if (password.equals(cursor.getString(2))){
                        return true;
                    }
                }
            }
        }catch (SQLiteException ex){

        }
        return false;
    }

    public void addPlayer(Player player){
        db=myDataBase.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values=new ContentValues();
            values.put(MyDatabase.getPlayerUser(),player.getUsername());
            values.put(MyDatabase.getPlayerPass(),player.getPassword());
            values.put(MyDatabase.getPlayerMoney(),10000);
            db.insert(MyDatabase.getTablePlayer(),null,values);
            db.setTransactionSuccessful();
        }catch (SQLiteException ex){

        }finally {
            db.endTransaction();
        }
    }

    public void updateMoney(String name, int money){
        db=myDataBase.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values=new ContentValues();
            values.put(MyDatabase.getPlayerMoney(),money);
            db.update(MyDatabase.getTablePlayer(),values,MyDatabase.getPlayerUser()+"=?",new String[]{name});
            db.setTransactionSuccessful();

        }catch (SQLiteException ex){

        }finally {
            db.endTransaction();
        }
    }

    public int getMoney(String name){
        int money=0;
        db=myDataBase.getReadableDatabase();
        try {
            String sql="SELECT * FROM "+myDataBase.getTablePlayer()+" WHERE "+myDataBase.getPlayerUser()+" = '"+name+"'";
            Cursor cursor=db.rawQuery(sql,null);
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()){
                money=cursor.getInt(3);
            }
        }catch (SQLiteException ex){

        }

        return money;
    }

}
