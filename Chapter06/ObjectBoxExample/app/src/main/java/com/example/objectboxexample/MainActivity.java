package com.example.objectboxexample;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.model.Note;
import com.example.model.Note_;

import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText mEtId;
    private EditText mEtText;
    private EditText mEtComment;


    @NonNull
    private String getComment() {
        return mEtComment.getText().toString();
    }

    @NonNull
    private String getText() {
        return mEtText.getText().toString();
    }

    private long getId() {
        return Long.valueOf(mEtId.getText().toString());
    }

    private Box<Note> notesBox;
    private Query<Note> notesQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        // 从App中得到BoxStore
        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        // 初始化notesBox
        notesBox = boxStore.boxFor(Note.class);

    }

    private void setupViews() {
        mEtId = findViewById(R.id.et_id);
        mEtText = findViewById(R.id.et_text);
        mEtComment = findViewById(R.id.et_comment);
    }

    public void addNote(View view) {
        Note note = new Note(getId(), getText(), getComment(), new Date());
        notesBox.put(note);
        Log.d(TAG, "Note id: " + note.id);
    }

    public void get(View view) {
        Note note = notesBox.get(getId());
        Log.d(TAG, "get: " + note);
    }

    public void update(View view) {
        Note note = notesBox.get(getId());
        Log.d(TAG, "get: " + note);
        note.text = getText();
        note.comment = getComment();
        notesBox.put(note);
        Log.d(TAG, "update: " + note);
    }

    public void remove(View view) {
        notesBox.remove(getId());
    }

    public void find(View view) {
        // query notes where id in 1,2,3,4,5, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
        notesQuery = notesBox.query().in(Note_.id, new long[]{1, 2, 3, 4, 5}).order(Note_.text).build();
        List<Note> notes = notesQuery.find();
        for (Note note : notes) {
            Log.d(TAG, "find: " + note);
        }
    }
}