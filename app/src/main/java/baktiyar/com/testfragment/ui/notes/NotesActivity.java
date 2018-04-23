package baktiyar.com.testfragment.ui.notes;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import baktiyar.com.testfragment.R;
import baktiyar.com.testfragment.model.Note;
import baktiyar.com.testfragment.model.database.DatabaseHelper;
import baktiyar.com.testfragment.ui.create_note.CreateNoteActivity;
import baktiyar.com.testfragment.ui.detailes.DetailedNoteActivity;
import baktiyar.com.testfragment.ui.quiz.QuizActivity;
import baktiyar.com.testfragment.utils.ActionStatus;

public class NotesActivity extends AppCompatActivity implements View.OnClickListener, NotesContract.View, NotesAdapter.OnClickListener {

    public static final String PARCEL_NOTE = "parced note";
    public static final String ACTION_STATUS = "action status";

    private RecyclerView mRvNoteList;
    private FloatingActionButton mFabAddNote;
    private LinearLayout mNoDataLayout, mNoteListLayout;
    private Button mBtnBeginTest;

    private NotesAdapter mNotesAdapter;
    private ArrayList<Note> mNoteList;
    private NotesPresenter mPresenter;
    private DatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
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
        mBtnBeginTest = findViewById(R.id.btnBeginTest);
        mNoDataLayout = findViewById(R.id.noData);
        mNoteListLayout = findViewById(R.id.noteListLayout);
        mFabAddNote.setOnClickListener(this);
        mBtnBeginTest.setOnClickListener(this);
    }

    private void initPresenter() {
        mPresenter = new NotesPresenter(this, mDbHelper);
        updateNotes();
    }

    private void initRecyclerView() {
        mNoteList = new ArrayList<>();
        mRvNoteList.setLayoutManager(new LinearLayoutManager(this));
        mNotesAdapter = new NotesAdapter(mNoteList, this);
        mRvNoteList.setAdapter(mNotesAdapter);

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initToolbar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Todos: " + mNoteList.size());
        actionBar.setIcon(getDrawable(R.drawable.ic_add));

    }
    @Override
    public void onClick(View v) {
        if (v == mFabAddNote) {
            Intent intent = new Intent(this, CreateNoteActivity.class);
            intent.putExtra(ACTION_STATUS, ActionStatus.CREATE);
            startActivity(intent);
        } else if (v == mBtnBeginTest){
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onGetNotesSuccess(ArrayList<Note> notes) {
        mNoteList = notes;
        mNotesAdapter = new NotesAdapter(mNoteList, this);
        mRvNoteList.setAdapter(mNotesAdapter);
        initToolbar();
    }

    @Override
    public void onClick(Note note) {
        Intent intent = new Intent(this, DetailedNoteActivity.class);
        intent.putExtra(PARCEL_NOTE, note);
        startActivity(intent);
    }

    private void updateNotes(){
        mPresenter.getNotes();
        checkList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNotes();
    }

    private void checkList(){
        if (mNoteList.size() == 0){
            mNoDataLayout.setVisibility(View.VISIBLE);
            mNoteListLayout.setVisibility(View.GONE);
        }
        else {
            mNoDataLayout.setVisibility(View.GONE);
            mNoteListLayout.setVisibility(View.VISIBLE);
        }
    }
}
