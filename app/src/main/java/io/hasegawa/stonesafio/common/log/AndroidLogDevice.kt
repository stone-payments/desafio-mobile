package io.hasegawa.stonesafio.common.log

import android.util.Log
import io.hasegawa.stonesafio.BuildConfig
import io.hasegawa.stonesafio.domain.common.log.LogDevice
import java.util.regex.Pattern

class AndroidLogDevice : LogDevice {
    companion object {
        private val CALL_STACK_INDEX = 3
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }

    override fun v(f: () -> String) = Unit.apply { Log.v(tag(), f.invoke()) }
    override fun v(e: Throwable, f: () -> String) = Unit.apply { Log.v(tag(), f(), e) }

    override fun d(f: () -> String) = when (BuildConfig.DEBUG) {
        true -> Unit.apply { Log.d(tag(), f.invoke()) }
        else -> Unit
    }

    override fun d(e: Throwable, f: () -> String) = when (BuildConfig.DEBUG) {
        true -> Unit.apply { Log.d(tag(), f(), e) }
        else -> Unit
    }

    override fun i(f: () -> String) = Unit.apply { Log.i(tag(), f.invoke()) }
    override fun i(e: Throwable, f: () -> String) = Unit.apply { Log.i(tag(), f(), e) }

    override fun e(f: () -> String) = Unit.apply { Log.e(tag(), f.invoke()) }
    override fun e(e: Throwable, f: () -> String) = Unit.apply { Log.e(tag(), f(), e) }

    private fun tag(): String {
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            throw IllegalStateException(
                    "Synthetic stacktrace didn't have enough elements: are you using proguard?")
        }
        val element = stackTrace[CALL_STACK_INDEX]
        return createStackElementTag(element)
    }


    private fun createStackElementTag(element: StackTraceElement): String {
        var tag = element.className
        val matcher = ANONYMOUS_CLASS.matcher(tag)
        if (matcher.find()) {
            tag = matcher.replaceAll("")
        }
        return tag.substring(tag.lastIndexOf('.') + 1)
    }
}

