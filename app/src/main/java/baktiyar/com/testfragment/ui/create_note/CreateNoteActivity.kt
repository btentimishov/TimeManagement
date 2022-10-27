package baktiyar.com.testfragment.ui.create_note

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import baktiyar.com.testfragment.R
import baktiyar.com.testfragment.model.Note
import baktiyar.com.testfragment.model.database.DatabaseHelper
import baktiyar.com.testfragment.ui.notes.NotesActivity
import baktiyar.com.testfragment.utils.ActionStatus
import baktiyar.com.testfragment.utils.DatePickerFragment
import baktiyar.com.testfragment.utils.TimePickerFragment
import baktiyar.com.testfragment.utils.Utils.stringIsNull
import com.google.android.material.textfield.TextInputLayout

class CreateNoteActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mEtTitle: TextInputLayout
    private lateinit var mEtDescription: TextInputLayout
    private lateinit var mTvDoDate: TextView
    private lateinit var mTvDoTime: TextView
    private lateinit var mBtnSaveNote: Button
    private var mNote: Note? = Note()
    private var status: ActionStatus? = ActionStatus.CREATE
    private var mDbHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)
        mDbHelper = DatabaseHelper(this)
        init()
    }

    fun initGetIntent() {
        val intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            if (bundle.containsKey(NotesActivity.Companion.ACTION_STATUS)) {
                status = bundle[NotesActivity.Companion.ACTION_STATUS] as ActionStatus?
            }
            if (bundle.containsKey(NotesActivity.Companion.PARCEL_NOTE)) {
                mNote = bundle.getParcelable(NotesActivity.Companion.PARCEL_NOTE)
            }
        }
    }

    private fun init() {
        initGetIntent()
        initToolbar()
        initActivity()
    }

    private fun saveData() {
        if (isFilled) {
            initNote(mNote)
            if (status === ActionStatus.CREATE) {
                mDbHelper!!.insertNote(mNote)
            } else if (status === ActionStatus.UPDATE) {
                mDbHelper!!.updateNote(mNote)
            }
            onBackPressed()
        } else {
            Toast.makeText(this, "Type title", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initActivity() {
        mEtTitle = findViewById(R.id.etNoteTitle)
        mEtDescription = findViewById(R.id.etNoteDescription)
        mTvDoDate = findViewById(R.id.tvDoDate)
        mTvDoTime = findViewById(R.id.tvDoTime)
        mBtnSaveNote = findViewById(R.id.btnSaveNote)
        mTvDoTime.setOnClickListener(this)
        mBtnSaveNote.setOnClickListener(this)
        mTvDoDate.setOnClickListener(this)
        if (status === ActionStatus.UPDATE) {
            supportActionBar!!.setTitle(R.string.update_note)
            mTvDoTime.setText(mNote?.doTime)
            mTvDoDate.setText(mNote?.doDate)
            mEtTitle.getEditText()?.setText(mNote?.title)
            mEtDescription.getEditText()?.setText(mNote?.description)
        }
    }

    private fun initToolbar() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(R.string.create_note)
    }

    private fun initNote(note: Note?) {
        if (!stringIsNull(mEtTitle.editText!!.text.toString())) {
            note?.title = mEtTitle.editText!!.text.toString()
        }
        if (!stringIsNull(mEtDescription.editText!!.text.toString())) {
            note?.description = mEtDescription.editText!!.text.toString()
        }
        if (!stringIsNull(mTvDoDate.text.toString())) {
            note?.doDate = mTvDoDate.text.toString()
        }
        if (!stringIsNull(mTvDoTime.text.toString())) {
            note?.doTime = mTvDoTime.text.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        if (v === mBtnSaveNote) saveData() else if (v === mTvDoDate) showDatePickerDialog() else if (v === mTvDoTime) showTimePickerDialog()
    }

    fun showDatePickerDialog() {
        val datePickerFragment = DatePickerFragment()
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

    fun showTimePickerDialog() {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "timePicker")
    }

    val isFilled: Boolean
        get() = mEtTitle!!.editText!!.text.toString() != ""
}