package com.reddit.presentation.utils

import android.content.Context

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 20:09
 */
object ThemeUtils {
    @JvmStatic
    fun getColorFromAttr(context: Context, attr: Int): Int {
        val a = context.obtainStyledAttributes(intArrayOf(attr))
        try {
            return a.getColor(0, 0)
        } finally {
            a.recycle()
        }
    }
}