package top.flyma.easy.weex

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alibaba.weex.CaptureActivity
import com.taobao.weex.WXSDKEngine
import android.R.attr.data
import android.app.Activity
import android.net.Uri
import android.provider.Settings
import com.taobao.weex.utils.WXLogUtils
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        WXSDKEngine.reload()
    }

    fun onClick(view: View) {
        if (view.id == R.id.btnScan) {
            AndPermission.with(this)
                .runtime()
                .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                .onGranted {
                    var intent: Intent = Intent(this@MainActivity, CaptureActivity::class.java)
                    startActivity(intent)
                }
                .onDenied {
                    val packageURI = Uri.parse("package:$packageName")
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    Toast.makeText(this@MainActivity, "没有权限无法扫描呦", Toast.LENGTH_LONG).show()
                }.start()
        }
    }

}
