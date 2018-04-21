package baktiyar.com.testfragment.ui.create_note;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import baktiyar.com.testfragment.R;
import baktiyar.com.testfragment.model.Note;
import baktiyar.com.testfragment.model.database.DatabaseHelper;
import baktiyar.com.testfragment.utils.DatePickerFragment;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout mEtTitle;
    private TextInputLayout mEtDescription;
    private TextView mTvDoTime;
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
        if (isFilled()) {
            note = new Note();
            initNote(note);
            mDbHelper.insertNote(note);
            onBackPressed();
        } else {
            Toast.makeText(this, "Type title", Toast.LENGTH_SHORT).show();
        }
    }

    private void initActivity() {
        mEtTitle = findViewById(R.id.etNoteTitle);
        mEtDescription = findViewById(R.id.etNoteDescription);
        mBtnSaveNote = findViewById(R.id.btnSaveNote);
        mTvDoTime = findViewById(R.id.tvDoTime);
        mBtnSaveNote.setOnClickListener(this);
        mTvDoTime.setOnClickListener(this);

    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.create_note);
    }

    private void initNote(Note note) {
        note.setTitle(mEtTitle.getEditText().getText().toString());
        note.setDescription(mEtDescription.getEditText().getText().toString());
        note.setDoTime(mTvDoTime.getText().toString());
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
        } else if (v == mTvDoTime) {
            showDatePickerDialog();
        }
    }

    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

        String date = newFragment.giveDate();
        mTvDoTime.setText(date);
    }

    public Boolean isFilled() {
        return !Objects.equals(mEtTitle.getEditText().getText().toString(), "");
    }
}
