package com.shutterfly.pixabaygallery.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shutterfly.pixabaygallery.core.app.App
import com.shutterfly.pixabaygallery.core.constants.Constants.PIXABAY_DATABASE
import com.shutterfly.pixabaygallery.models.database_entities.FavoriteGalleryItem

@Database(entities = [FavoriteGalleryItem::class], version = 1, exportSchema = false)
abstract class PixabayDatabase : RoomDatabase() {


    abstract fun pixabayDao () : PixabayDao

    companion object {

        private var instance : PixabayDatabase? = null

        @Synchronized
        fun getInstance(): PixabayDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(App.applicationContext(), PixabayDatabase::class.java, PIXABAY_DATABASE)
                    .fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }

}