package com.ecospend.paylinksdk.shared.model

abstract class UseCase<in Params, out Type : Any?> {

    abstract suspend operator fun invoke(params: Params): Type

    object None {
        override fun toString() = "UseCase.None"
    }
}
