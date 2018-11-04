package com.nicekun.uctoutiaozananimation;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UCZanAnimationResource {
    private List<Bitmap> normalList = new ArrayList<>();
    private List<Bitmap> supriseList = new ArrayList<>();

    private UCZanAnimationResource(UCZanAnimationResourceBuilder builder) {
        for (Map.Entry<String, Bitmap> entry : builder.res.entrySet()) {
            if (builder.suprsizeNameList.contains(entry.getKey())) {
                supriseList.add(entry.getValue());
            } else {
                normalList.add(entry.getValue());
            }
        }
    }

    public Bitmap getRandomBitmap() {
        return getRandomBitmap(false);
    }

    public Bitmap getRandomBitmap(boolean surprise) {
        Bitmap bitmap = null;
        if (surprise) {
            if (supriseList.size() > 0) {
                bitmap = supriseList.get((int) (Math.random() * supriseList.size()));
            }
        } else {
            if (normalList.size() > 0) {
                bitmap = normalList.get((int) (Math.random() * normalList.size()));
            }
        }
        return bitmap;
    }

    public Bitmap getBitmapByResId() {
        return null;
    }

    public Bitmap getBitmapByName() {
        return null;
    }


    public static class UCZanAnimationResourceBuilder {
        private Context mContext;
        HashMap<String, Bitmap> res = new HashMap<>();
        List<String> suprsizeNameList = new ArrayList<>();

        public UCZanAnimationResourceBuilder(Context context) {
            mContext = context;
        }

        public UCZanAnimationResource build() {
            return new UCZanAnimationResource(this);
        }

        public UCZanAnimationResourceBuilder appendResource(int resId) {
            Bitmap bitmap = UCZanBitmapFactory.from(mContext, resId);
            res.put("res-" + res, bitmap);
            return this;
        }

        public UCZanAnimationResourceBuilder appendAssetResource(String assetDir) {
            Map<String, Bitmap> bitmaps = UCZanBitmapFactory.fromAssets(mContext, assetDir);
            if (bitmaps != null) {
                for (Map.Entry<String, Bitmap> entry : bitmaps.entrySet()) {
                    res.put("assets-" + entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        public UCZanAnimationResourceBuilder setSupriseFileNameOfAssets(String[] fileName) {
            for (String f : fileName) {
                suprsizeNameList.add("assets-" + f);
            }
            return this;
        }

        public UCZanAnimationResourceBuilder setSupriseFileNameOfRes(String[] fileName) {
            for (String f : fileName) {
                suprsizeNameList.add("res-" + f);
            }
            return this;
        }

        public UCZanAnimationResourceBuilder appendAssetZipResource(String zipFileName) {
            Map<String, Bitmap> bitmaps = UCZanBitmapFactory.fromAssetsZipFile(mContext, zipFileName);
            if (bitmaps != null) {
                res.putAll(bitmaps);
            }
            return this;
        }

        public UCZanAnimationResourceBuilder appendResource(Bitmap bitmap, String tag) {
            res.put(tag, bitmap);
            return this;
        }

        public UCZanAnimationResourceBuilder appendResources(Map<String, Bitmap> bitmaps) {
            if (bitmaps != null) {
                res.putAll(bitmaps);
            }
            return this;
        }
    }
}
