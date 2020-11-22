package com.voytasic.noteslistactivity.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.voytasic.noteslistactivity.async.DeleteAsyncTask;
import com.voytasic.noteslistactivity.async.DeleteExecutor;
import com.voytasic.noteslistactivity.async.InsertAsyncTask;
import com.voytasic.noteslistactivity.async.UpdateAsyncTask;
import com.voytasic.noteslistactivity.models.Note;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NoteRepository {
    private NoteDatabase mNoteDatabase;


    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note){
        new InsertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void updateNoteTask(Note note){
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

//    public void deleteNoteTask(Note note){
//        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
//    }
    public void deleteNoteTask(Note...note)  {
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);

//        new DeleteExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                mNoteDatabase.getNoteDao().delete(note);
//            }
//        });

    }

    public LiveData<List<Note>> retrieveNotes(){
        return mNoteDatabase.getNoteDao().getNotes();
    }
}


