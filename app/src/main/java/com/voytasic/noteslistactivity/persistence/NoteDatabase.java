package com.voytasic.noteslistactivity.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.voytasic.noteslistactivity.models.Note;


@Database(entities = {Note.class}, version = 1, exportSchema = true)
public abstract class NoteDatabase extends RoomDatabase {

    public static final String DATABASE_NAME="notes_db";
    private static NoteDatabase INSTANCE;

    static NoteDatabase getInstance(final Context context){
        if(INSTANCE==null) {
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        NoteDatabase.class,
                        DATABASE_NAME
                ).build();
            }
        return INSTANCE;
    }
    public abstract NoteDao getNoteDao();
}
