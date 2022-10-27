package baktiyar.com.testfragment.ui.notes

import baktiyar.com.testfragment.model.Note

/**
 * Created by admin on 20.04.2018.
 */
class NotesContract {
    interface View {
        fun onGetNotesSuccess(notes: ArrayList<Note>?)
    }

    internal interface Presenter {
        val notes: Unit
    }
}