package com.example.vi1995.notes.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.vi1995.notes.Note;
import com.example.vi1995.notes.database.NoteDbSchema.NoteTable;

import java.util.UUID;

public class NoteCursorWrapper extends CursorWrapper {

    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){

        String uuidString = getString(getColumnIndex(NoteTable.Cols.UUID));
        String name = getString(getColumnIndex(NoteTable.Cols.NAME));
        String text = getString(getColumnIndex(NoteTable.Cols.TEXT));

        Note note = new Note(UUID.fromString(uuidString));
        note.setName(name);
        note.setText(text);

        return note;
    }
}
