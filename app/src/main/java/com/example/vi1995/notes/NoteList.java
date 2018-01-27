package com.example.vi1995.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vi1995.notes.database.NoteBaseHelper;
import com.example.vi1995.notes.database.NoteCursorWrapper;
import com.example.vi1995.notes.database.NoteDbSchema;
import com.example.vi1995.notes.database.NoteDbSchema.NoteTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NoteList {

    private static NoteList sNoteList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static NoteList get(Context context){
        if(sNoteList == null){
            sNoteList = new NoteList(context);
        }
        return sNoteList;
    }

    private NoteList(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new NoteBaseHelper(mContext).getWritableDatabase();
    }

    public void addNote(Note n){
        ContentValues values = getContentValues(n);

        mDatabase.insert(NoteTable.NAME, null, values);
    }

    public List<Note> getNotes(){
        List<Note> notes = new ArrayList<>();

        NoteCursorWrapper cursor = queryNotes(null, null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return notes;
    }

    public Note getNote(UUID id){
        NoteCursorWrapper cursor = queryNotes(
                NoteTable.Cols.UUID + " = ?",
                new String[]{ id.toString() }
        );

        try {
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return  cursor.getNote();
        } finally {
            cursor.close();
        }
    }

    public void updateNote(Note note){
        String uuidString = note.getId().toString();
        ContentValues values = getContentValues(note);

        mDatabase.update(NoteTable.NAME, values,
                NoteTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private static ContentValues getContentValues(Note note){
        ContentValues value = new ContentValues();
        value.put(NoteTable.Cols.UUID, note.getId().toString());
        value.put(NoteTable.Cols.NAME, note.getName());
        value.put(NoteTable.Cols.TEXT, note.getText());
        return value;
    }

    private NoteCursorWrapper queryNotes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                NoteTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new NoteCursorWrapper(cursor);
    }

    public void removeNote(Note n){
        String uuidString = n.getId().toString();
        mDatabase.delete(NoteTable.NAME,
                NoteTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }
}
