package knf.tools.signaturektx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import knf.tools.signatures.getSignatures

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signatureInfo = getSignatures()
        findViewById<TextView>(R.id.signature).text = "Signature: ${signatureInfo?.signatures?.first()?.encoded}"
        findViewById<TextView>(R.id.hash).text = "Hash: ${signatureInfo?.signatures?.first()?.hashBase64}"
    }
}