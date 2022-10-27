package baktiyar.com.testfragment.ui.notes

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import baktiyar.com.testfragment.R
import baktiyar.com.testfragment.model.Note
import baktiyar.com.testfragment.model.database.DatabaseHelper
import baktiyar.com.testfragment.ui.create_note.CreateNoteActivity
import baktiyar.com.testfragment.ui.details.DetailedNoteActivity
import baktiyar.com.testfragment.utils.ActionStatus
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesActivity : AppCompatActivity(), NotesContract.View,
    NotesAdapter.OnClickListener {
    private var mRvNoteList: RecyclerView? = null
    private var mNoDataLayout: LinearLayout? = null
    private var mNoteListLayout: LinearLayout? = null
    private var mNotesAdapter: NotesAdapter? = null
    private var mNoteList: ArrayList<Note>? = null
    private var mPresenter: NotesPresenter? = null
    private var mDbHelper: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        mDbHelper = DatabaseHelper(this)
        init()
    }

    private fun init() {
        initActivity()
        initRecyclerView()
        initPresenter()
    }

    private fun initActivity() {
        mRvNoteList = findViewById(R.id.rvNoteList)
        val mFabAddNote: FloatingActionButton = findViewById(R.id.fabAddNote)
        mFabAddNote.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            intent.putExtra(ACTION_STATUS, ActionStatus.CREATE)
            startActivity(intent)
        }
        mNoDataLayout = findViewById(R.id.noData)
        mNoteListLayout = findViewById(R.id.noteListLayout)
    }

    private fun initPresenter() {
        mPresenter = NotesPresenter(this, mDbHelper)
        updateNotes()
    }

    private fun initRecyclerView() {
        mNoteList = ArrayList()
        mRvNoteList!!.layoutManager = LinearLayoutManager(this)
        mNotesAdapter = NotesAdapter(mNoteList, this)
        mRvNoteList!!.adapter = mNotesAdapter
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun initToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.subtitle = "Todos: " + mNoteList!!.size
            actionBar.setIcon(AppCompatResources.getDrawable(applicationContext, R.drawable.ic_add))
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onGetNotesSuccess(notes: ArrayList<Note>?) {
        mNoteList = notes
        mNotesAdapter = NotesAdapter(mNoteList, this)
        mRvNoteList!!.adapter = mNotesAdapter
        initToolbar()
    }

    override fun onNoteClick(note: Note?) {
        val intent = Intent(this, DetailedNoteActivity::class.java)
        intent.putExtra(PARCEL_NOTE, note)
        startActivity(intent)
    }

    private fun updateNotes() {
        mPresenter!!.notes
        checkList()
    }

    override fun onResume() {
        super.onResume()
        updateNotes()
    }

    private fun checkList() {
        if (mNoteList!!.size == 0) {
            mNoDataLayout!!.visibility = View.VISIBLE
            mNoteListLayout!!.visibility = View.GONE
        } else {
            mNoDataLayout!!.visibility = View.GONE
            mNoteListLayout!!.visibility = View.VISIBLE
        }
    }

    companion object {
        const val PARCEL_NOTE = "parced note"
        const val ACTION_STATUS = "action status"
    }
}