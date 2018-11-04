package com.nicekun.uczananimation;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class UCZanAnimation {
    private Context mContext;
    private final UCZanAnimationView mUcZanAnimationView;
    private Typeface mTypeface;

    public UCZanAnimation(Context context, FrameLayout palyground, UCZanAnimationResource resource) {
        mContext = context;
        mUcZanAnimationView = new UCZanAnimationView(context, resource);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        palyground.addView(mUcZanAnimationView, params);
    }

    public void play(View view) {
        int width = view.getWidth();
        int height = view.getHeight();

        int location[] = new int[2];
        view.getLocationInWindow(location);
        int posLeft = location[0];
        int posTop = location[1];

//        Log.e(UCZanUtils.TAG, "UCZanAnimation - play : play view width:" + width + "; height:" + height + "; left:" + posLeft + "; top:" + posTop);

        int ucLocation[] = new int[2];
        mUcZanAnimationView.getLocationInWindow(ucLocation);
        int ucPosLeft = ucLocation[0];
        int ucPosTop = ucLocation[1];

//        Log.e(UCZanUtils.TAG, "UCZanAnimation - play :  UC location: ucPosLeft:" + ucPosLeft + "; ucPosTop:" + ucPosTop);
        mUcZanAnimationView.playZan(posLeft + width / 2 - ucPosLeft, posTop + height / 2 - ucPosTop);
    }


    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
        if(mUcZanAnimationView != null){
            mUcZanAnimationView.setTypeface(typeface);
        }
    }

    public Typeface getTypeface() {
        return mTypeface;
    }
}
