package io.hasegawa.stonesafio.common

import android.support.v7.widget.RecyclerView
import android.view.View
import java.lang.ref.WeakReference
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/*
 * Implementation based on KotterKnife, with support for anything extending KotterKnife, not only
 * pre-defined Android components.
 */

interface KotterKnife {
    companion object {
        fun bind(instance: KotterKnife, view: View) {
            LazyRegistry.reset(instance)
            instance.kotterView = WeakReference(view)
        }

        fun unbind(instance: KotterKnife) {
            LazyRegistry.reset(instance)
            instance.kotterView?.clear()
            instance.kotterView = null
        }
    }

    var kotterView: WeakReference<View>?
}

fun <V : View> RecyclerView.ViewHolder.bindView(id: Int): ReadOnlyProperty<RecyclerView.ViewHolder, V> = required(id, viewFinder)

fun <V : View> KotterKnife.bindView(id: Int): ReadOnlyProperty<KotterKnife, V> = required(id, viewFinder)

fun <V : View> RecyclerView.ViewHolder.bindOptionalView(id: Int): ReadOnlyProperty<RecyclerView.ViewHolder, V?> = optional(id, viewFinder)

fun <V : View> KotterKnife.bindOptionalView(id: Int): ReadOnlyProperty<KotterKnife, V?> = optional(id, viewFinder)


private val RecyclerView.ViewHolder.viewFinder: RecyclerView.ViewHolder.(Int) -> View?
    get() = { itemView.findViewById(it) }

private val KotterKnife.viewFinder: KotterKnife.(Int) -> View?
    get() = { kotterView?.get()?.findViewById(it) }

private fun viewNotFound(id: Int, desc: KProperty<*>): Nothing =
        throw IllegalStateException("View ID $id for '${desc.name}' not found.")

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> required(id: Int, finder: T.(Int) -> View?)
        = Lazy { t: T, desc -> t.finder(id) as V? ?: viewNotFound(id, desc) }

@Suppress("UNCHECKED_CAST")
private fun <T, V : View> optional(id: Int, finder: T.(Int) -> View?)
        = Lazy { t: T, _/*desc*/ -> t.finder(id) as V? }

// Like Kotlin's lazy delegate but the initializer gets the target and metadata passed to it
private class Lazy<in T, out V>(private val initializer: (T, KProperty<*>) -> V) : ReadOnlyProperty<T, V> {
    private object EMPTY

    private var value: WeakReference<Any?> = WeakReference(EMPTY)

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (value.get() == EMPTY) {
            value = WeakReference(initializer(thisRef, property))
            LazyRegistry.register(thisRef as Any, this)
        }
        @Suppress("UNCHECKED_CAST")
        return value.get() as V
    }

    fun reset() {
        value = WeakReference(EMPTY)
    }
}

private object LazyRegistry {
    private val lazyMap = WeakHashMap<Any, MutableCollection<Lazy<*, *>>>()

    fun register(target: Any, lazy: Lazy<*, *>) {
        lazyMap.getOrPut(target, { Collections.newSetFromMap(WeakHashMap()) }).add(lazy)
    }

    fun reset(target: Any) {
        lazyMap[target]?.forEach { it.reset() }
    }
}