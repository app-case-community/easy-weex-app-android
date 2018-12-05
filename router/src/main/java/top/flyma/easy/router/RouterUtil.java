package top.flyma.easy.router;

import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;

public class RouterUtil {
    public static void init(Application context, boolean isDebug) {
        if (isDebug) {
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode
        }
        ARouter.init(context);
    }
}
