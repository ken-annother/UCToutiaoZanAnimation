package com.nicekun.uczananimation;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UCZanAnimationDirector implements Runnable {

    private SurfaceHolder mSurfaceHolder;
    private final Paint mPaint;

    private CopyOnWriteArrayList<UCZanSprite> mList = new CopyOnWriteArrayList<>();

    private UCZanSpriteManager mUcZanSpriteManager;

    private static final int THREAD_CANCEL = 0x0001;
    private static final int THREAD_NORMAL = 0x1000;

    private int mThreadStatus;
    private final UCZanCountIndicator mZanCountIndicator;

    public UCZanAnimationDirector(SurfaceHolder holder) {
        this.mSurfaceHolder = holder;
        mPaint = new Paint();
        mZanCountIndicator = new UCZanCountIndicator();
    }


    public void setSpriteManager(UCZanSpriteManager spriteManager) {
        mUcZanSpriteManager = spriteManager;
    }


    public void generateZan(int startX, int startY) {
        mZanCountIndicator.trigger(startX, startY);

        List<UCZanSprite> sprites = mUcZanSpriteManager.obtain(mZanCountIndicator.getSpriteNumber(startX, startY), mZanCountIndicator.getSpriteType(startX, startY));

        for (UCZanSprite randomSprite : sprites) {
            randomSprite.setPostion(startX, startY);
            randomSprite.setGravity(UCZanSprite.Gravity.CENTER);
            randomSprite.setFunction(new UCZanAnimationBaseFunction((float) (-20 * Math.random()), (float) (-10f * Math.random() + 2)));
            addSprite(randomSprite);
        }
    }

    private void addSprite(UCZanSprite sprite) {
        if (sprite != null) {
            mList.add(sprite);
            if (mCallback != null) {
                mCallback.onSpriteCountChanged(mList.size());
            }
        }
    }


    public void onCreate() {
        mThreadStatus = THREAD_NORMAL;
        new Thread(this).start();
    }


    public void onDestroy() {
        mThreadStatus = THREAD_CANCEL;
    }

    @Override
    public void run() {
        while (mThreadStatus != THREAD_CANCEL) {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            try {
                if (canvas != null) {
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    for (UCZanSprite sprite : mList) {
                        Rect rect = sprite.getRect();
                        Rect clipBounds = canvas.getClipBounds();
                        if (!UCZanUtils.outOf(clipBounds, rect) && sprite.getAlpha() > 0) {
                            sprite.draw(canvas, mPaint);
                        } else {
                            mList.remove(sprite);
                            if (mCallback != null) {
                                mCallback.onSpriteCountChanged(mList.size());
                            }
                        }
                    }

                    mZanCountIndicator.draw(canvas);
                }
                Thread.sleep(80);
            } catch (Exception e) {
                Log.e(UCZanUtils.TAG, "UCZanAnimationDirector - run: " + e.toString());
            } finally {
                if (canvas != null) {
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

    }

    private Callback mCallback;

    public void addCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setIndicatorTypeface(Typeface typeface) {
        if(mZanCountIndicator != null){
            mZanCountIndicator.setTypeface(typeface);
        }
    }

    public interface Callback {
        void onSpriteCountChanged(int count);
    }
}
