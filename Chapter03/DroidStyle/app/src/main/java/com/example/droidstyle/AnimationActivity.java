package com.example.droidstyle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

public class AnimationActivity extends AppCompatActivity {
    private ImageView iv_clip, iv_scale;
    private SeekBar sb_clip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        iv_clip = findViewById(R.id.iv_clip);
//        通过设置level值控制裁剪多少，level取值范围为0~10000，默认为0，表示完全裁剪，图片将不可见；10000则完全不裁剪，可见完整图片。
        iv_clip.setImageLevel(5000);

        iv_scale = findViewById(R.id.iv_scale);
//        通过设置level值控制缩放多少，level取值范围为0~10000，默认为0，表示完全缩放，图片将不可见；10000则完全不缩放，可见完整图片。
        iv_scale.setImageLevel(5000);

        sb_clip = findViewById(R.id.sb_clip);
//        通过滑块改变图片的level值
        sb_clip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                iv_clip.setImageLevel(progress);
                iv_scale.setImageLevel(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
