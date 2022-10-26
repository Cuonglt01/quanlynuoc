package ql.bn.qlbannuoc.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ql.bn.qlbannuoc.Model.Waterbottle;

public class DatabaseWaterbottleHelper extends SQLiteOpenHelper {

    public DatabaseWaterbottleHelper(@Nullable Context context) {
        super(context, "qlbannuoc1.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE waterbottle(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, type TEXT, brand TEXT, date TEXT, image BLOB, price TEXT, id_user INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS waterbottle");
        onCreate(sqLiteDatabase);
    }

    //insert data into database
    public boolean insertData(Waterbottle waterbottle){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", waterbottle.getName());
        contentValues.put("address", waterbottle.getAddress());
        contentValues.put("type", waterbottle.getType());
        contentValues.put("brand", waterbottle.getBrand());
        contentValues.put("date", waterbottle.getDate());
        contentValues.put("image", waterbottle.getImage());
        contentValues.put("price", waterbottle.getPrice());
        contentValues.put("id_user", waterbottle.getId_user());
        sqLiteDatabase.insert("waterbottle", null, contentValues);
        return true;
    }

    //get data from database by id_user
    public List<Waterbottle> getAllData(int id_user){
        List<Waterbottle> waterbottleList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM waterbottle WHERE id_user = '"+id_user+"'", null);
        if(cursor.moveToFirst()){
            do{
                Waterbottle waterbottle = new Waterbottle();
                waterbottle.setId(cursor.getInt(0));
                waterbottle.setName(cursor.getString(1));
                waterbottle.setAddress(cursor.getString(2));
                waterbottle.setType(cursor.getString(3));
                waterbottle.setBrand(cursor.getString(4));
                waterbottle.setDate(cursor.getString(5));
                waterbottle.setImage(cursor.getBlob(6));
                waterbottle.setPrice(cursor.getString(7));
                waterbottle.setId_user(cursor.getInt(8));
                waterbottleList.add(waterbottle);
            }while (cursor.moveToNext());
        }
        return waterbottleList;
    }

    //get data from database by id
    public Waterbottle getDataById(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM waterbottle WHERE id = '"+id+"'", null);
        if(cursor.moveToFirst()){
            Waterbottle waterbottle = new Waterbottle();
            waterbottle.setId(cursor.getInt(0));
            waterbottle.setName(cursor.getString(1));
            waterbottle.setAddress(cursor.getString(2));
            waterbottle.setType(cursor.getString(3));
            waterbottle.setBrand(cursor.getString(4));
            waterbottle.setDate(cursor.getString(5));
            waterbottle.setImage(cursor.getBlob(6));
            waterbottle.setPrice(cursor.getString(7));
            waterbottle.setId_user(cursor.getInt(8));
            return waterbottle;
        }
        return null;
    }

    //update data into database
    public boolean updateData(Waterbottle waterbottle){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", waterbottle.getName());
        contentValues.put("address", waterbottle.getAddress());
        contentValues.put("type", waterbottle.getType());
        contentValues.put("brand", waterbottle.getBrand());
        contentValues.put("date", waterbottle.getDate());
        contentValues.put("image", waterbottle.getImage());
        contentValues.put("price", waterbottle.getPrice());
        contentValues.put("id_user", waterbottle.getId_user());
        sqLiteDatabase.update("waterbottle", contentValues, "id = ?", new String[]{String.valueOf(waterbottle.getId())});
        return true;
    }
    //delete data from database
    public boolean deleteData(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("waterbottle", "id = ?", new String[]{String.valueOf(id)});
        return true;
    }

}
