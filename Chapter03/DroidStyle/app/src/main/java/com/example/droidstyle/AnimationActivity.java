package com.example.droidstyle;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;

public class AnimationActivity extends AppCompatActivity {
    private ImageView iv_clip, iv_scale, iv_level_list;
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

        iv_level_list = findViewById(R.id.iv_level_list);

        sb_clip = findViewById(R.id.sb_clip);
//        通过滑块改变图片的level值
        sb_clip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                iv_clip.setImageLevel(progress);
                iv_scale.setImageLevel(progress);
                iv_level_list.setImageLevel(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void btnTransition(View view) {
        TransitionDrawable td = (TransitionDrawable) getResources().getDrawable(R.drawable.bg_imageview_transition);
        ImageView imageView = (ImageView) view;
        imageView.setImageDrawable(td);
        if (System.currentTimeMillis() % 1000000 == 1) {
            // 正向切换，即从第一个drawable切换到第二个
            td.startTransition(3000);
        } else {
            // 逆向切换，即从第二个drawable切换回第一个
            td.reverseTransition(3000);
        }
    }

    public void btnTransitionList(View view) {
        AnimationDrawable td = (AnimationDrawable) getResources().getDrawable(R.drawable.bg_imageview_animation_list);
        ImageView imageView = (ImageView) view;
        imageView.setImageDrawable(td);
        td.start();
    }

    public void fadeAlpha(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        view.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        view.startAnimation(animation);
    }

    public void scale(View view) {
//        <scale>标签对应的类为ScaleAnimation，父类也是Animation，添加到View上的用法和AlphaAnimation一样
        ScaleAnimation scaleAnim = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.scale_out);
        view.startAnimation(scaleAnim);
    }
}
