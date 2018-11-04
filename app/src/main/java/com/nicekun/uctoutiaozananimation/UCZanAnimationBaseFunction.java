package com.nicekun.uctoutiaozananimation;

public class UCZanAnimationBaseFunction implements UCZanAnimationFunction {
    private float mDownwardStartSpeed;
    private float mRightStartSpeed;

    private float mGravity = 3;
    private float mAlphaThinSpeedPerTime = 0;
    private int mAlphaKeepTime = 0;


    public UCZanAnimationBaseFunction(float downwardStartSpeed,
                                           float rightStartSpeed) {

        this.mDownwardStartSpeed = downwardStartSpeed;
        this.mRightStartSpeed = rightStartSpeed;
    }

    public UCZanAnimationBaseFunction(float downwardStartSpeed,
                                           float rightStartSpeed,
                                           float gravity,
                                           float alphaThinSpeedPerTime,
                                           int alphaKeepTime) {

        this.mDownwardStartSpeed = downwardStartSpeed;
        this.mRightStartSpeed = rightStartSpeed;

        this.mGravity = gravity;
        this.mAlphaThinSpeedPerTime = alphaThinSpeedPerTime;

        this.mAlphaKeepTime = alphaKeepTime;
    }

    @Override
    public int moveDeltaX(int redrawCount) {
        return (int) (mRightStartSpeed * redrawCount);
    }

    @Override
    public int moveDeltaY(int redrawCount) {
        return (int) ((mDownwardStartSpeed + mGravity * redrawCount) * redrawCount);
    }

    @Override
    public int moveAlpha(int invalidCount) {
        return (int) (invalidCount * (mAlphaThinSpeedPerTime - mAlphaKeepTime));
    }

}
