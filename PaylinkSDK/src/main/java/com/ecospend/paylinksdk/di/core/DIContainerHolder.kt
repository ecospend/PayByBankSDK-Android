package com.ecospend.paylinksdk.di.core

class DIContainerHolder(
    val container: DIContainer,
    val components: MutableMap<String, DIComponent> = mutableMapOf()
)
