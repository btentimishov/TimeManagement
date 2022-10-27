package baktiyar.com.testfragment.ui.notes

import baktiyar.com.testfragment.model.Note
import baktiyar.com.testfragment.model.database.DatabaseHelper
import baktiyar.com.testfragment.ui.notes.NotesContract.Presenter

/**
 * Created by admin on 20.04.2018.
 */
class NotesPresenter(var mView: NotesContract.View, var mDbHelper: DatabaseHelper?) : Presenter {
    var mNoteList: ArrayList<Note>? = null
    override val notes: Unit
        get() {
            mNoteList = mDbHelper?.allNotes
            mView.onGetNotesSuccess(mNoteList)
        }
}