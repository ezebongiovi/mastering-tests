package com.edipasquale.tdd_room.view.fragment

import androidx.fragment.app.Fragment
import java.util.*

/**
 * This is a class I made for making tests to wait for fragment's asynchronous actions.
 *
 */
abstract class IdlerFragment : Fragment(), IdlingResource {
    private var mIdling = object : Observable() {
        var idling: Boolean = true

        override fun notifyObservers(arg: Any?) {
            setChanged()
            idling = arg as Boolean
            super.notifyObservers(arg)
        }
    }

    protected fun setIdling(idling: Boolean) {
        mIdling.notifyObservers(idling)
    }

    override fun isIdling(): Boolean = mIdling.idling

    override fun observeIdling(observer: Observer) {
        mIdling.addObserver(observer)
    }
}