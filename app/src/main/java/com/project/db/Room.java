package com.project.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Room {

    static public String TABLE_NAME = "room";
    static public String COLUMN_ID = "id";
    static public String COLUMN_NAME = "name";
    static public String COLUMN_DECK = "deck";
    static public String COLUMN_MAX_ADULT = "max_adult";
    static public String COLUMN_MAX_CHILDREN = "max_children";
    static public String COLUMN_DESC = "desc";
    static public String COLUMN_TYPE = "type";
    static public String COLUMN_PRICE = "price";

    static public int TYPE_OCEAN_VIEW = 1;
    static public int TYPE_CONCIERGE = 2;
    static public int TYPE_INSIDE = 3;
    static public int TYPE_VERANDAH = 4;

    static String[] types = {"Ocean View", "Concierge", "Inside", "Verandah"};

    public long id;
    public String name;
    public String desc;
    public int deck;
    public int max_adult;
    public int max_children;
    public int type;
    public double price;

    public ContentValues toContentValues() {
        ContentValues data = new ContentValues();
        data.put(COLUMN_ID, this.id);
        data.put(COLUMN_NAME, this.name);
        data.put(COLUMN_TYPE, this.type);
        data.put(COLUMN_PRICE, this.price);
        data.put(COLUMN_DECK, this.deck);
        data.put(COLUMN_DESC, this.desc);
        data.put(COLUMN_MAX_ADULT, this.max_adult);
        data.put(COLUMN_MAX_CHILDREN, this.max_children);
        return data;
    }

    public String roomType(){
        return types[type - 1];
    }

    public static Room convertFromCursor(Cursor cursor) {
        Room room = new Room();

        room.id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
        room.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        room.desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESC));
        room.type = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));
        room.price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));
        room.deck = cursor.getInt(cursor.getColumnIndex(COLUMN_DECK));
        room.max_adult = cursor.getInt(cursor.getColumnIndex(COLUMN_MAX_ADULT));
        room.max_children = cursor.getInt(cursor.getColumnIndex(COLUMN_MAX_CHILDREN));
        return room;
    }

    public static String[] getColumnNames() {
        return new String[] {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_DESC,
            COLUMN_TYPE,
            COLUMN_PRICE,
            COLUMN_DECK,
            COLUMN_MAX_ADULT,
            COLUMN_MAX_CHILDREN
        };
    }

    static public Room findRoom(long id) {
        SQLiteDatabase db = DBHelper.getDbInstance();

        String[] selectedArgs = { String.valueOf(id) };
        Cursor cursor = db.query(TABLE_NAME, getColumnNames(), "id = ?", selectedArgs, null, null, null);
        cursor.moveToFirst();

        return convertFromCursor(cursor);
    }
}
