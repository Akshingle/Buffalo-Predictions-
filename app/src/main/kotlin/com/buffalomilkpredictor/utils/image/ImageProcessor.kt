package com.buffalomilkpredictor.utils.image

import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.content.Context
import timber.log.Timber
import java.io.File

class ImageProcessor(private val context: Context) {

    fun loadBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } catch (e: Exception) {
            Timber.e(e, "Error loading bitmap from URI")
            null
        }
    }

    fun loadBitmapFromFile(filePath: String): Bitmap? {
        return try {
            android.graphics.BitmapFactory.decodeFile(filePath)
        } catch (e: Exception) {
            Timber.e(e, "Error loading bitmap from file")
            null
        }
    }

    fun scaleBitmap(bitmap: Bitmap, maxWidth: Int = 1024, maxHeight: Int = 1024): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val scaleWidth = when {
            width > maxWidth -> maxWidth.toFloat() / width
            else -> 1f
        }

        val scaleHeight = when {
            height > maxHeight -> maxHeight.toFloat() / height
            else -> 1f
        }

        val scale = minOf(scaleWidth, scaleHeight)

        return if (scale == 1f) {
            bitmap
        } else {
            val matrix = Matrix()
            matrix.postScale(scale, scale)
            Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
        }
    }

    fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
        return if (degrees == 0) {
            bitmap
        } else {
            val matrix = Matrix()
            matrix.postRotate(degrees.toFloat())
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }

    fun cropBitmap(bitmap: Bitmap, x: Int, y: Int, width: Int, height: Int): Bitmap {
        return Bitmap.createBitmap(bitmap, x, y, width, height)
    }

    fun enhanceImage(bitmap: Bitmap): Bitmap {
        // Basic enhancement: increase contrast and saturation
        // For production, use more advanced image processing
        return bitmap
    }

    fun saveBitmapToFile(bitmap: Bitmap, filePath: String): Boolean {
        return try {
            val file = File(filePath)
            file.parentFile?.mkdirs()
            
            val outputStream = file.outputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
            outputStream.close()
            
            Timber.d("Bitmap saved to $filePath")
            true
        } catch (e: Exception) {
            Timber.e(e, "Error saving bitmap to file")
            false
        }
    }

    fun getImageDimensions(filePath: String): Pair<Int, Int>? {
        return try {
            val options = android.graphics.BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            android.graphics.BitmapFactory.decodeFile(filePath, options)
            Pair(options.outWidth, options.outHeight)
        } catch (e: Exception) {
            Timber.e(e, "Error getting image dimensions")
            null
        }
    }
}
