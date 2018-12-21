package util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


    @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table shopping(id integer primary key autoincrement,product_id varchar(50),title varchar(100)," +
                    "price varchar(50),image varchar(10), num integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
