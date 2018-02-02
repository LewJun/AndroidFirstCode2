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

## 重用query 和参数
```
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
```

## 聚合值
```
   notesQuery = notesBox.query().build();

   // count返回查询行数
   Log.d(TAG, "count: " + notesQuery.count());

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
```

## 一对一
一个订单对应一个客户
在订单中添加客户的引用**ToOne<Customer> customer**

``` java
@Entity
public class Customer {
    @Id
    public long id;

    public String name;
}

@Entity
public class Order {
    @Id
    public long id;

    // 下单时间
    public Date date;

    // 一个订单对应一个客户
    public ToOne<Customer> customer;
}


public void toOne(View view) {
    // 添加一个客户和一些订单。
    Order order = new Order();
    Customer customer = new Customer();
    customer.name = "LewJun";
    // 要设置customer对象，需要在ToOne实例上调用 setTarget()，并保存order对象
    order.customer.setTarget(customer);
    mOrderBox.put(order);

    Log.d(TAG, "toOne: customer: " + customer + ", order: " + order);
}

public void getOrder(View view) {
    // get会查询数据库调用（懒加载）
    Order order = mOrderBox.get(1);
    Log.d(TAG, "getOrder: order " + order);

    // 要在订单中获取客户customer id，只需要使用 getTargetId() 它可以更有效，因为它根本不查询数据库。
    Log.d(TAG, "getOrder: target id " + order.customer.getTargetId());
    // 要在订单中获取客户customer整个对象，需要使用 getTarget()
    Customer customer = order.customer.getTarget();
    Log.d(TAG, "getOrder: customer " + customer);

    // 移除关系
    order.customer.setTarget(null);
    // 持久化到数据库
    mOrderBox.put(order);

    Log.d(TAG, "getOrder: order " + order);
    Log.d(TAG, "getOrder: target id " + order.customer.getTargetId());

    customer = order.customer.getTarget();
    Log.d(TAG, "getOrder: customer " + customer);

    // 重新绑定关系
    order.customer.setTargetId(3);

    // 持久化到数据库
    mOrderBox.put(order);

    // 重新得到order
    order = mOrderBox.get(1);
    Log.d(TAG, "getOrder: order " + order);
    Log.d(TAG, "getOrder: customer " + order.customer.getTarget());
}

```

## 一对多
一个客户可以对用多个订单
在Customer中添加**ToMany<Order> orders**属性，并继续维持Order中的**ToOne<Customer>**

``` java
    // 使用@Backlink注解，它链接回目标对象中的一对一关系
    @Backlink // 必须在Order类中添加属性ToOne<Customer> 属性名; 属性名任意 否则会报错
    public ToMany<Order> orders;
```

``` java
public void toMany(View view) {
    Customer customer = new Customer();
    customer.name="zhangsan";
    customer.orders.add(new Order());
    customer.orders.add(new Order());
    customer.orders.add(new Order());

    mCustomerBox.put(customer);

    Log.d(TAG, "toMany: customer " + customer);
    for (Order order : customer.orders) {
        Log.d(TAG, "toMany: order " + order);
    }
}
```

## 树型结构关系
可以使用指向自身的to-one和to-many关系来构成树型结构

json 数据结构：
```json
{
  "code": 1,
  "errmsg": "ok",
  "data": [
    {
      "itemCode": "11",
      "itemName": "受伤",
      "itemSub": [
        {
          "itemCode": "11001",
          "itemName": "手"
        },
        {
          "itemCode": "11002",
          "itemName": "足"
        },
        {
          "itemCode": "11003",
          "itemName": "头"
        }
      ]
    },
    {
      "itemCode": "22",
      "itemName": "皮疹",
      "itemSub": [
        {
          "itemCode": "22001",
          "itemName": "手"
        },
        {
          "itemCode": "22002",
          "itemName": "足"
        },
        {
          "itemCode": "22003",
          "itemName": "脸"
        }
      ]
    },
    {
      "itemCode": "33",
      "itemName": "疱疹",
      "itemSub": [
        {
          "itemCode": "33001",
          "itemName": "手"
        },
        {
          "itemCode": "33002",
          "itemName": "足"
        },
        {
          "itemCode": "33003",
          "itemName": "脸"
        },
        {
          "itemCode": "33004",
          "itemName": "口腔"
        }
      ]
    },
    {
      "itemCode": "234234",
      "itemName": "红眼"
    },
    {
      "itemCode": "6345224",
      "itemName": "感冒"
    },
    {
      "itemCode": "63456454",
      "itemName": "咳嗽"
    }
  ]
}

```

CheckItem
```java
@Entity
public class CheckItem {
    @Id
    public long id;
    public String itemCode;
    public String itemName;
    public int level;

    @Backlink
    public ToMany<CheckItem> children;
    public ToOne<CheckItem> parent;

    @Transient
    public List<CheckItem> itemSub;
}
```
> 注意如上字段不能将children替换为itemSub 如public ToMany<CheckItem> itemSub形式。在使用Gson进行转换的时候会抛出异常
Can not set io.objectbox.relation.ToMany field to java.util.ArrayList

使用见--->
treeNode() {
}

getTreeNode() {
}


## @Uid的使用
如果要直接删除和添加表，那么直接操作就可以了。但是如果要保留之前的数据，这就得要使用@Uid来重命名类名或属性名了。
```java
@Entity
public class MyEntity {
    @Id
    public long id;

    public String year;
    
    public String day;
}
```
然后添加了几条数据
```java 
mMyEntityBox.put(new MyNewEntity(0, "2016", "22"));
mMyEntityBox.put(new MyNewEntity(0, "2017", "23"));
mMyEntityBox.put(new MyNewEntity(0, "2018", "24"));
```

* 在实体上使用@Uid
```java
@Entity
@Uid
public class MyEntity {}
```

* 编译项目报错
```
注: [ObjectBox] Starting ObjectBox processor (debug: false)
错误: [ObjectBox] UID operations for entity "MyEntity": 
[Rename] apply the current UID using @Uid(824981247062842558L) - 
[Change/reset] apply a new UID using @Uid(8060000089094701411L)
1 个错误
```
提示当前的Uid是824981247062842558L，之前的Uid是8060000089094701411L

* 将重置后得Uid应用在实体上

注意，我们是要改名，所以要使用[Rename]部分，如果是改变字段类型，要使用[Change/reset]部分
```java
@Entity
@Uid(824981247062842558L)
public class MyEntity {}
```

* 在语言层面上重命名

MyEntity -> MyNewEntity
```java
@Entity
@Uid(824981247062842558L)
public class MyNewEntity {}
```
这样，之前的数据就得以保留了下来。

* 更改字段

更改year字段类型为int，[Change/reset]
重命名day字段为dayOfMonth，[Rename]

> 注意 更改字段类型可能会导致数据丢失，例如将String year -> int year，数据全变为了0

## 自定义类型

MyNewEntity.Color.java

MyNewEntity.ColorConvert.java

```java 
    public enum Color {
        RED(1, "F00"),
        GREEN(2, "0F0"),
        BLUE(3, "00F"),
        BLACK(4, "000"),
        WHITE(5, "FFF");

        public int code;

        public String msg;

        Color(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

//    如果您在实体类中定义自定义类型或转换器，则它们必须是静态的。否则编译错误: 需要包含MyNewEntity.ColorConvert的封闭实例
    static class ColorConverter implements PropertyConverter<Color, Integer> {

        @Override
        public Color convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {
                return null;
            }

            for (Color color : Color.values()) {
                if (color.code == databaseValue) {
                    return color;
                }
            }
            return Color.BLACK;
        }

        @Override
        public Integer convertToDatabaseValue(Color entityProperty) {
            return entityProperty == null ? null : entityProperty.code;
        }
    }

    @Convert(converter = ColorConverter.class, dbType = Integer.class)
    public Color color;
```


```java 
    public void colorConvert(View view) {
        MyNewEntity entity = new MyNewEntity();
        entity.year = 2015;
        entity.dayOfMonth = "11";
        entity.color = MyNewEntity.Color.GREEN;
        mMyEntityBox.put(entity);
        Log.d(TAG, "colorConvert: " + entity);
    }

    public void getColorConvert(View view) {
        mMyNewEntityQuery = mMyEntityBox.query()
                .equal(MyNewEntity_.color, MyNewEntity.Color.GREEN.code)
                .notEqual(MyNewEntity_.year, 0)
                .build();

        List<MyNewEntity> myNewEntities = mMyNewEntityQuery.find();
        Log.d(TAG, "myNewEntities: " + myNewEntities);
        for (MyNewEntity myNewEntity : myNewEntities) {
            Log.d(TAG, "getColorConvert: " + myNewEntity);
        }
    }
```

## FAQ
* minSdkVersion >= 15
* box.get(id > 0)，id必须大于0，java.lang.IllegalArgumentException: Illegal key value: 0
* 默认情况下，只有ObjectBox可以分配ID。如果您尝试使用的ID大于当前最高ID或更改ID，则ObjectBox会报错。
  它建议在插入数据的时候，id可以使用0。
  如果需要自己分配ID，则可以将ID注释更改为 @Id(assignable = true) long id;。这将允许将一个对象Id有任何值，包括0。
  我认为为了避免报错，还是应该加上该注解，否则每次put之前都要检查该id是否存在
  > java.lang.IllegalArgumentException: ID is higher or equal to internal ID sequence: 100 (vs. 2). 
  Use ID 0 (zero) to insert new entities.
* 如果使用了一对多，那么在多的一方必须要使用ToOne指向一，否则会报错
```
注: [ObjectBox] Starting ObjectBox processor (debug: false)
错误: [ObjectBox] Illegal @Backlink: no (to-one) relation found in 'Order'. Required by backlink to-many relation 'orders' in 'Customer'.
1 个错误
```

