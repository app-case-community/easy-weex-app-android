package top.flyma.easy.weex;

import java.util.HashMap;
import java.util.Map;

public class App extends WeexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    Map<String, String> weexCustomOptions() {
        return new HashMap<String, String>() {{
            put("appName", "easy-weex");
            put("appGroup", "top.flyme.easy");
        }};
    }

    @Override
    boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
