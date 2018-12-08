package com.alibaba.weex.extend.component;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.adapter.URIAdapter;
import com.taobao.weex.annotation.Component;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.action.GraphicSize;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXLogUtils;

import java.util.Map;

@Component(
        lazyload = false
)
public class WXWeb extends com.taobao.weex.ui.component.WXWeb {

    public WXWeb(WXSDKInstance instance, WXVContainer parent, boolean isLazy, BasicComponentData basicComponentData) {
        super(instance, parent, isLazy, basicComponentData);
    }

    @Override
    protected void createViewImpl() {
        super.createViewImpl();
        diableScrollbar();
    }

    @Override
    public void setUrl(String url) {
        if (TextUtils.isEmpty(url) || getHostView() == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            Uri rewrited = this.getInstance().rewriteUri(Uri.parse(url), URIAdapter.BUNDLE);
            if (Constants.Scheme.LOCAL.equals(rewrited.getScheme())) {
                String assetName = rewrited.getPath().substring(1);
                loadUrl("file:///android_asset/" + assetName);
            } else if (Constants.Scheme.HTTP.equals(rewrited.getScheme()) || Constants.Scheme.HTTPS.equals(rewrited.getScheme())) {
                loadUrl(getInstance().rewriteUri(Uri.parse(url), URIAdapter.WEB).toString());
            }
        }
    }

    private void loadUrl(String url) {
        this.mWebView.loadUrl(url);
    }

    private void diableScrollbar() {
        if (getHostView() != null) {
            FrameLayout frameLayout = (FrameLayout) getHostView();
            WebView webView = null;
            for (int i = 0; i < frameLayout.getChildCount(); i++) {
                View item = frameLayout.getChildAt(i);
                if (item instanceof  WebView) {
                    webView = (WebView) item;
                    break;
                }
            }
            if (webView != null) {
                webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
                webView.setHorizontalScrollBarEnabled(false);//水平不显示
                webView.setVerticalScrollBarEnabled(false); //垂直不显示
            }

        }
    }
}
