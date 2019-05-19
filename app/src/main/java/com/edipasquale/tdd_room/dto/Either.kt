package com.edipasquale.tdd_room.dto

sealed class Either<out D, out E> {
    class Data<D>(val data: D): Either<D, Nothing>()
    class Error<E>(val error: E): Either<Nothing, E>()
}