package com.paynetone.counter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.paynetone.counter.R;

public class NCustomProgressBar   extends LinearLayout {
    View loaderCircle;
    int delta = 10;
    boolean check = true;
    boolean stopAnimation = false;
    Animation rotation,animationOut;
    public NCustomProgressBar(Context context) {
        super(context);
        rotation = AnimationUtils.loadAnimation(context, R.anim.n_rotation_repeat);
        animationOut = AnimationUtils.loadAnimation(context,R.anim.n_animation_progress_out);
        animationOut.setAnimationListener(listener);
        inflate(context, R.layout.custom_progress_bar,this);
        loaderCircle = findViewById(R.id.loader_circle);
        runAnimation();
    }

    public NCustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        rotation = AnimationUtils.loadAnimation(context,R.anim.n_rotation_repeat);
        animationOut = AnimationUtils.loadAnimation(context,R.anim.n_animation_progress_out);
        animationOut.setAnimationListener(listener);
        inflate(context, R.layout.custom_progress_bar,this);
        loaderCircle = findViewById(R.id.loader_circle);
        runAnimation();
    }

    public NCustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rotation = AnimationUtils.loadAnimation(context,R.anim.n_rotation_repeat);
        animationOut = AnimationUtils.loadAnimation(context,R.anim.n_animation_progress_out);
        animationOut.setAnimationListener(listener);
        inflate(context, R.layout.custom_progress_bar,this);
        loaderCircle = findViewById(R.id.loader_circle);
        runAnimation();
    }

    private void runAnimation(){
        loaderCircle.startAnimation(rotation);
    }

    Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            rotation.cancel();
            setVisibility(GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public void startAnimationOut(){
        startAnimation(animationOut);
    }

    public void stopAnimation(){
        stopAnimation = true;
    }

    public void startAnimation(){
        stopAnimation = false;
        runAnimation();
    }

    @Override
    public void setVisibility(int visibility) {
        if(visibility==VISIBLE) {
            super.setVisibility(visibility);
            stopAnimation = false;
            runAnimation();
        }else {
            if(stopAnimation) {
                super.setVisibility(visibility);
            }else {
                stopAnimation = true;
                startAnimationOut();
            }
        }
    }
}
