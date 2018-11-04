package com.nicekun.uctoutiaozananimation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class UCZanSprite{
    private Bitmap pic;
    private String name;

    private int alpha;

    private int leftPos;
    private int topPos;

    private float relX;
    private float relY;

    public UCZanSprite(String name, Bitmap pic) {
        this.name = name;
        this.pic = pic;
        this.alpha = 255;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public int getLeftPos() {
        return leftPos;
    }

    public int getTopPos() {
        return topPos;
    }

    public float getRelX() {
        return relX;
    }

    public float getRelY() {
        return relY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setPostion(int leftPos, int topPos) {
        this.leftPos = leftPos;
        this.topPos = topPos;
    }

    public void setGravity(float relX, float relY) {
        if (relX < 0 || relX > 1 || relY < 0 || relY > 1) {
            return;
        }

        this.relX = relX;
        this.relY = relY;
    }

    public void setGravity(Gravity gravity) {
        setGravity(gravity.relX, gravity.relY);
    }

    private int invalidCount;

    public void draw(Canvas canvas, Paint paint) {
        invalidCount++;
        doMove(invalidCount);
        int alphaPre = paint.getAlpha();
        paint.setAlpha(this.alpha);
        canvas.drawBitmap(pic, leftPos - pic.getWidth() * relX, topPos - pic.getHeight() * relY, paint);
        paint.setAlpha(alphaPre);
    }


    private void doMove(int invalidCount){
        if(mUCZanAnimationFunction != null){
            leftPos = leftPos + mUCZanAnimationFunction.moveDeltaX(invalidCount);
            topPos = topPos + mUCZanAnimationFunction.moveDeltaY(invalidCount);
            alpha = alpha - mUCZanAnimationFunction.moveAlpha(invalidCount);
            if(alpha < 0){
                alpha = 0;
            }else if(alpha > 255){
                alpha = 255;
            }
        }
    }

    private UCZanAnimationFunction mUCZanAnimationFunction;

    public void setFunction(UCZanAnimationFunction function){
        this.mUCZanAnimationFunction = function;
    }

    public Rect getRect() {
        return new Rect(leftPos, topPos, leftPos + pic.getWidth(), topPos + pic.getHeight());
    }

    public enum Gravity {
        CENTER(0.5f, 0.5f), //中心
        NE(1, 0), //右上角
        N(0.5f, 0), //顶部中间
        NW(0, 0),
        E(1, 0.5f),
        W(0, 0.5f),
        S(0.5f, 1),
        SW(0, 1),
        SE(1, 1);

        private float relX;
        private float relY;

        Gravity(float relX, float relY) {
            this.relX = relX;
            this.relY = relY;
        }

        public float getRelX() {
            return relX;
        }

        public float getRelY() {
            return relY;
        }
    }
}
