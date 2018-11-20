package top.flyma.easy.weex

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alibaba.weex.CaptureActivity
import com.taobao.weex.WXSDKEngine

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
            var intent: Intent = Intent(this, CaptureActivity::class.java)
            startActivity(intent)
//            var intent = IntentIntegrator(this)
//            intent.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.QR_CODE_MODE)
//            intent.initiateScan()
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        var result = IntentIntegrator.parseActivityResult(requestCode, resultCode , data)
//        if (result != null) {
//            if(result.getContents() == null) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show()
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }
}
