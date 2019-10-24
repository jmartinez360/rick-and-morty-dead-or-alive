package com.dev.rickandmortydeadoralive.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.dev.rickandmortydeadoralive.BuildConfig
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory
import com.facebook.imagepipeline.core.ImagePipelineFactory
import com.facebook.imagepipeline.request.ImageRequest
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object ShareImageUtils {

    fun shareImage(context: Context, imageUrl: String, characterName: String) {

        val imageBitmap = getBitmapFromUrl(context, imageUrl)

        imageBitmap?.let {
            val uri = getUriImageFromBitmap(imageBitmap, context)

            val shareIntent = Intent(Intent.ACTION_SEND)

            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check $characterName from Rick and Morty serie!!")
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            shareIntent.type = "image/png"
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(Intent.createChooser(shareIntent, "Share Rick and Morty character"))


        }
    }

    private fun getBitmapFromUrl(context: Context, url: String): Bitmap? {

        val uri = Uri.parse(url)
        val imageRequest = ImageRequest.fromUri(uri)

        val cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, context)
        if (ImagePipelineFactory.getInstance().mainFileCache.hasKey(cacheKey)) {
           val resource = ImagePipelineFactory.getInstance().mainFileCache.getResource(cacheKey)

            var data: ByteArray? = null
            try {
                data = resource.read()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return BitmapFactory.decodeByteArray(data, 0, data!!.size)
        }

        return null
    }

    private fun getUriImageFromBitmap(bmp: Bitmap, context: Context): Uri? {

        var bmpUri: Uri? = null

        try {
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "IMG_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.flush()
            out.close()
            bmpUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bmpUri
    }
}