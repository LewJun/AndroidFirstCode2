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
import io.objectbox.query.PropertyQuery;
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

        // 重用query 和参数
        // 查询对象允许您以有效的方式多次执行查询。为了使查询更加可重用，
        // 您以前在QueryBuilder中设置的所有条件值都可以更改。这就是我们称之为查询参数的原因。

        // 这里我们使用了同一个查询对象来查找两组用户，每个用户的名字都不同。
        // 请注意， 构建查询时，我们仍然需要初始化firstName属性的值 。因为我们使用setParameter ()覆盖值。我们可以在初始构建查询时传递一个空字符串。
        // 重用query的好处是不用每次通过build来获取，频繁查询会提高性能。
        notesQuery = notesBox.query().equal(Note_.text, "").build();
        List<Note> joesList = notesQuery.setParameter(Note_.text, "joes").find();
        Log.d(TAG, "joesList: " + joesList);

        List<Note> jackList = notesQuery.setParameter(Note_.text, "jack").find();
        Log.d(TAG, "jackList: " + jackList);


        // 得到属性id的聚合值
        notesQuery = notesBox.query().build();
        PropertyQuery propertyQuery_id = notesQuery.property(Note_.id);
        // max
        // 得到最大的id
        long maxId = propertyQuery_id.max();
        Log.d(TAG, "max id: " + maxId);

        // max
        // 得到最小的id
        long minId = propertyQuery_id.min();
        Log.d(TAG, "min id:" + minId);

        // avg
        double avgId = propertyQuery_id.avg();
        Log.d(TAG, "avg id:" + avgId);
    }
}