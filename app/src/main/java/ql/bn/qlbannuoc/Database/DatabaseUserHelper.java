package ql.bn.qlbannuoc.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import ql.bn.qlbannuoc.Model.User;

public class DatabaseUserHelper extends SQLiteOpenHelper {

    public DatabaseUserHelper(@Nullable Context context) {
        super(context, "qlbannuoc.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //tạo bảng user trong database id, username, password
            sqLiteDatabase.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(sqLiteDatabase);
    }


    //insert data into database
    public boolean insertData(String username, String password){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("INSERT INTO user VALUES(null, '"+username+"', '"+password+"')");
        return true;
    }

    //check username
    public User getUser(String username){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        User user = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username = '"+username+"'", null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }
        return user;
    }
}
