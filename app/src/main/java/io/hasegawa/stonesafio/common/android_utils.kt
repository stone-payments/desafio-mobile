package io.hasegawa.stonesafio.common

import android.widget.ViewFlipper

/**
 * @param[viewId] The id of the view to be shown.
 * @param[preventInfiniteLoop] If true will check if the same view will is shown before the [viewId] is found.
 *
 * @return True if the [viewId] was found and is showing, false if an infinite loop was detected.
 */
fun ViewFlipper.showNextUntilViewId(viewId: Int, preventInfiniteLoop: Boolean = true): Boolean {
    val beforeAllViewId = this.currentView.id
    while (this.currentView.id != viewId) {
        this.showNext()

        if (preventInfiniteLoop && this.currentView.id == beforeAllViewId) {
            return false
        }
    }
    return true
}
