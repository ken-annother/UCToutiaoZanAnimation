package com.nicekun.uctoutiaozananimation;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UCZanCountIndicator {

    private final TextPaint mCountPaint;
    private final TextPaint mEncouragePaint;
    private final TextPaint mSmallEncouragePaint;


    public UCZanCountIndicator() {
        mCountPaint = new TextPaint();
        mCountPaint.setTextSize(80);
        mCountPaint.setAntiAlias(true);
        mCountPaint.setStyle(Paint.Style.FILL);
        mCountPaint.setColor(Color.parseColor("#F9534E"));
        mCountPaint.setShadowLayer(6, 0, 0, Color.BLACK);


        mSmallEncouragePaint = new TextPaint();
        mSmallEncouragePaint.setTextSize(70);
        mSmallEncouragePaint.setAntiAlias(true);
        mSmallEncouragePaint.setStyle(Paint.Style.FILL);
        mSmallEncouragePaint.setColor(Color.parseColor("#FCD84A"));
        mSmallEncouragePaint.setShadowLayer(4, 0, 0, Color.BLACK);

        mEncouragePaint = new TextPaint();
        mEncouragePaint.setTextSize(95);
        mEncouragePaint.setAntiAlias(true);
        mEncouragePaint.setStyle(Paint.Style.FILL);
        mEncouragePaint.setColor(Color.parseColor("#FCD84A"));
        mEncouragePaint.setShadowLayer(6, 0, 0, Color.BLACK);
    }


    private HashMap<String, Integer> mPointCount = new HashMap<>();
    private HashMap<String, Long> mPonitLastClick = new HashMap<>();
    private long drawCount;
    private static final int GT_IGNORECOUNT = 10;


    public void trigger(int pressX, int pressY) {
        Long lastCount = mPonitLastClick.get(pressX + "-" + pressY);
        if (lastCount == null) {
            mPointCount.put(pressX + "-" + pressY, 1);
            mPonitLastClick.put(pressX + "-" + pressY, drawCount);
            return;
        }

        int delta;
        if (drawCount < lastCount) {
            delta = (int) (Long.MAX_VALUE - lastCount + drawCount);
        } else {
            delta = (int) (drawCount - lastCount);
        }

        Integer nowPointCount = mPointCount.get(pressX + "-" + pressY);
        if (delta < GT_IGNORECOUNT && nowPointCount != null) {
            mPointCount.put(pressX + "-" + pressY, ++nowPointCount);
        } else {
            mPointCount.put(pressX + "-" + pressY, 1);
        }

        mPonitLastClick.put(pressX + "-" + pressY, drawCount);
    }

    public void draw(Canvas canvas) {
        drawCount++;
        if (drawCount == Long.MAX_VALUE) {
            drawCount = 0;
        }

        Set<Map.Entry<String, Long>> entries = mPonitLastClick.entrySet();
        Iterator<Map.Entry<String, Long>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> next = iterator.next();
            Long lastClick = next.getValue();
            int delta;
            if (drawCount < lastClick) {
                delta = (int) (Long.MAX_VALUE - lastClick + drawCount);
            } else {
                delta = (int) (drawCount - lastClick);
            }

            if (delta > GT_IGNORECOUNT) {
                iterator.remove();
                continue;
            }

            drawText(canvas, next.getKey());
        }

    }

    @SuppressWarnings("ConstantConditions")
    private void drawText(Canvas canvas, String pointKey) {
        String[] split = pointKey.split("-");
        int pressX = Integer.valueOf(split[0]);
        int pressY = Integer.valueOf(split[1]);

        String countExpr = "" + mPointCount.get(pointKey);
        float countExprWidth = mCountPaint.measureText(countExpr);

        EggType desc = getCountTextDesc(mPointCount.get(pointKey));
        float descWidth = mCountPaint.measureText(desc.getDesc());

        canvas.save();

        int width = canvas.getWidth();

        int startX = (int) (pressX - (descWidth + countExprWidth + 16) / 2);
        int endX = (int) (pressX + (descWidth + countExprWidth + 16) / 2);

        if (startX < 20) {
            canvas.translate(startX + 20, 0);
        } else if (endX + 20 > width) {
            canvas.translate(width - endX - 20, 0);
        }

        canvas.drawText(countExpr, startX, pressY - 100, mCountPaint);
        canvas.drawText(desc.getDesc(), startX + countExprWidth + 16, pressY - 100, desc.mGrade < 3 ? mSmallEncouragePaint : mEncouragePaint);

        canvas.restore();
    }


    public enum EggType {
        NORMAL_START(1, "点赞"),
        NORMAL(2, "连击"),
        GOOD(3, "好腻害!!!"),
        NICE(4, "崇拜你!!!!"),
        UNBELIEVABLE(4, "手速惊人!!!!!");

        private int mGrade;
        private String mDesc;

        EggType(int grade, String desc) {
            this.mGrade = grade;
            this.mDesc = desc;
        }

        public int getGrade() {
            return mGrade;
        }

        public String getDesc() {
            return mDesc;
        }
    }

    private EggType getCountTextDesc(int count) {
        if (count <= 1) {
            return EggType.NORMAL_START;
        } else if (count < 30) {
            return EggType.NORMAL;
        } else if (count % 30 < 6) {
            if (count % 90 < 6) {
                return EggType.UNBELIEVABLE;
            } else if (count % 60 < 6) {
                return EggType.NICE;
            } else {
                return EggType.GOOD;
            }

        } else {
            return EggType.NORMAL;
        }
    }

    public void setTypeface(Typeface typeface) {
        if (mCountPaint != null) {
            mCountPaint.setTypeface(typeface);
        }

        if (mEncouragePaint != null) {
            mEncouragePaint.setTypeface(typeface);
        }

        if (mSmallEncouragePaint != null) {
            mSmallEncouragePaint.setTypeface(typeface);
        }
    }


    public int getSpriteNumber(int startX, int startY) {
        Integer count = mPointCount.get(startX + "-" + startY);
        if (count == null) {
            return (int) (3 * Math.random() + 1);
        }

        EggType desc = getCountTextDesc(count);
        int baseNumber = (int) (desc.mGrade * 3 * Math.random() + 1);
        if (desc.getGrade() > 2 && baseNumber < 6) {
            baseNumber += 6;
        }

        return baseNumber;
    }

    public UCZanSpriteManager.SpriteType getSpriteType(int startX, int startY) {
        Integer count = mPointCount.get(startX + "-" + startY);
        if (count == null) {
            return UCZanSpriteManager.SpriteType.NORMAL;
        }

        EggType desc = getCountTextDesc(count);
        return desc.getGrade() <= 2 ? UCZanSpriteManager.SpriteType.NORMAL : UCZanSpriteManager.SpriteType.SURPRISE;
    }
}
