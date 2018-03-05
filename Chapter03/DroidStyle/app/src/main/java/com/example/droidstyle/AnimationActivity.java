package com.example.droidstyle;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
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

    public void rotate(View view) {
        RotateAnimation rotateAnim = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.rotate);
        view.startAnimation(rotateAnim);
    }

    public void translate(View view) {
        TranslateAnimation animation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.translate);
        view.startAnimation(animation);
    }

    public void set_anim(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.set_anim);
        view.startAnimation(animation);
    }

    public void onScaleWidth(final View view) {
        // 得到当前控件的宽度
        final int viewWidth = view.getWidth();
        // 通过AnimatorInflater加载Animator类实例动画
        ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.value_animator);
        // 监听值变化
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator va) {
                // 当前动画值，即当前宽度比例值，刚开始为100，即100%
                int currValue = (int) va.getAnimatedValue();
                // 根据比例更改view的宽度
                view.getLayoutParams().width = viewWidth * currValue / 100;
                // 应用
                view.requestLayout();
            }
        });
        // 启动动画
        valueAnimator.start();
    }

    public void onScaleWidth2(View view) {
        // 得到当前控件的宽度
        final int viewWidth = view.getWidth();
        // 将目标view进行包装
        ViewWrapper viewWrapper = new ViewWrapper(view, viewWidth);
        // 通过AnimatorInflater加载Animator类实例动画
        ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.object_animator);
        // 设置动画的目标对象为包装后的view
        objectAnimator.setTarget(viewWrapper);
        // 启动动画
        objectAnimator.start();
    }

    private static class ViewWrapper {
        private View targetView;
        private int maxWidth;

        private ViewWrapper(View targetView, int maxWidth) {
            this.targetView = targetView;
            this.maxWidth = maxWidth;
        }

        /**
         * 得到View实际的宽度
         */
        public int getWidth() {
            return targetView.getLayoutParams().width;
        }

        /**
         * 设置View实际的宽度 = 最大宽度 * 动画宽度比例值 / 100
         * @param widthValue 动画宽度比例值
         */
        public void setWidth(int widthValue) {
            targetView.getLayoutParams().width = maxWidth * widthValue / 100;
            targetView.requestLayout();
        }
    }
}
