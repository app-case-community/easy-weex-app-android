package com.alibaba.weex.commons;

import android.content.Context;
import com.facebook.drawee.backends.pipeline.Fresco;

public class FrescoUtil {
    public static void init(Context context) {
        Fresco.initialize(context);
    }
}
