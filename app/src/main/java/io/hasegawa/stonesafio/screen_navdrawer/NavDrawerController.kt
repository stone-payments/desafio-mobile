package io.hasegawa.stonesafio.screen_navdrawer

import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.jakewharton.rxbinding2.view.clicks
import io.hasegawa.stonesafio.R
import io.hasegawa.stonesafio.common.KotterKnife
import io.hasegawa.stonesafio.common.bindView
import io.hasegawa.stonesafio.screen_listing.ListingController
import io.hasegawa.stonesafio.screen_transactions.TransactionsController
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

class NavDrawerController : Controller(), KotterKnife {
    override var kotterView: WeakReference<View>? = null

    private val drawerLayout: DrawerLayout by bindView(R.id.navdrawer_drawerlayout)
    private val navView: NavigationView by bindView(R.id.navdrawer_navigation)
    private val mainContainerCl: CoordinatorLayout by bindView(R.id.navdrawer_main_container_cl)

    private var transactionDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.screen_navdrawer, container, false)
                .also { KotterKnife.bind(this, it) }
                .apply {
                    val childContentRouter = getChildRouter(mainContainerCl, null)
                    if (!childContentRouter.hasRootController()) {
                        val mainController = ListingController()
                        childContentRouter.pushController(RouterTransaction.with(mainController))
                    }

                    navView.menu.findItem(R.id.navdrawer_transactions_menu).clicks()
                            .doOnNext {
                                val top = childContentRouter.backstack.lastOrNull()?.controller()
                                when (top is TransactionsController) {
                                    true -> Unit
                                    else -> {
                                        val controller = TransactionsController()
                                        childContentRouter.pushController(RouterTransaction.with(controller))
                                        closeDrawer()
                                    }
                                }
                            }
                            .subscribe()
                            .also { transactionDisposable = it }
                }
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        transactionDisposable?.dispose()
        transactionDisposable = null
    }

    fun openDrawer() = drawerLayout.openDrawer(navView)

    fun closeDrawer() = drawerLayout.closeDrawer(navView)

    override fun handleBack(): Boolean =
            when (drawerLayout.isDrawerOpen(navView)) {
                true -> {
                    drawerLayout.closeDrawer(navView)
                    true
                }
                else -> super.handleBack()
            }

}