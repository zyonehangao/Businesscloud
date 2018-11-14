package com.cloud.shangwu.businesscloud.utils

import java.io.File
import java.lang.Character.isSpace

object FileUtils {
    /**
     * Return the file by path.
     *
     * @param filePath The path of file.
     * @return the file
     */
    fun  getFileByPath(filePath: String): File? {
        return if (isSpace(filePath)) null else File(filePath)
    }

     fun  isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }
}