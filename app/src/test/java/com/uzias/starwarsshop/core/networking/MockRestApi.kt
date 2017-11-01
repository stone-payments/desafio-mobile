package com.uzias.starwarsshop.core.networking

import com.uzias.starwarsshop.core.util.fileFromResource
import com.nhaarman.mockito_kotlin.mock
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import com.uzias.starwarsshop.core.application.StarWarsShopApplication
import com.uzias.starwarsshop.core.application.di.ApplicationComponentTest
import com.uzias.starwarsshop.core.application.di.DaggerApplicationComponent
import com.uzias.starwarsshop.core.application.di.DaggerApplicationComponentTest
import com.uzias.starwarsshop.core.networking.di.NetworkModuleTest
import org.junit.After
import org.junit.Before
import javax.inject.Inject
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
abstract class MockRestApi<T> {
    private lateinit var server: MockWebServer

    lateinit var file: String
    lateinit var testAppComponent: ApplicationComponentTest
    lateinit var endpoint: String
    abstract val resource: String

    @Before
    open fun setUp() {
        setupReadFile()
        setupServer()
        injectDependencies()
    }

    private fun setupReadFile() {
        file = fileFromResource(resource, javaClass)
    }

    private fun setupServer() {
        server = MockWebServer()
        server.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(file))
        server.start()
        endpoint = server.url("/").toString()
    }

    private fun injectDependencies() {
        testAppComponent = DaggerApplicationComponentTest
                .builder()
                .networkModuleUrl(endpoint)
                .build()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

}