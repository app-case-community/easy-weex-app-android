package top.flyma.easy.weex.extend.module;

import android.util.Log;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

public class NativeModule extends WXModule {

    @JSMethod
    public void log(String log) {
        Log.d("NativeModule", log);
    }
}
