package br.com.wagnerrodrigues.starwarsstore.presentation.presenter

import org.greenrobot.eventbus.EventBus

abstract class Presenter {

    open fun registerEvents(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    open fun unregisterEvents(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

}