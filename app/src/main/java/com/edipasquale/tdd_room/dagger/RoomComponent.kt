package com.edipasquale.tdd_room.dagger

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class])
interface RoomComponent {
}