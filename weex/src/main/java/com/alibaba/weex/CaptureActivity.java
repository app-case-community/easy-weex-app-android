package com.alibaba.weex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.zxing.Result;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import top.flyma.easy.weex.R;

import java.io.File;

public class CaptureActivity extends WXBaseActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.container);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String code = rawResult.getText();
        if (!TextUtils.isEmpty(code)) {
            Uri uri = Uri.parse(code);
            if (uri.getPath().contains("dynamic/replace")) {
                Intent intent = new Intent("weex.intent.action.dynamic", uri);
                intent.addCategory("weex.intent.category.dynamic");
                startActivity(intent);
                finish();
            } else if (uri.getQueryParameterNames().contains("_wx_devtool")) {
                WXEnvironment.sRemoteDebugProxyUrl = uri.getQueryParameter("_wx_devtool");
                WXEnvironment.sDebugServerConnectable = true;
                WXSDKEngine.reload();
                finish();
                return;
            } else {
                Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CaptureActivity.this, WXPageActivity.class);
                intent.setData(Uri.parse(code));
                startActivity(intent);
            }
        }
        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mScannerView.resumeCameraPreview(CaptureActivity.this);
//            }
//        }, 2000);
    }
}
