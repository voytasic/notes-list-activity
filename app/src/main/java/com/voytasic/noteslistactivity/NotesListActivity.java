package com.voytasic.noteslistactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.voytasic.noteslistactivity.adapters.NotesRecyclerAdapter;
import com.voytasic.noteslistactivity.models.Note;
import com.voytasic.noteslistactivity.persistence.NoteRepository;
import com.voytasic.noteslistactivity.utils.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotesListActivity extends AppCompatActivity implements
        NotesRecyclerAdapter.OnNoteListener,
        View.OnClickListener
{
    public static final String TAG = "NotesListActivity";
    // ui components
    private RecyclerView mRecyclerView;
    // vars
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNoteRecyclerAdapter;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notes_list);
        mRecyclerView = findViewById(R.id.recyclerView);
        mNoteRepository = new NoteRepository(this);

        findViewById(R.id.fab).setOnClickListener(this);
        Log.d(TAG, "onCreate: thread:"+Thread.currentThread().getName());
        initActionBar();
        initRecyclerView();
        retrieveNotes();
       // insertFakeNotes();

    }

    private void retrieveNotes(){

        mNoteRepository.retrieveNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if(mNotes.size() > 0){
                    mNotes.clear();
                }
                if(mNotes!=null){
                    mNotes.addAll(notes);
                }
                mNoteRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
    private void insertFakeNotes(){
        for(int i=0; i<100;i++){
            Note note = new Note();
            note.setTitle(" title #"+i);
            note.setContent(" content #"+i);
            note.setTimestamp("Nov 2020");
            //note.setValue(BigDecimal.TEN);
            mNotes.add(note);
        }

    }

    private void initActionBar(){
        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Notes");
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);

        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mNoteRecyclerAdapter = new NotesRecyclerAdapter(mNotes,this);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: "+position);
        Intent intent= new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onNoteClick: ");
        Intent intent= new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void deleteNote(Note note){
        mNotes.remove(note);
        mNoteRecyclerAdapter.notifyDataSetChanged();
        mNoteRepository.deleteNoteTask(note);
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }

    };
}