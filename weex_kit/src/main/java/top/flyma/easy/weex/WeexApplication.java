package top.flyma.easy.weex;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.alibaba.weex.commons.FrescoUtil;
import com.alibaba.weex.commons.adapter.DefaultWebSocketAdapterFactory;
import com.alibaba.weex.commons.adapter.FrescoImageAdapter;
import com.alibaba.weex.commons.adapter.FrescoImageComponent;
import com.alibaba.weex.commons.adapter.JSExceptionAdapter;
import com.alibaba.weex.extend.adapter.DefaultAccessibilityRoleAdapter;
import com.alibaba.weex.extend.adapter.InterceptWXHttpAdapter;
import com.alibaba.weex.extend.component.*;
import com.alibaba.weex.extend.module.*;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXException;
import top.flyma.easy.router.RouterUtil;
import top.flyma.easy.weex.extend.module.NativeModule;

import java.util.Map;

public abstract class WeexApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initThirdSdk();
        initWeex();
    }

    private void initThirdSdk() {
        RouterUtil.init(this, isDebug());
        FrescoUtil.init(getApplicationContext());
    }

    abstract Map<String, String> weexCustomOptions();

    private void initWeex() {
        WXEnvironment.setOpenDebugLog(isDebug());
        WXEnvironment.setApkDebugable(isDebug());

        WXBridgeManager.updateGlobalConfig("wson_on");

        Map<String, String> options = weexCustomOptions();
        for (Map.Entry<String, String> entry : options.entrySet()) {
            WXSDKEngine.addCustomOptions(entry.getKey(), entry.getValue());
        }

        WXSDKEngine.initialize(this,
                new InitConfig.Builder()
                        .setImgAdapter(new FrescoImageAdapter())
                        .setWebSocketAdapterFactory(new DefaultWebSocketAdapterFactory())
                        .setJSExceptionAdapter(new JSExceptionAdapter())
                        .setHttpAdapter(new InterceptWXHttpAdapter())
                        .build()
        );
        WXSDKManager.getInstance().setAccessibilityRoleAdapter(new DefaultAccessibilityRoleAdapter());

        try {
            loadPlugins();
        } catch (WXException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        registerLifecycle();
    }

    private void registerLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // The demo code of calling 'notifyTrimMemory()'
                if (false) {
                    // We assume that the application is on an idle time.
                    WXSDKManager.getInstance().notifyTrimMemory();
                }
                // The demo code of calling 'notifySerializeCodeCache()'
                if (false) {
                    WXSDKManager.getInstance().notifySerializeCodeCache();
                }
            }
        });
    }

    /**
     * 加载插件
     *
     * @throws WXException
     */
    private void loadPlugins() throws WXException, ClassNotFoundException {
        //Typeface nativeFont = Typeface.createFromAsset(getAssets(), "font/native_font.ttf");
        //WXEnvironment.setGlobalFontFamily("bolezhusun", nativeFont);

        WXSDKEngine.registerComponent("web", WXWeb.class);
        WXSDKEngine.registerComponent("image", FrescoImageComponent.class);

        WXSDKEngine.registerComponent("synccomponent", WXComponentSyncTest.class);
        WXSDKEngine.registerComponent(WXParallax.PARALLAX, WXParallax.class);
        WXSDKEngine.registerComponent("richtext", RichText.class);

        WXSDKEngine.registerModule("render", RenderModule.class);
        WXSDKEngine.registerModule("event", WXEventModule.class);
        WXSDKEngine.registerModule("syncTest", SyncTestModule.class);

        WXSDKEngine.registerComponent("mask", WXMask.class);

        WXSDKEngine.registerModule("myModule", MyModule.class);
        WXSDKEngine.registerModule("geolocation", GeolocationModule.class);
        WXSDKEngine.registerModule("titleBar", WXTitleBar.class);
        WXSDKEngine.registerModule("wsonTest", WXWsonTestModule.class);
        WXSDKEngine.registerModule("navigator", LocaleNavigatorModule.class);
        WXSDKEngine.registerModule("native", NativeModule.class);

        if (BuildConfig.useBindingx) {
            // bindingx
            Class expressionBinding = Class.forName("com.alibaba.android.bindingx.plugin.weex.WXExpressionBindingModule");
            Class binding = Class.forName("com.alibaba.android.bindingx.plugin.weex.WXBindingXModule");
            WXSDKEngine.registerModule("expressionBinding", expressionBinding);
            WXSDKEngine.registerModule("binding", binding);
            WXSDKEngine.registerModule("bindingx", binding);
        }
        if (BuildConfig.useGCanvas) {
            // gcanvas
            Class gcanvasModule = Class.forName("com.taobao.gcanvas.bridges.weex.GCanvasWeexModule");
            Class gcanvasComponent = Class.forName("com.taobao.gcanvas.bridges.weex.WXGCanvasWeexComponent");
            WXSDKEngine.registerModule("gcanvas", gcanvasModule);
            WXSDKEngine.registerComponent("gcanvas", gcanvasComponent);
        }
        if (BuildConfig.useLottie) {
            // lottie
            Class lottieComponent = Class.forName("com.alibaba.weex.extend.component.WXLottie");
            WXSDKEngine.registerComponent("lottie", lottieComponent);
        }
    }



    abstract boolean isDebug();
}
