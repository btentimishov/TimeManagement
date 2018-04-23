package baktiyar.com.testfragment.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import baktiyar.com.testfragment.model.Note;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
        onCreate(db);
    }

    public void insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_TITLE, note.getTitle());
        values.put(Note.COLUMN_DESCRIPTION, note.getDescription());
        values.put(Note.COLUMN_DO_DATE, note.getDoDate());
        values.put(Note.COLUMN_DO_TIME, note.getDoTime());
        db.insert(Note.TABLE_NAME, null, values);
        db.close();
    }


    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TITLE)));
                note.setDescription(cursor.getString(cursor.getColumnIndex(Note.COLUMN_DESCRIPTION)));
                note.setDoDate(cursor.getString(cursor.getColumnIndex(Note.COLUMN_DO_DATE)));
                note.setDoTime(cursor.getString(cursor.getColumnIndex(Note.COLUMN_DO_TIME)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();
        return notes;
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }


    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_TITLE, note.getTitle());
        values.put(Note.COLUMN_DESCRIPTION, note.getDescription());
        values.put(Note.COLUMN_DO_DATE, note.getDoDate());
        values.put(Note.COLUMN_DO_TIME, note.getDoTime());
        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }
}
