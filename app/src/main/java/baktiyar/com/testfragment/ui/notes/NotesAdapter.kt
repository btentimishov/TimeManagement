package baktiyar.com.testfragment.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import baktiyar.com.testfragment.R
import baktiyar.com.testfragment.model.Note
import baktiyar.com.testfragment.ui.notes.NotesAdapter.NoteHolder
import baktiyar.com.testfragment.utils.Utils.stringContainsNothing

/**
 * Created by admin on 20.04.2018.
 */
class NotesAdapter(
    private val mNoteList: ArrayList<Note>?,
    private val mListener: OnClickListener
) : RecyclerView.Adapter<NoteHolder>() {
    interface OnClickListener {
        fun onNoteClick(note: Note?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bindNote(mNoteList!![position])
    }

    override fun getItemCount(): Int {
        return mNoteList!!.size
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNoteDoDate: TextView
        var tvNoteDoTime: TextView
        var tvNoteTitle: TextView
        var clockLayout: LinearLayout

        init {
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle)
            tvNoteDoDate = itemView.findViewById(R.id.tvNoteDoDate)
            tvNoteDoTime = itemView.findViewById(R.id.tvNoteDoTime)
            clockLayout = itemView.findViewById(R.id.clockLayout)
        }

        fun bindNote(note: Note?) {
            tvNoteTitle.text = note?.title
            itemView.setOnClickListener { v: View? -> mListener.onNoteClick(note) }
            if (stringContainsNothing(note?.doDate) and stringContainsNothing(note?.doTime)) {
                clockLayout.visibility = View.GONE
            } else {
                tvNoteDoDate.text = note?.doDate
                tvNoteDoTime.text = note?.doTime
            }
        }
    }
}