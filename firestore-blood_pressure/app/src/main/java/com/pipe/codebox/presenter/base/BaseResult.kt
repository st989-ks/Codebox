package com.pipe.codebox.presenter.base

sealed class BaseResult<out T : Any, out U : Any, out Z : Any> {
    data class Success<T : Any>(val data: T) : BaseResult<T, Nothing, Nothing>()
    data class Errors<U : Any>(val error: U) : BaseResult<Nothing, U, Nothing>()
    data class Message<Z : Any>(val str: Z) : BaseResult<Nothing, Nothing, Z>()


}