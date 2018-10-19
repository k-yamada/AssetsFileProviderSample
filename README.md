# 概要

`assets`ディレクトリに配置したpdfファイルを開くためのサンプルプログラムです。


# プロジェクト作成手順

1. 以下の手順で、Assetsフォルダを作成します。
Android StudioのメニューバーのFile > New > Folder > Assets Folder
2. `assets/pdf/sample.pdf`に適当なpdfファイルを配置します
3. utilディレクトリに`AssetsFileProvider.kt`を作成します。


```AssetsFileProvider.kt
// AssetsFileProvider.kt

package kyamada.assetsfileprovidersample.util

import android.content.ContentProvider
import android.content.ContentValues
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.net.Uri
import java.io.FileNotFoundException
import java.io.IOException


class AssetsFileProvider : ContentProvider() {

    @Throws(FileNotFoundException::class)
    override fun openAssetFile(uri: Uri, mode: String): AssetFileDescriptor {
        try {
            return context!!.assets.openFd(uri.lastPathSegment)
        } catch (e: IOException) {
            e.printStackTrace()
            throw FileNotFoundException(e.message)
        }
    }

    override fun delete(uri: Uri, selection: String, selectionArgs: Array<String>): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun getType(uri: Uri): String {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues): Uri {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate(): Boolean {
        return false
    }

    override fun query(uri: Uri, projection: Array<String>, selection: String,
                       selectionArgs: Array<String>, sortOrder: String): Cursor {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun update(uri: Uri, values: ContentValues, selection: String,
                        selectionArgs: Array<String>): Int {
        throw UnsupportedOperationException("Not yet implemented")
    }

    companion object {
        private const val AUTHORITY = "kyamada.assetsfileprovidersample.assets"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY")!!
    }
}
```

4. AndroidManifest.xmlに`<provider>`タグを追加します。

```xml
// AndroidManifest.xml:

    <application
        ...

        <provider
                android:name="kyamada.assetsfileprovidersample.util.AssetsFileProvider"
                android:authorities="kyamada.assetsfileprovidersample.assets"
                android:exported="false"
                android:grantUriPermissions="true">
            <meta-data
                    android:name="android.support.FILE_PROVIDER_PATHS"
                    android:resource="@xml/filepaths" />
        </provider>
    </application>
```

5. `res/xml/filepaths.xml`を作成します。

```xml
// res/xml/filepaths.xml:

<?xml version="1.0" encoding="utf-8"?>
<paths>
    <asset
            name="pdf"
            path="pdf" />
</paths>
```

6. pdfを圧縮しないようにする設定を`app/build.gradle`に記述します。

```app/build.gradle
// app/build.gradle:

android {
    ...

    aaptOptions {
        noCompress "pdf"
    }
}
```

7. MainActivity.ktにpdfを開く処理を記述します。

```MainActivity.kt
// MainActivity.kt

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
```

8. アプリを起動するとPDFが表示されます。