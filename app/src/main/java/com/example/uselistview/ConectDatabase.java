package com.example.uselistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ConectDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MYDATABASE";
    private static final int DATABASE_VERSION = 1;

    public ConectDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public ConectDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE NOTES ( " +
                "ID  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TITLE TEXT," +
                "TIME INTEGER," +
                "VALUE TEXT" +
                ")";
        db.execSQL(query);
        String query1 = "CREATE TABLE MISSIONS ( " +
                "ID  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "VALUE TEXT ," +
                "TIME INTEGER , " +
                "FINISH INTEGER ," +
                "ANIM INTEGER " +
                ")";
        //System.out.println("CALL");
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void dropTable(String nametable) {
        SQLiteDatabase database = this.getWritableDatabase();
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", nametable);
        database.execSQL(drop_students_table);
    }


    public void addNote(Note note) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TITLE", note.title);
        values.put("TIME", note.time);
        values.put("VALUE", note.value);
        database.insert("NOTES", null, values);

        database.close();
    }

    public Note getNote(Note note) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query("NOTES",
                new String[]{"TITLE", "TIME", "VALUE"},
                "ID = ?",
                new String[]{String.valueOf(note.getId())},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Note notee = new Note(note.getId(),
                cursor.getString(0),
                cursor.getLong(1),
                cursor.getString(2));
        return notee;
    }


    public ArrayList<Note> getAllNote() {
        ArrayList<Note> arrayList = new ArrayList<>();
        String query = "SELECT * FROM NOTES ORDER BY TIME ASC";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String title, value;
                long time;
                title = cursor.getString(1);
                time = cursor.getLong(2);
                value = cursor.getString(3);
                Note note = new Note(id, title, time, value);

                arrayList.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return arrayList;
    }


    public int getCountNote() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM NOTES";
        Cursor cursor = database.rawQuery(query, null);
        int ans = cursor.getCount();
        cursor.close();
        database.close();
        return ans;
    }


    public int updateNote(Note Note) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", Note.getTitle());
        values.put("TIME", Note.getTime());
        values.put("VALUE", Note.getValue());

        return database.update("NOTES",
                values, "ID = ?",
                new String[]{String.valueOf(Note.getId())});
    }


    public void delNote(@NotNull Note Note) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("NOTES", "ID = ?",
                new String[]{String.valueOf(Note.getId())});
        database.close();
    }


    public void delAllNote() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("NOTES", null, null);
        database.close();
    }


    public void addMission(Mission mission) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("VALUE", mission.getValue());
        values.put("TIME", mission.getTime());
        values.put("FINISH", mission.isChoose()?1:0);
        values.put("ANIM", mission.isAnim()?1:0);
        database.insert("MISSIONS", null, values);
        database.close();
    }
    public Mission getMission(int id) {
        Mission mission;
        String value;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query("MISSIONS",
                new String[]{"VALUE", "TIME", "FINISH"},
                "ID = ?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        mission = new Mission(id, cursor.getString(1),cursor.getInt(3)== 1, cursor.getInt(2));
        return mission;
    }
    public ArrayList<Mission> getAllMission() {
        ArrayList<Mission> arrayList = new ArrayList<>();
        String query = "SELECT * FROM MISSIONS";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String value = cursor.getString(1);
                long time = cursor.getLong(2);
                int finish = cursor.getInt(3);
                int anim = cursor.getInt(4);
                Mission mission = new Mission(id, value,finish==1, time,anim==1);

                arrayList.add(mission);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return arrayList;
    }
    public int getCountMission() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM MISSIONS";
        Cursor cursor = database.rawQuery(query, null);
        int ans = cursor.getCount();
        cursor.close();
        database.close();
        return ans;
    }
    public int updateMission(Mission mission) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("VALUE", mission.getValue());
        values.put("TIME", mission.getTime());
        values.put("FINISH", mission.isChoose()?1:0);
        values.put("ANIM", mission.isAnim()?1:0);
        return database.update("MISSIONS",
                values, "ID = ?",
                new String[]{String.valueOf(mission.getId())});
    }
    public void delMission(@NotNull Mission mission) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("MISSIONS", "ID = ?",
                new String[]{String.valueOf(mission.getId())});
        database.close();
    }
    public void delAllMission() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("MISSIONS", null, null);
        database.close();
    }


}
