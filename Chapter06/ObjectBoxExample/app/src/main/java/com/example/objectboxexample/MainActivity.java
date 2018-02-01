package com.example.objectboxexample;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.model.ApiResult;
import com.example.model.CheckItem;
import com.example.model.CheckItem_;
import com.example.model.Customer;
import com.example.model.Note;
import com.example.model.Note_;
import com.example.model.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.PropertyQuery;
import io.objectbox.query.Query;
import io.objectbox.relation.ToMany;

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

    private BoxStore boxStore;

    private Box<Note> notesBox;
    private Query<Note> notesQuery;

    private Box<Customer> mCustomerBox;
    private Query<Customer> mCustomerQuery;

    private Box<Order> mOrderBox;
    private Query<Order> mOrderQuery;

    private Box<CheckItem> mCheckItemBox;
    private Query<CheckItem> mCheckItemQuery;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        // 从App中得到BoxStore
        BoxStore boxStore = ((App) getApplication()).getBoxStore();
        // 初始化notesBox
        notesBox = boxStore.boxFor(Note.class);

        mCustomerBox = boxStore.boxFor(Customer.class);

        mOrderBox = boxStore.boxFor(Order.class);

        mCheckItemBox = boxStore.boxFor(CheckItem.class);

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

    public void treeNode(View view) {
        // 模拟数据来源于服务器返回的json
        String jsonStr = "{\n" +
                "  \"code\": 1,\n" +
                "  \"errmsg\": \"ok\",\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"itemCode\": \"11\",\n" +
                "      \"itemName\": \"受伤\",\n" +
                "      \"itemSub\": [\n" +
                "        {\n" +
                "          \"itemCode\": \"11001\",\n" +
                "          \"itemName\": \"手\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"itemCode\": \"11002\",\n" +
                "          \"itemName\": \"足\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"itemCode\": \"11003\",\n" +
                "          \"itemName\": \"头\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"itemCode\": \"22\",\n" +
                "      \"itemName\": \"皮疹\",\n" +
                "      \"itemSub\": [\n" +
                "        {\n" +
                "          \"itemCode\": \"22001\",\n" +
                "          \"itemName\": \"手\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"itemCode\": \"22002\",\n" +
                "          \"itemName\": \"足\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"itemCode\": \"22003\",\n" +
                "          \"itemName\": \"脸\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"itemCode\": \"33\",\n" +
                "      \"itemName\": \"疱疹\",\n" +
                "      \"itemSub\": [\n" +
                "        {\n" +
                "          \"itemCode\": \"33001\",\n" +
                "          \"itemName\": \"手\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"itemCode\": \"33002\",\n" +
                "          \"itemName\": \"足\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"itemCode\": \"33003\",\n" +
                "          \"itemName\": \"脸\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"itemCode\": \"33004\",\n" +
                "          \"itemName\": \"口腔\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"itemCode\": \"234234\",\n" +
                "      \"itemName\": \"红眼\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"itemCode\": \"6345224\",\n" +
                "      \"itemName\": \"感冒\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"itemCode\": \"63456454\",\n" +
                "      \"itemName\": \"咳嗽\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";

        Gson gson = new Gson();
        Type typeOfT = new TypeToken<ApiResult<List<CheckItem>>>(){}.getType();
        ApiResult<List<CheckItem>> apiResult = gson.fromJson(jsonStr, typeOfT);
        Log.d(TAG, "apiResult: " + apiResult);
        List<CheckItem> checkItems = apiResult.data;
        for (CheckItem checkItem : checkItems) {
            Log.d(TAG, "checkItem: " + checkItem);
            if (checkItem.itemSub != null) {
                // 添加子项
                checkItem.children.addAll(checkItem.itemSub);
            }
            // 将第一级设置为1
            checkItem.level = 1;
            // 持久化
            mCheckItemBox.put(checkItem);

            Log.d(TAG, "after put: " + checkItem);
        }
    }

    public void getTreeNode(View view) {
        List<CheckItem> checkItems = mCheckItemBox.getAll();
        for (CheckItem checkItem : checkItems) {
            Log.d(TAG, "checkItem: " + checkItem);

            // 得到子项
            ToMany<CheckItem> children = checkItem.children;
            if (children != null && children.size() > 0) {
                Log.d(TAG, "checkItem.children: " + children);
                for (CheckItem child : children) {
                    Log.d(TAG, "child: " + child);
                }
            }
            Log.d(TAG, "-------------------------");

            //  得到父项 checkItem.parent.getTarget();
        }
        Log.d(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        mCheckItemQuery = mCheckItemBox.query()
                .equal(CheckItem_.level, 1)
                .build();

        checkItems = mCheckItemQuery.find();
        for (CheckItem checkItem : checkItems) {
            Log.d(TAG, "checkItem: " + checkItem);
            ToMany<CheckItem> children = checkItem.children;
            if (children != null && children.size() > 0) {
                Log.d(TAG, "checkItem.children: " + children);
                for (CheckItem child : children) {
                    Log.d(TAG, "child: " + child);
                }
            }
            Log.d(TAG, "-------------------------");
        }

    }
}