package com.nicekun.uctoutiaozananimation;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View mZanBtn;
    private UCZanAnimation mUcZanAnimation;
    private FrameLayout mPalyground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
    }

    private void initView() {
        mZanBtn = findViewById(R.id.zan_btn);
        mPalyground = findViewById(R.id.playground);

        UCZanAnimationResource ucZanAnimationResource = new UCZanAnimationResource.UCZanAnimationResourceBuilder(this)
                .appendAssetResource("UCZanAnimation")
                .setSupriseFileNameOfAssets(new String[]{"玫瑰花", "元宝", "音乐", "玫瑰花"})
                .build();

        mUcZanAnimation = new UCZanAnimation(this, mPalyground, ucZanAnimationResource);
        mUcZanAnimation.setTypeface(Typeface.createFromAsset(getAssets(), "STXINGKA.TTF"));

        mZanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.zan_btn) {
            mUcZanAnimation.play(v);
        }
    }
}
