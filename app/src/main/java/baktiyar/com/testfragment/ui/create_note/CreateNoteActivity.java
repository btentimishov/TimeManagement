package baktiyar.com.testfragment.ui.create_note;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import baktiyar.com.testfragment.R;
import baktiyar.com.testfragment.model.Note;
import baktiyar.com.testfragment.model.database.DatabaseHelper;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout mEtTitle;
    private TextInputLayout mEtDescription;
    private Button mBtnSaveNote;
    private Note note;

    private DatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        mDbHelper = new DatabaseHelper(this);


        init();
    }


    private void init() {
        initActivity();
        initToolbar();
    }

    private void saveData() {
        note = new Note();
        initNote(note);
        mDbHelper.insertNote(note);
    }

    private void initActivity() {
        mEtTitle = findViewById(R.id.etNoteTitle);
        mEtDescription = findViewById(R.id.etNoteDescription);
        mBtnSaveNote = findViewById(R.id.btnSaveNote);
        mBtnSaveNote.setOnClickListener(this);
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.create_note);
    }

    private void initNote(Note note) {
        note.setTitle(mEtTitle.getEditText().getText().toString());
        note.setDescription(mEtDescription.getEditText().getText().toString());
        note.setDoTime("vfdvdfvdfv");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnSaveNote) {
            saveData();
            onBackPressed();
        }
    }
}
