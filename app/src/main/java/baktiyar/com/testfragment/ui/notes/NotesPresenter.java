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
        mNoteList = mDbHelper.getAllNotes();
        mView.onGetNotesSuccess(mNoteList);
    }
}
