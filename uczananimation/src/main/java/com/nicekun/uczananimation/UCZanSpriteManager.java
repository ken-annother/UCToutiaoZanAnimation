package com.nicekun.uczananimation;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class UCZanSpriteManager {
    private UCZanAnimationResource mResource;

    public UCZanSpriteManager(UCZanAnimationResource resource) {
        this.mResource = resource;
    }

    public List<UCZanSprite> obtain(int count, SpriteType normal) {
        ArrayList<UCZanSprite> sprites = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UCZanSprite sprite = obtainSingle(normal);
            if (sprite != null) {
                sprites.add(sprite);
            }
        }
        return sprites;
    }

    public UCZanSprite obtainSingle(SpriteType normal) {
        Bitmap randomBitmap = null;
        switch (normal) {
            case NORMAL:
                randomBitmap = mResource.getRandomBitmap();
                break;
            case SURPRISE:
                randomBitmap = mResource.getRandomBitmap(true);
                break;
        }

        if (randomBitmap != null) {
            return new UCZanSprite("actor-" + (int) (Math.random() * 100), randomBitmap);
        }

        return null;
    }

    public enum SpriteType {
        NORMAL,
        SURPRISE
    }
}
