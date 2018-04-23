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

public class DetailedNoteActivity extends AppCompatActivity {
    private Note note = new Note();
    private DatabaseHelper mDbHelper;
    private TextView tvTitle, tvDesc, tvDoDate, tvDoTime;
    private LinearLayout descLayout, clockLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);
        mDbHelper = new DatabaseHelper(this);
        note = getIntent().getParcelableExtra(NotesActivity.PARCED_NOTE);

        init();
    }

    private void init() {
        initToolbar();
        initActivity();
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(note.getTitle());
    }

    private void initActivity() {
        tvTitle = findViewById(R.id.tvDetailedNoteTitle);
        tvDesc = findViewById(R.id.tvDetailedNoteDescription);
        tvDoDate = findViewById(R.id.tvDetailedNoteDoDate);
        tvDoTime = findViewById(R.id.tvDetailedNoteDoTime);

        tvTitle.setText(note.getTitle());
        clockLayout = findViewById(R.id.detailedClockLayout);
        descLayout = findViewById(R.id.detailedDescLayout);
        if (!Objects.equals(note.getDescription(), "")) {
            tvDesc.setText(note.getDescription());
        } else {
            descLayout.setVisibility(View.GONE);
        }
        if (Objects.equals(note.getDoDate(), "") & Objects.equals(note.getDoTime(), "")) {
            clockLayout.setVisibility(View.GONE);
        } else {
            tvDoDate.setText(note.getDoDate());
            tvDoTime.setText(note.getDoTime());
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
        intent.putExtra(NotesActivity.PARCED_NOTE, note);
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
        builder.setMessage("Are you sure to delete \"" + note.getTitle() + "\"?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }

    private void deleteNote() {
        mDbHelper.deleteNote(note);
        onBackPressed();
    }
}
