package com.sxi.notes;

import android.animation.ObjectAnimator;

public class Utils {
    public static final String ROTATION = "rotation";
    public static final String TRANSLATIONY = "translationY";
    public static final String TRANSLATIONX = "translationX";
    public static final String ALPHA = "alpha";
    public static void setAnimation(Object target, String propertyName, long duration, long from, long to) {
        ObjectAnimator oa = new ObjectAnimator();
        oa.setTarget(target);
        oa.setDuration(duration);
        oa.setFloatValues(from, to);
        oa.setPropertyName(propertyName);
        oa.start();
    }
}
