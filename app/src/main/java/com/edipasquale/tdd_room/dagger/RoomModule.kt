package com.edipasquale.tdd_room.dagger

import android.content.Context
import androidx.room.Room
import com.edipasquale.tdd_room.RoomApplication
import com.edipasquale.tdd_room.database.ImageDatabase
import com.edipasquale.tdd_room.repository.ImageRepository
import com.edipasquale.tdd_room.source.impl.LocalImageSource
import com.edipasquale.tdd_room.source.impl.NetworkImageSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(private val app: RoomApplication) {

    @Provides
    fun provideLocalSource(database: ImageDatabase, context: Context) =
        LocalImageSource(database.getImageDao(), context)

    @Provides
    fun provideNetworkSource() = NetworkImageSource()

    @Provides
    fun provideImageRepository(localSource: LocalImageSource, source: NetworkImageSource) =
        ImageRepository(localSource, source)

    @Provides
    fun provideApplicationContext() = app.applicationContext

    @Provides
    @Singleton
    fun provideImageDatabase() = Room.databaseBuilder<ImageDatabase>(
        app,
        ImageDatabase::class.java, app.getDatabaseName()
    ).build()
}