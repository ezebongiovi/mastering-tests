package com.edipasquale.tdd_room.dagger

import android.content.Context
import androidx.room.Room
import com.edipasquale.tdd_room.RoomApplication
import com.edipasquale.tdd_room.database.ImageDatabase
import com.edipasquale.tdd_room.repository.ImageRepository
import com.edipasquale.tdd_room.source.impl.RoomImageSource
import com.edipasquale.tdd_room.source.impl.AsyncTaskImageSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(private val app: RoomApplication) {

    @Provides
    fun provideLocalSource(database: ImageDatabase, context: Context) =
        RoomImageSource(database.getImageDao(), context)

    @Provides
    fun provideNetworkSource() = AsyncTaskImageSource()

    @Provides
    fun provideImageRepository(localSource: RoomImageSource, source: AsyncTaskImageSource) =
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