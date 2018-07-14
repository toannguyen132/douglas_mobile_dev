package com.project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;

/**
 * Created by 300282895 on 7/5/2018.
 */
public class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "groupproject";
    static final int DATABASE_VERSION = 13;

    static DBHelper instance;

//    SQLiteDatabase db;
//
//    public SQLiteDatabase getDb() {
//        return db;
//    }

    public static void initInstance(Context context){
        // get from database
        instance = new DBHelper(context);
    }

    public static SQLiteDatabase getDbInstance(){
        return instance.getWritableDatabase();
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
        seed(db);
    }

    public void createTables(SQLiteDatabase db){
        String[] tables = {
                "PRAGMA foreign_keys = 1",
                TablesDefinitions.USER,
                TablesDefinitions.ROOM,
                TablesDefinitions.ROOM_BOOKING,
                TablesDefinitions.PORT,
                TablesDefinitions.PORT_BOOKING,
                TablesDefinitions.ACTIVITY,
                TablesDefinitions.ACTIVITY_BOOKING
        };

        for (String table : tables){
            db.execSQL(table);
        }
    }

    public void dropTables(SQLiteDatabase db){
        String[] tables = {
                TablesDefinitions.DROP_ROOM_BOOKING,
                TablesDefinitions.DROP_ROOM,
                TablesDefinitions.DROP_PORT,
                TablesDefinitions.DROP_PORT_BOOKING,
                TablesDefinitions.DROP_ACTIVITY_BOOKING,
                TablesDefinitions.DROP_ACTIVITY,
                TablesDefinitions.DROP_USER,
        };

        for (String table : tables){
            db.execSQL(table);
        }
    }

    public void seed(SQLiteDatabase db){

        // add user
        db.execSQL("INSERT INTO " + User.TABLE_NAME + " (name, username, password, phone) VALUES ('Sample User', 'toan', '123456', '123-456-7890')");

        // add rooms
        db.execSQL("INSERT INTO " + Room.TABLE_NAME + " (name, type, price) VALUES " +
                "('OCEAN 01', " + Room.TYPE_OCEAN_VIEW + ", 500), " +
                "('CONCIERGE 01', " + Room.TYPE_CONCIERGE + ", 300), " +
                "('INSIDE 01', " + Room.TYPE_INSIDE + ", 200), " +
                "('VERANDAH 01', " + Room.TYPE_VERANDAH+ ", 100) ");

        // add port
        Port.seed(db);
        PortBooking.seed(db);

        // add activities
        OnboardActivity.seed(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        dropTables(db);
        createTables(db);
        seed(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);

        dropTables(db);
        createTables(db);
        seed(db);
    }
}
