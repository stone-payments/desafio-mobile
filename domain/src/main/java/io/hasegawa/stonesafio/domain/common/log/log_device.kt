package io.hasegawa.stonesafio.domain.common.log

interface LogDevice {
    fun v(f: () -> String)
    fun v(e: Throwable, f: () -> String)
    fun d(f: () -> String)
    fun d(e: Throwable, f: () -> String)
    fun i(f: () -> String)
    fun i(e: Throwable, f: () -> String)
    fun e(f: () -> String)
    fun e(e: Throwable, f: () -> String)
}


fun logv(f: () -> String) { logDeviceInstance?.v(f) }
fun logd(f: () -> String) { logDeviceInstance?.d(f) }
fun logi(f: () -> String) { logDeviceInstance?.i(f) }
fun loge(f: () -> String) { logDeviceInstance?.e(f) }

fun logv(e: Throwable?, f: () -> String) { if (e != null) logDeviceInstance?.v(e, f) else logDeviceInstance?.v(f) }
fun logd(e: Throwable?, f: () -> String) { if (e != null) logDeviceInstance?.d(e, f) else logDeviceInstance?.d(f) }
fun logi(e: Throwable?, f: () -> String) { if (e != null) logDeviceInstance?.i(e, f) else logDeviceInstance?.i(f) }
fun loge(e: Throwable?, f: () -> String) { if (e != null) logDeviceInstance?.e(e, f) else logDeviceInstance?.e(f) }

private var logDeviceInstance: LogDevice? = null

fun LogDevice.setAsDefaultForLog() {
    logDeviceInstance = this
}