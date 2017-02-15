package net.jgab.todd.core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by sid on 29/11/16.
 */

public class ScreenUtils {

    @SuppressWarnings("unchecked")
    public static <T> T findViewById(View parent, int id) {
        return (T) parent.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public static <T> T findViewById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

}
