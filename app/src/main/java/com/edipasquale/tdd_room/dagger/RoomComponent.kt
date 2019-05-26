package com.edipasquale.tdd_room.dagger

import com.edipasquale.tdd_room.view.fragment.DownloadFragment
import com.edipasquale.tdd_room.view.fragment.MyLibraryFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class])
interface RoomComponent {
    fun inject(entity: DownloadFragment)

    fun inject(entity: MyLibraryFragment)
}