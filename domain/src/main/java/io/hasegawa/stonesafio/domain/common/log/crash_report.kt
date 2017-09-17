package io.hasegawa.stonesafio.domain.common.log

interface CrashReportDevice {
    fun report(throwable: Throwable)
}

fun crashReport(throwable: Throwable?) {
    throwable?.let { crashReportDeviceInstance?.report(it) }
    loge(throwable) { "CrashReport" }
}

private var crashReportDeviceInstance: CrashReportDevice? = null

fun CrashReportDevice.setAsDefaultForCrashReport() {
    crashReportDeviceInstance = this
}
