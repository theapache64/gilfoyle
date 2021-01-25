package com.theapache64.gilfoyle.utils

import com.theapache64.gilfoyle.App
import java.io.File

object JarUtils {

    fun getJarDir(): String {

        val jarDir = File(
            App::class.java.protectionDomain.codeSource.location
                .toURI()
        ).parent

        if (jarDir.contains("/out/production") || jarDir.contains("/build/classes/")) {
            return ""
        }

        return "$jarDir/"
    }
}