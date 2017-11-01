package com.uzias.starwarsshop.util

interface ErrorPredicate {
    fun evaluate(error: Throwable): Boolean
}