package baktiyar.com.testfragment.ui.notes;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import baktiyar.com.testfragment.R;
import baktiyar.com.testfragment.model.Note;
import baktiyar.com.testfragment.model.database.DatabaseHelper;
import baktiyar.com.testfragment.ui.create_note.CreateNoteActivity;
import baktiyar.com.testfragment.ui.detailes.DetailedNoteActivity;

public class NotesActivity extends AppCompatActivity implements View.OnClickListener, NotesContract.View, NotesAdapter.OnClickListener {

    public static final String PARCED_NOTE = "parced note";
    private RecyclerView mRvNoteList;
    private NotesAdapter mNotesAdapter;
    private FloatingActionButton mFabAddNote;
    private ArrayList<Note> mNoteList;
    private NotesPresenter mPresenter;
    private DatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new DatabaseHelper(this);
        init();
    }

    private void init() {

        initActivity();
        initRecyclerView();
        initPresenter();
    }

    private void initActivity() {
        mRvNoteList = findViewById(R.id.rvNoteList);
        mFabAddNote = findViewById(R.id.fabAddNote);
        mFabAddNote.setOnClickListener(this);
    }

    private void initPresenter() {
        mPresenter = new NotesPresenter(this, mDbHelper);
        mPresenter.getNotes();
    }

    private void initRecyclerView() {
        mNoteList = new ArrayList<>();
        mRvNoteList.setLayoutManager(new LinearLayoutManager(this));
        mNotesAdapter = new NotesAdapter(mNoteList, this);
        mRvNoteList.setAdapter(mNotesAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == mFabAddNote) {
            goToActivity(CreateNoteActivity.class);
        }
    }


    private void goToActivity(Class o) {
        Intent intent = new Intent(this, o);
        startActivity(intent);
    }

    @Override
    public void onGetNotesSuccess(ArrayList<Note> notes) {
        mNoteList = notes;
        mNotesAdapter.setList(mNoteList);
        getSupportActionBar().setSubtitle("Todos: " + mNoteList.size());
    }

    @Override
    public void onClick(Note note) {
        Intent intent = new Intent(this, DetailedNoteActivity.class);
        intent.putExtra(PARCED_NOTE, note);
        startActivity(intent);
    }

    void testInitListLaterBeDeleted(ArrayList<Note> notes) {
        notes.add(new Note(1, "Hello", "", ""));
        notes.add(new Note(2, "Hi", "", ""));
        notes.add(new Note(3, "Hey", "", ""));
        notes.add(new Note(4, "vndfjkvnk", "", ""));
        notes.add(new Note(5, "bdfnjbdf", "", ""));
    }
    private void updateNotes(){
        mPresenter.getNotes();

    }
    @Override
    protected void onResume() {
        super.onResume();
        updateNotes();
        Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();

    }
}
