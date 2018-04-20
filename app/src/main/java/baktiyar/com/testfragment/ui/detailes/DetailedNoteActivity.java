package baktiyar.com.testfragment.ui.detailes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import baktiyar.com.testfragment.R;
import baktiyar.com.testfragment.model.Note;
import baktiyar.com.testfragment.model.database.DatabaseHelper;
import baktiyar.com.testfragment.ui.notes.NotesActivity;

public class DetailedNoteActivity extends AppCompatActivity {
    private Note note = new Note();
    private DatabaseHelper mDbHelper;
    private TextView tvTitle;
    private TextView tvDesc;
    private TextView tvDoTime;

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
        tvDoTime = findViewById(R.id.tvDetailedNoteDoTime);
        tvTitle.setText(note.getTitle());
        tvDesc.setText(note.getDescription());
        tvDoTime.setText(note.getDoTime());
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
        if (id == android.R.id.home){
            onBackPressed();
        } else if (id == R.id.menuItemDeleteNote){
            deleteNote();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        mDbHelper.deleteNote(note);
        onBackPressed();
    }
}
