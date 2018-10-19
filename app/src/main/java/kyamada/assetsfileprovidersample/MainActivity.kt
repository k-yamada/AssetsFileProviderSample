package kyamada.assetsfileprovidersample

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kyamada.assetsfileprovidersample.util.AssetsFileProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openAssetPdf()
    }

    private fun openAssetPdf() {
        val intent = Intent(Intent.ACTION_VIEW)
        val uri: Uri = AssetsFileProvider.CONTENT_URI.buildUpon()
            .appendPath("pdf")
            .appendPath("sample.pdf")
            .build()
        intent.setDataAndType(uri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
    }
}
