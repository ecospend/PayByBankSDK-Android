package com.ecospend.paylinksdk.shared.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String): T? = trying { fromJson<T>(json, object : TypeToken<T>() {}.type) }

inline fun <reified T> String.fromJson(): T? = Gson().fromJson<T>(this)
