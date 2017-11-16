package douglasspgyn.com.github.desafiostone.common.extensions

import android.support.v4.widget.SwipeRefreshLayout

/**
 * Created by Douglas on 12/11/17.
 */

fun SwipeRefreshLayout.show() {
    this.isRefreshing = true
}

fun SwipeRefreshLayout.hide() {
    this.isRefreshing = false
}