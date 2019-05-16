package top.flyma.easy.weex.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import com.alibaba.weex.WXPageActivity;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;

public class QrUtils {
    public static void startQr(final Context context) {
        int colorPrimaryId = context.getResources().getIdentifier("colorPrimary", "color", context.getPackageName());
        int colorPrimary = ContextCompat.getColor(context, colorPrimaryId);
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(识别二维码)")//扫描框下文字
                .setShowDes(false)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(Color.WHITE)//设置扫描框颜色
                .setLineColor(Color.WHITE)//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_QRCODE)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_QRCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setCustombarcodeformat(QrConfig.BARCODE_I25)//此项只有在扫码类型为TYPE_CUSTOM时才有效
                .setPlaySound(true)//是否扫描成功后bi~的声音
//                .setDingPath(R.raw.test)//设置提示音(不设置为默认的Ding~)
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫描二维码")//设置Tilte文字
                .setTitleBackgroudColor(colorPrimary)//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .create();
        QrManager.getInstance().init(qrConfig).startScan((Activity) context, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(String code) {
                Toast.makeText(context.getApplicationContext(), code, Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(code)) {
                    Uri uri = Uri.parse(code);
                    if (uri.getPath().contains("dynamic/replace")) {
                        Intent intent = new Intent("weex.intent.action.dynamic", uri);
                        intent.addCategory("weex.intent.category.dynamic");
                        context.startActivity(intent);
                    } else if (uri.getQueryParameterNames().contains("_wx_devtool")) {
//                        WXEnvironment.sRemoteDebugProxyUrl = uri.getQueryParameter("_wx_devtool");
//                        WXEnvironment.sDebugServerConnectable = true;
//                        WXSDKEngine.reload();
                        Toast.makeText(context, "不支持调试", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(context, WXPageActivity.class);
                        intent.setData(Uri.parse(code));
                        context.startActivity(intent);
                    }
                }
            }
        });
    }
}
