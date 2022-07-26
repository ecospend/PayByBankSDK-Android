package com.ecospend.paylinksdk.di.core

import android.content.res.Resources

object EcoDi {

    var containerHolders = mutableListOf<DIContainerHolder>()

    inline fun <reified T : Any> provide(
        scope: DIScope = DIScope.Singleton,
        container: DIContainer = DIContainer.Shared,
        noinline injector: (argument: Any?) -> T,
    ) {
        val data = containerHolders.firstOrNull {
            it.container == container
        } ?: DIContainerHolder(container = container)
            .also { containerHolders.add(it) }

        data.components[T::class.java.simpleName] =
            DIComponent(
                scope = scope,
                injector = injector
            )
    }

    inline fun <reified T> inject(argument: Any? = null): T {
        containerHolders.forEach {
            it.components[T::class.java.simpleName]
                ?.let { component ->
                    return component.instance(argument) as T
                }
        }
        throw (
            Resources.NotFoundException(
                "${this::class.java.simpleName} ->" +
                    " ${T::class.java} not found. Please review how you provided this reference."
            )
            )
    }

    fun reset(container: DIContainer = DIContainer.Shared) {
        containerHolders
            .firstOrNull { it.container == container }
            ?.components
            ?.forEach { it.value.reset() }
    }

    fun clear() {
        containerHolders.clear()
    }
}
