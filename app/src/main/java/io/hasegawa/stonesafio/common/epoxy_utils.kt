package io.hasegawa.stonesafio.common

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.OnViewRecycled
import java.lang.ref.WeakReference


abstract class EpoxyModelWithHolderKt<T : EpoxyHolder>(val defaultLayoutId: Int, val id: String)
    : EpoxyModelWithHolder<T>() {

    init {
        id(id)
    }

    override final fun id(key: CharSequence): EpoxyModel<T> {
        return super.id(key)
    }

    override final fun id(id: Long): EpoxyModel<T> {
        return super.id(id)
    }

    override final fun id(key: CharSequence, id: Long): EpoxyModel<T> {
        return super.id(key, id)
    }

    override final fun id(): Long {
        return super.id()
    }

    override final fun id(id1: Long, id2: Long): EpoxyModel<T> {
        return super.id(id1, id2)
    }

    override final fun id(vararg ids: Number): EpoxyModel<T> {
        return super.id(*ids)
    }

    override fun getDefaultLayout() = defaultLayoutId
}

abstract class EpoxyKotterHolder : EpoxyHolder(), KotterKnife {
    final override var kotterView: WeakReference<View>? = null
    override final fun bindView(itemView: View?) {
        KotterKnife.bind(this, itemView!!)
        onViewBound(itemView)
    }

    open fun onViewBound(view: View) {}

    @OnViewRecycled
    fun unbindKotter() {
        KotterKnife.unbind(this)
    }
}
