package com.example.vi1995.notes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;
import java.util.UUID;

public class NotePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Note> mNotes;
    private static final String EXTRA_NOTE_ID =
            "com.example.vi1995.notes.note_id";

    public static Intent newIntent(Context packageContext, UUID noteId) {
        Intent intent = new Intent(packageContext, NotePagerActivity.class);
        intent.putExtra(EXTRA_NOTE_ID, noteId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_pager);

        UUID noteId = (UUID) getIntent().getSerializableExtra(EXTRA_NOTE_ID);
        mViewPager = findViewById(R.id.activity_note_pager_view_pager);

        mNotes = NoteList.get(this).getNotes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter((new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Note note = mNotes.get(position);
                return NoteFragment.newInstance(note.getId());
            }

            @Override
            public int getCount() {
                return mNotes.size();
            }
        }));

        for(int i = 0; i < mNotes.size(); i++){
            if (mNotes.get(i).getId().equals(noteId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
