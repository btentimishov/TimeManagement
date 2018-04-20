package baktiyar.com.testfragment.ui.notes;

import java.util.ArrayList;
import java.util.List;

import baktiyar.com.testfragment.model.Note;
import baktiyar.com.testfragment.model.database.DatabaseHelper;

/**
 * Created by admin on 20.04.2018.
 */

public class NotesPresenter implements NotesContract.Presenter {
    NotesContract.View mView;
    DatabaseHelper mDbHelper;
    ArrayList<Note> mNoteList;

    public NotesPresenter(NotesContract.View mView, DatabaseHelper mDbHelper) {
        this.mView = mView;
        this.mDbHelper = mDbHelper;
    }

    @Override
    public void getNotes() {
        /*mDbHelper.insertNote(new Note(1, "Haha", "Haha", "Haha"));
        mDbHelper.insertNote(new Note(2, "Hihi", "Hihi", "Hihi"));
        mDbHelper.insertNote(new Note(3, "Hehe", "Hehe", "Hehe"));
        mDbHelper.insertNote(new Note(4, "Huhu", "Huhu", "Huhu"));
        mDbHelper.insertNote(new Note(5, "Hoho", "Hoho", "Hoho"));*/
        mNoteList = mDbHelper.getAllNotes();
        mView.onGetNotesSuccess(mNoteList);
    }
}
