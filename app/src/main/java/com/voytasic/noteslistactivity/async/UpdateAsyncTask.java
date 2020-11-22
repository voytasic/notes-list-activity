package com.voytasic.noteslistactivity.async;

import android.os.AsyncTask;
import android.util.Log;

import com.voytasic.noteslistactivity.models.Note;
import com.voytasic.noteslistactivity.persistence.NoteDao;

public class UpdateAsyncTask extends AsyncTask<Note,Void,Void> {
    private static final String TAG = "UpdateAsyncTask";
    private NoteDao mNoteDao;
    public UpdateAsyncTask(NoteDao noteDao) {
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: thread:"+Thread.currentThread().getName());
        mNoteDao.update(notes);
        return null;
    }
}
