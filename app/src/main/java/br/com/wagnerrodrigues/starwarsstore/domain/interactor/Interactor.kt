package br.com.wagnerrodrigues.starwarsstore.domain.interactor

import org.greenrobot.eventbus.EventBus

abstract class Interactor {

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