# ObjectBox 使用
[TOC]

## Gradle setup
Add this to your root build.gradle (project level):
```groovy
buildscript {
    ext.objectboxVersion = '1.4.1'
    repositories {
        maven { url "http://objectbox.net/beta-repo/" }
    }
    dependencies {
        classpath "io.objectbox:objectbox-gradle-plugin:$objectboxVersion"
    }
    
}
    
allprojects {
    repositories {
        maven { url "http://objectbox.net/beta-repo/" }
    }
}
```
And this to our app's build.gradle (module level):
```groovy
apply plugin: 'io.objectbox' // after applying Android plugin
```

## 添加model模块，并添加model和关联model模块到app
```java
@Entity
public class Note {
    @Id
    public long id;
    public String text;
    public String comment;
    public Date date;

    public Note() {
    }

    public Note(long id, String text, String comment, Date date) {
        
    }
}
```

## build
build项目

## 初始化BoxStore
初始化之前一定要Build项目，否则找不到MyObjectBox
在App中初始化BoxStore，并在AndroidManifest.xml中配置App
```java
public class App extends Application {
    private static final String TAG = "App";
    private BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        // do this once, for example in your Application class
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(mBoxStore).start(this);
        }

        Log.d(TAG, "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }
}

```

## 简单增删改查布局
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <EditText
            android:id="@+id/et_id"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:hint="id"
            android:inputType="number"
            android:text="0" />

        <EditText
            android:id="@+id/et_text"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:hint="text" />

        <EditText
            android:id="@+id/et_comment"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:hint="comment" />
    </LinearLayout>

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="addNote"
        android:text="@string/add" />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="get"
        android:text="get" />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="update"
        android:text="update" />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="remove"
        android:text="remove" />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="find"
        android:text="find" />
</LinearLayout>
```


## 简单增删改查实现
```java

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
```
