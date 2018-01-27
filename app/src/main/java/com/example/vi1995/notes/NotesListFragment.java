package com.example.vi1995.notes;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NotesListFragment extends Fragment {

    private RecyclerView mNotesRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private NoteAdapter mNoteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes_list,
                container, false);

        mNotesRecyclerView = view.findViewById(R.id.notes_recycler_view);
        mNotesRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity()));

        mFloatingActionButton = view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note();
                NoteList.get(getActivity()).addNote(note);
                Intent intent = NotePagerActivity.newIntent(getActivity(),
                        note.getId());
                startActivity(intent);
            }
        });

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI(){
        NoteList noteList = NoteList.get(getActivity());
        List<Note> notes = noteList.getNotes();

        if(mNoteAdapter == null){
            mNoteAdapter = new NoteAdapter(notes);
            mNotesRecyclerView.setAdapter(mNoteAdapter);
        } else {
            mNoteAdapter.setNotes(notes);
            mNoteAdapter.notifyDataSetChanged();
        }
    }

    private class NoteHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {

        private TextView mNameOfNoteTextView;
        private TextView mTextOfNoteTextView;
        private Note mNote;

        public NoteHolder(View itemView) {
            super(itemView);
            mNameOfNoteTextView = itemView.findViewById(
                    R.id.list_item_name_of_note_text_view);
            mTextOfNoteTextView = itemView.findViewById(
                    R.id.list_item_text_of_note_text_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bindNote(Note note) {
            mNote = note;
            if(mNote.getName() == null || mNote.getName().equals("")){
                mNameOfNoteTextView.setText(R.string.none_name);
            } else mNameOfNoteTextView.setText(mNote.getName());

            if(mNote.getText() == null || mNote.getText().equals("")) {
                    mTextOfNoteTextView.setText(R.string.none_text);
                } else mTextOfNoteTextView.setText(mNote.getText());
        }


        @Override
        public void onClick(View v) {
            Intent intent = NotePagerActivity.newIntent(getActivity(),
                    mNote.getId());
            startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {

        private List<Note> mNotes;

        public NoteAdapter(List<Note> notes) {
            mNotes = notes;
        }

        @Override
        public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_note,
                    parent, false);
            return new NoteHolder(view);
        }

        @Override
        public void onBindViewHolder(NoteHolder holder, int position) {
            Note note = mNotes.get(position);
            holder.bindNote(note);
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }

        public void setNotes(List<Note> notes){
            mNotes = notes;
        }
    }
}
