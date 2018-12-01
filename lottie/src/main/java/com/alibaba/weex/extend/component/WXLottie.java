package com.alibaba.weex.extend.component;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import com.airbnb.lottie.LottieAnimationView;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.adapter.IWXHttpAdapter;
import com.taobao.weex.adapter.URIAdapter;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXLogUtils;
import com.taobao.weex.utils.WXUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WXLottie extends WXComponent<LottieAnimationView> {

    private static final String TAG = "WXLottie";

    private static final String PROP_LOOP = "loop";

    private static final String EMIT_EVENT_LOADED = "loaded";
    private boolean isLoaded = false;

    private boolean isLoop = false;

    private String jsonSrc = null;

    public WXLottie(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }

    @Override
    protected LottieAnimationView initComponentHostView(Context context) {
        LottieAnimationView view = new LottieAnimationView(context);
        return view;
    }

    @Override
    protected boolean setProperty(String key, Object param) {
        switch (key) {
            case Constants.Name.SRC:
                this.jsonSrc = WXUtils.getString(param, "");
                setJsonSrc(this.jsonSrc);
                break;
        }
        return super.setProperty(key, param);
    }


    @WXComponentProp(name = "src")
    private void setJsonSrc(String jsonSrc) {
        WXLogUtils.e(TAG, "set src:" + jsonSrc);
        if (!TextUtils.isEmpty(jsonSrc)) {
            IWXHttpAdapter adapter = WXSDKEngine.getIWXHttpAdapter();
            Uri rewrited = this.getInstance().rewriteUri(Uri.parse(jsonSrc), URIAdapter.BUNDLE);

            if (Constants.Scheme.LOCAL.equals(rewrited.getScheme())) {
                if (this.getHostView() != null) {
                    String assetName = rewrited.getAuthority() + rewrited.getPath();
                    this.getHostView().setAnimation(assetName);
                }
            } else if (Constants.Scheme.FILE.equals(rewrited.getScheme())) {
                loadLocalLottieJson(rewrited);
            } else if (Constants.Scheme.HTTP.equals(rewrited.getScheme()) || Constants.Scheme.HTTPS.equals(rewrited.getScheme())) {
                if (this.getHostView() != null) {
                    this.getHostView().setAnimationFromUrl(jsonSrc);
                }
            }
            this.fireEvent(EMIT_EVENT_LOADED);
        }
    }

    @WXComponentProp(name = "loop")
    public void setJsonLoop(boolean looped) {
        WXLogUtils.e(TAG, "isLooped:" + looped);
        if (this.getHostView() != null) {
            this.getHostView().loop(looped);
        }
    }

    @WXComponentProp(name = "speed")
    public void setSpeed(float speed) {
        WXLogUtils.e(TAG, "set speed:" + speed);
        if (this.getHostView() != null) {
            this.getHostView().setSpeed(speed);
        }
    }

    @WXComponentProp(name = "progress")
    public void setProgress(float progress) {
        WXLogUtils.e(TAG, "set progress:" + progress);
        if (this.getHostView() != null) {
            this.getHostView().setProgress(progress);
        }
    }

    @JSMethod
    public void play() {
        if (this.getHostView() != null && ViewCompat.isAttachedToWindow(this.getHostView())) {
            this.getHostView().playAnimation();
        }
    }

    @JSMethod
    public void pause() {
        if (this.getHostView() != null && ViewCompat.isAttachedToWindow(this.getHostView())) {
            this.getHostView().pauseAnimation();
        }
    }

    @JSMethod
    public void reset() {
        if (this.getHostView() != null && ViewCompat.isAttachedToWindow(this.getHostView())) {
            this.getHostView().cancelAnimation();
            this.getHostView().setProgress(0);
        }
    }

    @Override
    public void addEvent(String type) {
        if (EMIT_EVENT_LOADED.equals(type)) {
            isLoaded = true;
        }
    }

    @Override
    public void removeEvent(String type) {
        if (EMIT_EVENT_LOADED.equals(type)) {
            isLoaded = false;
        }
    }

    private void loadLocalLottieJson(Uri uri) {
        if (uri != null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                try {
                    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + uri.getPath();
                    FileInputStream inputStream = new FileInputStream(filePath);
                    byte[] buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);
                    JSONObject object = new JSONObject(new String(buffer));
                    inputStream.close();
                    if (this.getHostView() != null) {
                        this.getHostView().setAnimationFromJson(object.toString(), filePath);
                    }
                } catch (FileNotFoundException e) {
                    WXLogUtils.e(TAG, e.toString());
                } catch (IOException e) {
                    WXLogUtils.e(TAG, e.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
