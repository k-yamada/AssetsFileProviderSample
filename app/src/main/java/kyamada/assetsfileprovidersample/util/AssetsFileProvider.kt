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
