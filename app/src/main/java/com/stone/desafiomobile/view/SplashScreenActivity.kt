package com.stone.desafiomobile.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.stone.desafiomobile.R
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * Activity com a splash screen
 */
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        /**
         * espera um tempo para ir para a proxima activity
         */
        launch(CommonPool) {
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            delay(500)
            startActivity(intent)
            finish()
        }
    }
}
