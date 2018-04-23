package baktiyar.com.testfragment.ui.create_note;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
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
import baktiyar.com.testfragment.ui.notes.NotesActivity;
import baktiyar.com.testfragment.utils.ActionStatus;
import baktiyar.com.testfragment.utils.DatePickerFragment;
import baktiyar.com.testfragment.utils.TimePickerFragment;
import baktiyar.com.testfragment.utils.Utils;

import static baktiyar.com.testfragment.utils.Utils.stringContainsNothing;
import static baktiyar.com.testfragment.utils.Utils.stringIsNull;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout mEtTitle, mEtDescription;
    private TextView mTvDoDate, mTvDoTime;
    private Button mBtnSaveNote;
    private Note mNote = new Note();
    private ActionStatus status = ActionStatus.CREATE;
    private DatabaseHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        mDbHelper = new DatabaseHelper(this);
        init();
    }

    void initGetIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.containsKey(NotesActivity.ACTION_STATUS)) {
                status = (ActionStatus) bundle.get(NotesActivity.ACTION_STATUS);
            }
            if (bundle.containsKey(NotesActivity.PARCEL_NOTE)) {
                mNote = (Note) bundle.getParcelable(NotesActivity.PARCEL_NOTE);
            }
        }
    }

    private void init() {
        initGetIntent();
        initToolbar();
        initActivity();
    }

    private void saveData() {
        if (isFilled()) {
            initNote(mNote);
            if (status == ActionStatus.CREATE) {
                mDbHelper.insertNote(mNote);
            } else if (status == ActionStatus.UPDATE) {
                mDbHelper.updateNote(mNote);
            }
            onBackPressed();
        } else {
            Toast.makeText(this, "Type title", Toast.LENGTH_SHORT).show();
        }
    }


    private void initActivity() {
        mEtTitle = findViewById(R.id.etNoteTitle);
        mEtDescription = findViewById(R.id.etNoteDescription);
        mTvDoDate = findViewById(R.id.tvDoDate);
        mTvDoTime = findViewById(R.id.tvDoTime);
        mBtnSaveNote = findViewById(R.id.btnSaveNote);
        mTvDoTime.setOnClickListener(this);
        mBtnSaveNote.setOnClickListener(this);
        mTvDoDate.setOnClickListener(this);


        if (status == ActionStatus.UPDATE) {
            getSupportActionBar().setTitle(R.string.update_note);
            mTvDoTime.setText(mNote.getDoTime());
            mTvDoDate.setText(mNote.getDoDate());
            mEtTitle.getEditText().setText(mNote.getTitle());
            mEtDescription.getEditText().setText(mNote.getDescription());
        }


    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.create_note);
    }

    private void initNote(Note note) {
        if (!stringIsNull(mEtTitle.getEditText().getText().toString())) {
            note.setTitle(mEtTitle.getEditText().getText().toString());
        }
        if (!stringIsNull(mEtDescription.getEditText().getText().toString())) {
            note.setDescription(mEtDescription.getEditText().getText().toString());
        }
        if (!stringIsNull(mTvDoDate.getText().toString())) {
            note.setDoDate(mTvDoDate.getText().toString());
        }
        if (!stringIsNull(mTvDoTime.getText().toString())) {
            note.setDoTime(mTvDoTime.getText().toString());
        }
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
        if (v == mBtnSaveNote) saveData();
        else if (v == mTvDoDate) showDatePickerDialog();
        else if (v == mTvDoTime) showTimePickerDialog();

    }

    public void showDatePickerDialog() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog() {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public Boolean isFilled() {
        return !Objects.equals(mEtTitle.getEditText().getText().toString(), "");
    }

}
