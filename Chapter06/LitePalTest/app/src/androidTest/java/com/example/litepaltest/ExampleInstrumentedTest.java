package com.example.litepaltest;

import android.content.ContentValues;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.litepaltest.model.Album;
import com.example.litepaltest.model.Song;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.litepal.crud.DataSupport;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.litepaltest", appContext.getPackageName());
    }

    @Test
    public void testSaveData() throws Exception {
        Album album = new Album();
        album.name = "some name";
        album.price = 12.99f;
        album.save();

        Song song1 = new Song();
        song1.name = "song name 3";
        song1.duration = 130;
        song1.album = album;
        song1.save();

        Song song2 = new Song();
        song2.name = "song name 4";
        song2.duration = 123;
        song2.album = album;
        song2.save();
    }

    @Test
    public void testUpdateData() throws Exception {
        Album album1 = DataSupport.find(Album.class, 1);
        album1.price = 33.32f;
        album1.save();

        Album album2 = new Album();
        album2.name = "album name";
        album2.update(2);

        Album album3 = new Album();
        album3.price = 25f;

        album3.updateAll("id > ? or name like ?", "1", "%album%");

        ContentValues values = new ContentValues();
        values.put("duration", 222);
        // update song set duration = 222 where id > 1 or name like '%album%'
        DataSupport.updateAll(Song.class, values, "id > ? or name like ?", "1", "%album%");
    }

    @Test
    public void testDeleteData() throws Exception {
        DataSupport.delete(Song.class, 6L);
        DataSupport.deleteAll(Song.class, "id > ? or name like ?", "6", "%sss%");
    }


    @Test
    public void testQueryData1() throws Exception {
//        这种查询方式LitePal并不推荐，因为如果一旦关联表中的数据很多，查询速度可能就会非常慢。而且激进查询
// 只能查询出指定表的关联表数据，但是没法继续迭代查询关联表的关联表数据。
// 因此，这里我建议大家还是使用默认的懒加载更加合适
// DataSupport.findAll(Album.class, true);// isEager 是否激进查询
        List<Album> albumList = DataSupport.findAll(Album.class);// 懒加载
        System.out.println();
    }

    @Test
    public void testQueryData2() throws Exception {
        List<Song> songList = DataSupport.findAll(Song.class);
        Log.d(TAG, "testQueryData: " + songList);
    }
}
