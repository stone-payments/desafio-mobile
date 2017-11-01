package com.uzias.starwarsshop.core.util

import org.mockito.internal.verification.VerificationModeFactory
import org.mockito.verification.VerificationMode

object MockitoHelpers {
    fun oneTimeOnly(): VerificationMode = VerificationModeFactory.times(1)
}
