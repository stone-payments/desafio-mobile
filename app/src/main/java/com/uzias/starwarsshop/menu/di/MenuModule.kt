package com.uzias.starwarsshop.menu.di

import com.uzias.starwarsshop.core.presentation.PlaceholderViewsManager
import com.uzias.starwarsshop.menu.presentation.navigation.MenuNavigation
import com.uzias.starwarsshop.menu.presentation.navigation.MenuNavigationImpl
import com.uzias.starwarsshop.menu.presentation.presenter.MenuPresenter
import com.uzias.starwarsshop.menu.presentation.presenter.MenuPresenterImpl
import com.uzias.starwarsshop.menu.presentation.view.MenuActivity
import com.uzias.starwarsshop.menu.presentation.view.MenuView
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.activity_menu.*

@Module
class MenuModule {

    @Provides
    fun providesMenuPresenter(navigation: MenuNavigation, view: MenuView): MenuPresenter
            = MenuPresenterImpl(navigation = navigation, view = view)

    @Provides
    fun providesPlaceHolder(activity: MenuActivity): PlaceholderViewsManager =
            PlaceholderViewsManager(loadingViewStub = activity.content_layout,
                errorViewStub = activity.content_layout,
                emptyViewStub = activity.content_layout,
                containerView = activity.content_layout)

    @Provides
    fun providesMenuNavigation(activity: MenuActivity): MenuNavigation
            = MenuNavigationImpl(activity.supportFragmentManager)

    @Provides
    fun providesMenuView(activity: MenuActivity): MenuView = activity

}