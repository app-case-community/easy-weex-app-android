package com.alibaba.weex;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.zxing.Result;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.utils.WXLogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

//
public class CaptureActivity extends com.yzq.zxinglibrary.android.CaptureActivity {
    @Override
    public void handleDecode(Result rawResult) {
        try {
            Class t = Class.forName("com.yzq.zxinglibrary.android.InactivityTimer");
            Class b = Class.forName("com.yzq.zxinglibrary.android.BeepManager");
            Field timer = getClass().getSuperclass().getDeclaredField("inactivityTimer");
            Field beepManager = getClass().getSuperclass().getDeclaredField("beepManager");
            timer.setAccessible(true);
            beepManager.setAccessible(true);
            t.getMethod("onActivity").invoke(timer.get(this));
            b.getMethod("playBeepSoundAndVibrate").invoke(beepManager.get(this));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        inactivityTimer.onActivity();
//        beepManager.playBeepSoundAndVibrate();
        String code = rawResult.getText();
        WXLogUtils.d("code:"+code);
        if (!TextUtils.isEmpty(code)) {
            Uri uri = Uri.parse(code);
            if (uri.getPath().contains("dynamic/replace")) {
                Intent intent = new Intent("weex.intent.action.dynamic", uri);
                intent.addCategory("weex.intent.category.dynamic");
                startActivity(intent);
                this.finish();
            } else if (uri.getQueryParameterNames().contains("_wx_devtool")) {
                WXEnvironment.sRemoteDebugProxyUrl = uri.getQueryParameter("_wx_devtool");
                WXEnvironment.sDebugServerConnectable = true;
                WXSDKEngine.reload();
                this.finish();
                return;
            } else {
                Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CaptureActivity.this, WXPageActivity.class);
                intent.setData(Uri.parse(code));
                startActivity(intent);
            }
        }
    }

}
