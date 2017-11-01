package com.uzias.starwarsshop.core.errors

class ContentNotFoundError : RuntimeException() {
    override val message: String?
        get() = "No content available"
}
