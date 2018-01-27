package com.example.vi1995.notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

public class NoteFragment extends Fragment {

    private EditText mNoteNameEditText;
    private EditText mNoteTextEditText;
    private Note mNote;

    private static final String ARG_NOTE_ID = "note_id";

    public static NoteFragment newInstance(UUID noteId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE_ID,noteId);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID noteId = (UUID) getArguments().getSerializable(ARG_NOTE_ID);
        mNote = NoteList.get(getActivity()).getNote(noteId);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if((mNote.getName() == null || mNote.getName().equals("")) &&
                (mNote.getText() == null || mNote.getText().equals(""))) {
            NoteList.get(getActivity()).removeNote(mNote);
        } else NoteList.get(getActivity()).updateNote(mNote);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container,
                false);

        mNoteNameEditText = view.findViewById(R.id.note_name);
        mNoteNameEditText.setText(mNote.getName());
        mNoteNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mNote.setName(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNoteTextEditText = view.findViewById(R.id.note_text);
        mNoteTextEditText.setText(mNote.getText());
        mNoteTextEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mNote.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_note, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_note:
                NoteList.get(getActivity()).removeNote(mNote);
                getActivity().finish();
                return true;
                default:
                return super.onOptionsItemSelected(item);
        }
    }
}
