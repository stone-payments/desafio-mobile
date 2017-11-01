package com.uzias.starwarsshop.core.domain

interface UseCase<out T> {
    fun run(): T
}