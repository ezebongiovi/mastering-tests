package com.edipasquale.tdd_room

import android.app.Application
import com.edipasquale.tdd_room.dagger.DaggerRoomComponent
import com.edipasquale.tdd_room.dagger.RoomComponent
import com.edipasquale.tdd_room.dagger.RoomModule

class RoomApplication : Application() {

    private lateinit var roomComponent: RoomComponent

    override fun onCreate() {
        super.onCreate()

        roomComponent = DaggerRoomComponent.builder()
            .roomModule(RoomModule(this))
            .build()
    }

    fun getRoomComponent() = roomComponent
}