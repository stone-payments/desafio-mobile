package io.hasegawa.stonesafio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import io.hasegawa.stonesafio.screen_listing.ListingController

class MainActivity : AppCompatActivity() {
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val containerFl = FrameLayout(this)
        setContentView(containerFl)

        router = Conductor.attachRouter(this, containerFl, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(ListingController()))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            return super.onBackPressed()
        }
    }
}
