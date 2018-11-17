package com.cloud.shangwu.businesscloud.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import android.support.annotation.IntRange



class ImageUtils {




    /**
     * Return the compressed bitmap using quality.
     *
     * @param src     The source of bitmap.
     * @param quality The quality.
     * @return the compressed bitmap
     */
    fun compressByQuality(src: Bitmap,
                          @IntRange(from = 0, to = 100) quality: Int): Bitmap? {
        return compressByQuality(src, quality, false)
    }
    /**
     * Return the compressed bitmap using quality.
     *
     * @param src     The source of bitmap.
     * @param quality The quality.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return the compressed bitmap
     */
    fun compressByQuality(src: Bitmap,
                          @IntRange(from = 0, to = 100) quality: Int,
                          recycle: Boolean): Bitmap? {
        if (isEmptyBitmap(src)) return null
        val baos = ByteArrayOutputStream()
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos)
        val bytes = baos.toByteArray()
        if (recycle && !src.isRecycled) src.recycle()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    private fun isEmptyBitmap(src: Bitmap?): Boolean {
        return src == null || src.width == 0 || src.height == 0
    }

}