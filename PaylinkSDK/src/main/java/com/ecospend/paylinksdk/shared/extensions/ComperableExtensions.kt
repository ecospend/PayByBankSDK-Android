package com.ecospend.paylinksdk.shared.extensions

infix fun <T : Comparable<T>> T?.isGreaterThan(other: T?): Boolean =
    if (this != null && other != null) this > other else false

infix fun <T : Comparable<T>> T?.isGreaterThanOrEqual(other: T?): Boolean =
    if (this != null && other != null) this >= other else false

infix fun <T : Comparable<T>> T?.isLessThan(other: T?): Boolean =
    if (this != null && other != null) this < other else false

infix fun <T : Comparable<T>> T?.isLessThanOrEqual(other: T?): Boolean =
    if (this != null && other != null) this <= other else false
