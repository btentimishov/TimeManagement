package baktiyar.com.testfragment.ui.notes;

import java.util.ArrayList;
import java.util.List;

import baktiyar.com.testfragment.model.Note;

/**
 * Created by admin on 20.04.2018.
 */

public class NotesContract {
    interface View {
        void onGetNotesSuccess(ArrayList<Note> notes);
    }

    interface Presenter{
        void getNotes();
    }
}
