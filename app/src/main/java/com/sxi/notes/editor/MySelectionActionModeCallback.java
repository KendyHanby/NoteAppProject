package com.sxi.notes.editor;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.SpannableString;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import android.view.ActionMode.Callback;

import com.sxi.notes.R;

public class MySelectionActionModeCallback implements Callback {
    private Context context;
    private Activity activity;

    public MySelectionActionModeCallback(Context context) {
        this.context = context;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                activity = (Activity) context;
                // Do something with the activity object
                break;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof Activity) {
            activity.getMenuInflater().inflate(R.menu.selection_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()){

        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
