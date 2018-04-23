package baktiyar.com.testfragment.ui.notes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import baktiyar.com.testfragment.R;
import baktiyar.com.testfragment.model.Note;

/**
 * Created by admin on 20.04.2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteHolder> {
    private ArrayList<Note> mNoteList;
    private OnClickListener mListener;

    interface OnClickListener {
        void onClick(Note note);
    }

    public NotesAdapter(ArrayList<Note> notes, OnClickListener listener) {
        this.mNoteList = notes;
        this.mListener = listener;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        NoteHolder holder = new NoteHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        holder.bindNote(mNoteList.get(position));
    }

    void setList(ArrayList<Note> notes) {
        this.mNoteList = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView tvNoteDoDate;
        TextView tvNoteDoTime;
        TextView tvNoteTitle;

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(note);
                }

            });
            if (Objects.equals(note.getDoDate(), "") & Objects.equals(note.getDoTime(), "")) {
                clockLayout.setVisibility(View.GONE);
            } else {
                tvNoteDoDate.setText(note.getDoDate());
                tvNoteDoTime.setText(note.getDoTime());
            }
        }
    }
}
