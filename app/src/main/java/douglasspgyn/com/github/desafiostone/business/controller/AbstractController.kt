package douglasspgyn.com.github.desafiostone.business.controller

import douglasspgyn.com.github.desafiostone.application.API
import java.lang.reflect.ParameterizedType

/**
 * Created by Douglas on 12/11/17.
 */

abstract class AbstractController<out T> protected constructor() {

    private var beanClass: Class<T>? = null

    init {
        val genericSuperClass = javaClass.genericSuperclass

        if (genericSuperClass != null && genericSuperClass !is Class<*>) {
            this.beanClass = (genericSuperClass as ParameterizedType).actualTypeArguments[0] as Class<T>
        }
    }

    val service: T get() = API.provideService(beanClass!!)
}