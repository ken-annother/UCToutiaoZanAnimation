package com.nicekun.uczananimation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class UCZanAnimationView extends SurfaceView implements SurfaceHolder.Callback,  UCZanAnimationDirector.Callback{
    private UCZanAnimationDirector mUcZanAnimationDirector;

    public UCZanAnimationView(Context context, UCZanAnimationResource resource) {
        super(context);

        SurfaceHolder holder = getHolder();
        mUcZanAnimationDirector = new UCZanAnimationDirector(holder);

        UCZanSpriteManager spriteManager = new UCZanSpriteManager(resource);
        mUcZanAnimationDirector.setSpriteManager(spriteManager);
        mUcZanAnimationDirector.addCallback(this);

        holder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);//保持屏幕长亮

        holder.setFormat(PixelFormat.TRANSPARENT);//设置背景透明
        setZOrderOnTop(true);
    }

    public void playZan(int startX, int startY) {
       mUcZanAnimationDirector.generateZan(startX, startY);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(UCZanUtils.TAG, "surfaceCreated");
        mUcZanAnimationDirector.onCreate();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(UCZanUtils.TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(UCZanUtils.TAG, "surfaceDestroyed");
        mUcZanAnimationDirector.onDestroy();
    }

    @Override
    public void onSpriteCountChanged(int count) {
        Log.e(UCZanUtils.TAG,  "UCZanAnimationView - onSpriteCountChanged : onSpriteCountChanged count:" + count);
    }

    public void setTypeface(Typeface typeface) {
        mUcZanAnimationDirector.setIndicatorTypeface(typeface);
    }
}
