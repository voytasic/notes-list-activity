package com.voytasic.noteslistactivity.async;

import com.voytasic.noteslistactivity.models.Note;
import com.voytasic.noteslistactivity.persistence.NoteDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DeleteExecutor implements Executor {
    private static final String TAG = "DeleteExecutor";

    public DeleteExecutor() {
       Executor executor = Executors.newSingleThreadExecutor();

    }

    @Override
    public void execute(Runnable command) {
       // command.run();
     new Thread(command).start();

    }
}
