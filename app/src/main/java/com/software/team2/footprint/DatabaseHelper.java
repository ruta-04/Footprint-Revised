package com.software.team2.footprint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME="registeruser";
    public static final String COL_1="ID";
    public static final String COL_2="f_name";
    public static final String COL_3="l_name";
    public static final String COL_4="email";
    public static final String COL_5="username";
    public static final String COL_6="password";

    public static int user_key_for_transaction;
    public static String user_name;

    public static final String TABLE_WATCHLIST= "watchlist";
    public static final String W_COL_1="w_symbol";
    public static final String W_COL_2="w_name";

    public static final String TABLE_TRANSACTION="record_transaction";
    public static final String T_COL_1="ID";
    public static final String T_COL_2="user_key";
    public static final String T_COL_3="stock_name";
    public static final String T_COL_4="stock_symbol";
    public static final String T_COL_5="price";
    public static final String T_COL_6="total_shares";
    public static final String T_COL_7="total_money";
    public static final String T_COL_8="bought_sold";
    public static final String T_COL_9="date";
    public static final String T_COL_10="each_purchase_price";

    public static final String TABLE_NOTES="record_notes";
    public static final String N_COL_1="ID";
    public static final String N_COL_2="user_key";
    public static final String N_COL_3="title";
    public static final String N_COL_4="content";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, f_name TEXT, l_name TEXT, email TEXT, username TEXT, password TEXT)");

//        transaction databases
        db.execSQL("CREATE TABLE record_transaction (ID INTEGER PRIMARY KEY AUTOINCREMENT,user_key INTEGER,stock_name TEXT ,stock_symbol TEXT,price FLOAT,total_shares INTEGER ,total_money FLOAT,bought_sold TEXT, date TEXT, each_purchase_price FLOAT)");

        // notes database
        db.execSQL("CREATE TABLE record_notes (ID INTEGER PRIMARY KEY AUTOINCREMENT, user_key INTEGER, title TEXT ,content TEXT)");

        db.execSQL("CREATE TABLE watchlist (w_symbol TEXT PRIMARY KEY, w_name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TRANSACTION);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_WATCHLIST);
        onCreate(db);

    }

    public boolean checkWatchlist(String w_symbol) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_WATCHLIST + " where w_symbol = ?",new String[] {w_symbol});
        int count = cursor.getCount();
        Log.i("COUNT_number","count is : "+count);
        if(count>0) {
            return true;
        }
        else {
            //db.close();
            cursor.close();
            return false;
        }

    }

    public long addToWatchlist(String w_symbol, String w_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("w_symbol", w_symbol);
        contentValues.put("w_name", w_name);
        boolean alreadyThere = checkWatchlist(w_symbol);
        if(!alreadyThere) {
            long res = db.insert("watchlist", null, contentValues);
            db.close();
            return res;
        }
        else
        {
            return 0;
        }

    }

    public long record_transaction(int user_key, String stock_name, String stock_symbol,float price,int total_shares, float total_money, String bought_sold, String date,float each_purchase_price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_key",user_key);
        contentValues.put("stock_name", stock_name);
        contentValues.put("stock_symbol", stock_symbol);
        contentValues.put("price", price);
        contentValues.put("total_shares", total_shares);
        contentValues.put("total_money", total_money);
        contentValues.put("bought_sold", bought_sold);
        contentValues.put("date", date);
        contentValues.put("each_purchase_price", each_purchase_price);
        long res = db.insert("record_transaction", null, contentValues);
        db.close();
        return res;

    }

    public Cursor getAllTransactions() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = (Cursor) db.rawQuery("select *  from " + TABLE_TRANSACTION + " where user_key =? ", new String[]{String.valueOf(user_key_for_transaction)});
        return res;
    }

    public long addUser(String f_name, String l_name, String email, String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("f_name", f_name);
        contentValues.put("l_name", l_name);
        contentValues.put("email", email);
        contentValues.put("username", user);
        contentValues.put("password", password);
        long res = db.insert("registeruser", null, contentValues);
        db.close();
        return res;
    }

    public long addNote(int user_key, String title, String content){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_key", user_key);
        contentValues.put("title", title);
        contentValues.put("content", content);
        long res = db.insert("record_notes", null, contentValues);
        db.close();
        return res;
    }

    public boolean checkUser(String username, String password){
        String[] columns = {COL_1};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_5 + "=?" +  " and " + COL_6 +"=?";
        String[] selectionArgs = { username, password};
        //Cursor user_key;
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();


        Log.i("COUNT_number","count is : "+count);
        if(count>0) {

           Cursor user_key = db.rawQuery("select ID from " + TABLE_NAME + " where username = ?", new String[] {username});
           user_key.moveToFirst();
            int key_u=user_key.getColumnIndex("ID");
            user_key_for_transaction= user_key.getInt(key_u);
            Log.i("USER_key_TRANSACTION", "key  "+user_key_for_transaction);
            db.close();
            cursor.close();
            user_key.close();
            return true;

        }
        else {
            db.close();
            cursor.close();
            return false;
        }

    }
    public boolean check_username(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " +  TABLE_NAME+ " where username = ?",new String[] {username});
        int count = cursor.getCount();

        Log.i("COUNT_number","count is : "+count);
        if(count>0 ) {
            return false;
        }
        else {
            //db.close();
            cursor.close();
            return true;
        }

    }
    public boolean check_email( String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " +  TABLE_NAME+ " where email = ?",new String[] {email});
        int count = cur.getCount();
        if( count>0) {
            return false;
        }
        else {
            //db.close();
            cur.close();
            return true;
        }

    }

    public int transaction_user_key()
    {
        Log.i("USER_key_TRANSACTION", "key  "+user_key_for_transaction);
        return user_key_for_transaction;
    }

    public Cursor getUser()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = (Cursor) db.rawQuery("select * from " + TABLE_NAME + " where ID =? ", new String[]{String.valueOf(user_key_for_transaction)});
        return res;
    }





    public Cursor getWatchlistEntries()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_WATCHLIST, null);
        return res;
    }

    public int deleteWatchList(String symbol, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_WATCHLIST, "w_symbol =? AND w_name = ?", new String[] {symbol, name});
    }

    public Cursor getAllnotes()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = (Cursor) db.rawQuery("select *  from "+TABLE_NOTES +" where user_key =? ", new String[] {String.valueOf(user_key_for_transaction)} );
        return res;
    }

    public boolean updateNoteData(String title, String content)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = (Cursor) db.rawQuery("select *  from "+TABLE_NOTES +" where title =? AND user_key = ? ", new String[] {title, String.valueOf(user_key_for_transaction)} );
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_key",user_key_for_transaction);
        contentValues.put("title",title);
        contentValues.put("content",content);
        db.update(TABLE_NOTES,contentValues,"user_key =? AND title =?", new String[] {String.valueOf(user_key_for_transaction),title});
        return true;
    }

    public boolean updatePassword(String email, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = (Cursor) db.rawQuery("select *  from "+TABLE_NAME +" where email =? ", new String[] {email} );
        ContentValues contentValues = new ContentValues();
        if(cursor!=null && cursor.getCount()>0)
        {
            while(cursor.moveToNext())
            {
                contentValues.put("f_name",cursor.getString(1));
                contentValues.put("l_name",cursor.getString(2));
                contentValues.put("email",cursor.getString(3));
                contentValues.put("username",cursor.getString(4));
                contentValues.put("password",password);
                Log.i("first name ",cursor.getString(1));
                Log.i("last name ",cursor.getString(2));
                Log.i("email name ",cursor.getString(3));
                Log.i("username ",cursor.getString(4));
                Log.i("username ",password);

            }
            cursor.close();
        }

        db.update(TABLE_NAME,contentValues,"email=?", new String[] {email});
        return true;
    }

    public int deleteNoteData(String title, String content)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NOTES, "user_key =? AND title = ? AND content =?", new String[] {String.valueOf(user_key_for_transaction),title,content});

    }

    public Cursor get_performance()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = (Cursor) db.rawQuery("select *  from "+TABLE_TRANSACTION +" where user_key =? ", new String[] {String.valueOf(user_key_for_transaction)} );
        return res;

    }

}


















