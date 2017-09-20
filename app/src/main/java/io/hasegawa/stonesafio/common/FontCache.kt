package io.hasegawa.stonesafio.common

import android.content.Context
import android.graphics.Typeface
import android.os.SystemClock
import android.widget.TextView
import io.hasegawa.stonesafio.domain.common.log.logd
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

object FontCache {

    private val FONTS_DIR = "fonts/"

    private val CACHE = mutableMapOf<String, Typeface?>()

    fun brandFont(context: Context, textView: TextView) {
        applyTypefaceAsync(context, textView, "Righteous-Regular.ttf")
    }

    fun applyTypefaceAsync(context: Context, textView: TextView, typefaceName: String) {
        val weakRef = WeakReference(textView)
        Observable
                .fromCallable {
                    synchronized(CACHE) {
                        get(context, typefaceName)
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it?.let { typeface -> weakRef.get()?.typeface = typeface }
                }
    }

    private fun get(context: Context, name: String): Typeface? {
        val fileName = FONTS_DIR + name
        return CACHE.getOrPut(fileName) {
            try {
                val start = SystemClock.elapsedRealtime()
                val font = Typeface.createFromAsset(context.assets, fileName)
                val end = SystemClock.elapsedRealtime()
                logd { "Loaded font [$name] in ${end - start}ms." }

                font
            } catch (e: Exception) {
                null
            }
        }
    }
}
