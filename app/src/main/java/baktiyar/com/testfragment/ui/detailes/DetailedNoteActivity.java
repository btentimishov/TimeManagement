package baktiyar.com.testfragment.ui.detailes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import baktiyar.com.testfragment.R;
import baktiyar.com.testfragment.model.Note;
import baktiyar.com.testfragment.model.database.DatabaseHelper;
import baktiyar.com.testfragment.ui.create_note.CreateNoteActivity;
import baktiyar.com.testfragment.ui.notes.NotesActivity;
import baktiyar.com.testfragment.utils.ActionStatus;
import baktiyar.com.testfragment.utils.Utils;

import static baktiyar.com.testfragment.utils.Utils.stringContainsNothing;

public class DetailedNoteActivity extends AppCompatActivity {
    private Note mNote = new Note();
    private DatabaseHelper mDbHelper;
    private TextView mTvTitle, mTvDesc, mTvDoDate, mTvDoTime;
    private LinearLayout mDescLayout, mClockLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);
        mDbHelper = new DatabaseHelper(this);
        init();
    }

    private void init() {
        initGetIntent();
        initToolbar();
        initActivity();
    }

    private void initGetIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            if (bundle.containsKey(NotesActivity.PARCEL_NOTE)){
                mNote = getIntent().getParcelableExtra(NotesActivity.PARCEL_NOTE);
            }
        }
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mNote.getTitle());
    }

    private void initActivity() {
        mTvTitle = findViewById(R.id.tvDetailedNoteTitle);
        mTvDesc = findViewById(R.id.tvDetailedNoteDescription);
        mTvDoDate = findViewById(R.id.tvDetailedNoteDoDate);
        mTvDoTime = findViewById(R.id.tvDetailedNoteDoTime);

        mTvTitle.setText(mNote.getTitle());
        mClockLayout = findViewById(R.id.detailedClockLayout);
        mDescLayout = findViewById(R.id.detailedDescLayout);


        if (!stringContainsNothing(mNote.getDescription())) {
            mTvDesc.setText(mNote.getDescription());
        } else {
            mDescLayout.setVisibility(View.GONE);
        }
        if (stringContainsNothing(mNote.getDoDate()) & stringContainsNothing(mNote.getDoTime())) {
            mClockLayout.setVisibility(View.GONE);
        } else {
            mTvDoDate.setText(mNote.getDoDate());
            mTvDoTime.setText(mNote.getDoTime());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.menuItemDeleteNote) {
            showDeleteNoteDialog();
        } else if (id == R.id.menuItemEditNote){
            goToEditView();
        }
        return super.onOptionsItemSelected(item);
    }

    void goToEditView(){
        Intent intent = new Intent(this, CreateNoteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(NotesActivity.PARCEL_NOTE, mNote);
        intent.putExtra(NotesActivity.ACTION_STATUS, ActionStatus.UPDATE);
        startActivity(intent);
        finish();
    }

    private void showDeleteNoteDialog() {
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteNote();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to delete \"" + mNote.getTitle() + "\"?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }

    private void deleteNote() {
        mDbHelper.deleteNote(mNote);
        onBackPressed();
    }
}
