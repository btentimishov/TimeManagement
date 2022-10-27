package baktiyar.com.testfragment.model.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import baktiyar.com.testfragment.model.Note

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Note.Companion.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + Note.Companion.TABLE_NAME)
        onCreate(db)
    }

    fun insertNote(note: Note?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Note.Companion.COLUMN_TITLE, note?.title)
        values.put(Note.Companion.COLUMN_DESCRIPTION, note?.description)
        values.put(Note.Companion.COLUMN_DO_DATE, note?.doDate)
        values.put(Note.Companion.COLUMN_DO_TIME, note?.doTime)
        db.insert(Note.Companion.TABLE_NAME, null, values)
        db.close()
    }

    val allNotes: ArrayList<Note>
        get() {
            val notes = ArrayList<Note>()
            val selectQuery = "SELECT  * FROM " + Note.Companion.TABLE_NAME
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val note = Note()
                    note.id =
                        cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID))
                    note.title =
                        cursor.getString(cursor.getColumnIndex(Note.COLUMN_TITLE))
                    note.description =
                        cursor.getString(cursor.getColumnIndex(Note.COLUMN_DESCRIPTION))
                    note.doDate =
                        cursor.getString(cursor.getColumnIndex(Note.COLUMN_DO_DATE))
                    note.doTime =
                        cursor.getString(cursor.getColumnIndex(Note.COLUMN_DO_TIME))
                    notes.add(note)
                } while (cursor.moveToNext())
            }
            db.close()
            return notes
        }

    fun deleteNote(note: Note?) {
        val db = this.writableDatabase
        db.delete(
            Note.Companion.TABLE_NAME,
            Note.Companion.COLUMN_ID + " = ?",
            arrayOf(note?.id.toString())
        )
        db.close()
    }

    fun updateNote(note: Note?): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Note.Companion.COLUMN_TITLE, note?.title)
        values.put(Note.Companion.COLUMN_DESCRIPTION, note?.description)
        values.put(Note.Companion.COLUMN_DO_DATE, note?.doDate)
        values.put(Note.Companion.COLUMN_DO_TIME, note?.doTime)
        // updating row
        return db.update(
            Note.Companion.TABLE_NAME,
            values,
            Note.Companion.COLUMN_ID + " = ?",
            arrayOf(note?.id.toString())
        )
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "notes_db"
    }
}