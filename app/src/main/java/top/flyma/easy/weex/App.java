package top.flyma.easy.weex;

public class App extends WeexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
