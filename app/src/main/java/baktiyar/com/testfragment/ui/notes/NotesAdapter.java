package baktiyar.com.testfragment.ui.notes;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import baktiyar.com.testfragment.R;
import baktiyar.com.testfragment.model.Note;

import static baktiyar.com.testfragment.utils.Utils.stringContainsNothing;

/**
 * Created by admin on 20.04.2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    private ArrayList<Note> mNoteList;
    private OnClickListener mListener;

    interface OnClickListener {
        void onNoteClick(Note note);
    }

    public NotesAdapter(ArrayList<Note> notes, OnClickListener listener) {
        this.mNoteList = notes;
        this.mListener = listener;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        holder.bindNote(mNoteList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView tvNoteDoDate, tvNoteDoTime, tvNoteTitle;
        LinearLayout clockLayout;

        NoteHolder(View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteDoDate = itemView.findViewById(R.id.tvNoteDoDate);
            tvNoteDoTime = itemView.findViewById(R.id.tvNoteDoTime);
            clockLayout = itemView.findViewById(R.id.clockLayout);
        }

        void bindNote(final Note note) {
            tvNoteTitle.setText(note.getTitle());
            itemView.setOnClickListener(v -> mListener.onNoteClick(note));
            if (stringContainsNothing(note.getDoDate()) & stringContainsNothing(note.getDoTime())) {
                clockLayout.setVisibility(View.GONE);
            } else {
                tvNoteDoDate.setText(note.getDoDate());
                tvNoteDoTime.setText(note.getDoTime());
            }
        }
    }
}
