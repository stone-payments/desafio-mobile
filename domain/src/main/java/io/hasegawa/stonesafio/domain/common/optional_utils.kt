package io.hasegawa.stonesafio.domain.common

import com.google.common.base.Optional


fun <T> T?.toOptional() = Optional.fromNullable(this)

fun <T> Optional<T>.toKtType(): T? = this.get()