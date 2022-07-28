package com.ecospend.paybybank.di.core

class DIComponent(
    private val scope: DIScope,
    private val injector: (argument: Any?) -> Any
) {
    private var instance: Any? = null

    fun instance(argument: Any?): Any {
        return when (scope) {
            DIScope.Transient -> injector(argument)
            DIScope.Singleton -> instance ?: injector(argument).also { instance = it }
        }
    }

    fun reset() {
        instance = null
    }
}
