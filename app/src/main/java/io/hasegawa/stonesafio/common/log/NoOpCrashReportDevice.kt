package io.hasegawa.stonesafio.common.log

import io.hasegawa.stonesafio.domain.common.log.CrashReportDevice

class NoOpCrashReportDevice : CrashReportDevice {
    override fun report(throwable: Throwable) = Unit
}