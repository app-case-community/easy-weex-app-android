package top.flyma.easy.weex

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.alibaba.weex.WXPageActivity
import com.taobao.weex.WXSDKEngine
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_main.*
import top.flyma.easy.weex.demo.R
import top.flyma.easy.weex.utils.QrUtils


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
                    //                    var intent: Intent = Intent(this@MainActivity, CaptureActivity::class.java)
//                    startActivity(intent)
                    QrUtils.startQr(this@MainActivity)
                }
                .onDenied {
                    val packageURI = Uri.parse("package:$packageName")
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    Toast.makeText(this@MainActivity, "没有权限无法扫描呦", Toast.LENGTH_LONG).show()
                }.start()
        } else if (view.id == R.id.btnGo) run {
            if (TextUtils.isEmpty(editText.text)) {
                Toast.makeText(this, "请输入地址", Toast.LENGTH_SHORT).show()
                return
            }
            var intent = Intent(this, WXPageActivity::class.java)
//            var url = "local://weex.com/pages/demo/js/index.js"
            var url = editText.text.toString()
            intent.putExtra("bundleUrl", url)
            startActivity(intent)
        }
    }

}
