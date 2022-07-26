package com.ecospend.paylinksdk.shared.extensions

import com.google.gson.Gson

inline fun <reified T> Gson.fromMap(map: Map<*, *>): T? = trying { fromJson(toJsonTree(map), T::class.java) }

inline fun <reified T> Map<*, *>.fromJson(): T? = Gson().fromMap(this)
