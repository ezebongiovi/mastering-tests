package com.edipasquale.tdd_room.view.fragment

import java.util.*

interface IdlingResource {

    fun isIdling(): Boolean

    fun observeIdling(observer: Observer)
}