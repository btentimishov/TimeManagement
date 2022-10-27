package baktiyar.com.testfragment.ui.details

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import baktiyar.com.testfragment.R
import baktiyar.com.testfragment.model.Note
import baktiyar.com.testfragment.model.database.DatabaseHelper
import baktiyar.com.testfragment.ui.create_note.CreateNoteActivity
import baktiyar.com.testfragment.ui.notes.NotesActivity
import baktiyar.com.testfragment.utils.ActionStatus
import baktiyar.com.testfragment.utils.Utils.stringContainsNothing

class DetailedNoteActivity : AppCompatActivity() {
    private var mNote: Note? = Note()
    private var mDbHelper: DatabaseHelper? = null
    private lateinit var mTvTitle: TextView
    private lateinit var mTvDesc: TextView
    private lateinit var mTvDoDate: TextView
    private lateinit var mTvDoTime: TextView
    private lateinit var mDescLayout: LinearLayout
    private lateinit var mClockLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_note)
        mDbHelper = DatabaseHelper(this)
        init()
    }

    private fun init() {
        initGetIntent()
        initToolbar()
        initActivity()
    }

    private fun initGetIntent() {
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            if (bundle.containsKey(NotesActivity.Companion.PARCEL_NOTE)) {
                mNote = getIntent().getParcelableExtra(NotesActivity.Companion.PARCEL_NOTE)
            }
        }
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = mNote?.title
    }

    private fun initActivity() {
        mTvTitle = findViewById(R.id.tvDetailedNoteTitle)
        mTvDesc = findViewById(R.id.tvDetailedNoteDescription)
        mTvDoDate = findViewById(R.id.tvDetailedNoteDoDate)
        mTvDoTime = findViewById(R.id.tvDetailedNoteDoTime)
        mTvTitle.text = mNote?.title
        mClockLayout = findViewById(R.id.detailedClockLayout)
        mDescLayout = findViewById(R.id.detailedDescLayout)
        if (!stringContainsNothing(mNote?.description)) {
            mTvDesc.text = mNote?.description
        } else {
            mDescLayout.visibility = View.GONE
        }
        if (stringContainsNothing(mNote?.doDate) and stringContainsNothing(mNote?.doTime)) {
            mClockLayout.visibility = View.GONE
        } else {
            mTvDoDate.text = mNote?.doDate
            mTvDoTime.text = mNote?.doTime
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        } else if (id == R.id.menuItemDeleteNote) {
            showDeleteNoteDialog()
        } else if (id == R.id.menuItemEditNote) {
            goToEditView()
        }
        return super.onOptionsItemSelected(item)
    }

    fun goToEditView() {
        val intent = Intent(this, CreateNoteActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra(NotesActivity.Companion.PARCEL_NOTE, mNote)
        intent.putExtra(NotesActivity.Companion.ACTION_STATUS, ActionStatus.UPDATE)
        startActivity(intent)
        finish()
    }

    private fun showDeleteNoteDialog() {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> deleteNote()
                DialogInterface.BUTTON_NEGATIVE -> dialog.cancel()
            }
        }
        val builder = AlertDialog.Builder(this)

        builder.setMessage("Are you sure to delete \"" + mNote?.title + "\"?")
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener)
            .show()
    }

    private fun deleteNote() {
        mDbHelper!!.deleteNote(mNote)
        onBackPressed()
    }
}