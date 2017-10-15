package jb.project.starwarsshoppinglist.home.presenter

import jb.project.starwarsshoppinglist.home.activity.view.HomeView
import jb.project.starwarsshoppinglist.productList.activity.view.ProductListView

/**
 * Created by Jb on 12/10/2017.
 */

interface HomePresenter
{
    fun init(homeView: HomeView)
}