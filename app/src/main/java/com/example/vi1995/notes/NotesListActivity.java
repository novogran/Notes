package com.example.vi1995.notes;

import android.support.v4.app.Fragment;

public class NotesListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new NotesListFragment();
    }
}
