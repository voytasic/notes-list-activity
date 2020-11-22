package com.voytasic.noteslistactivity.executor;


import com.voytasic.noteslistactivity.persistence.NoteDao;

public class Executor implements java.util.concurrent.Executor {
    public Executor(NoteDao noteDao) {
    }

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
