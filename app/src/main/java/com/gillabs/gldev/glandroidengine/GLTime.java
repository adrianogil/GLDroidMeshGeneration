package com.gillabs.gldev.glandroidengine;

import android.os.SystemClock;

/**
 * Created by gil on 12/12/17.
 */
public class GLTime {

    private static float mTime;
    private static float mLastTime;
    private static float mDeltaTime;

    public static void OnFrameStart()
    {
        long time = SystemClock.uptimeMillis() % 10000L;

        mLastTime = mTime;
        mTime = time / 10000f;

        mDeltaTime = (mTime - mLastTime);
    }

    public static float time() {
        return mTime;
    }

    public static float deltaTime() {
        return mDeltaTime;
    }
}
