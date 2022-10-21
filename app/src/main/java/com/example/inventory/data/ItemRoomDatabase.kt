package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemRoomDatabase : RoomDatabase() {
    abstract fun itemDao() : ItemDao

    companion object {
        @Volatile
        var INSTANCE : ItemRoomDatabase? = null

        fun getDatabase(context: Context) : ItemRoomDatabase {
            return INSTANCE ?: synchronized(this){
                val temp = Room
                    .databaseBuilder(context, ItemRoomDatabase::class.java, "itemDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = temp
                return temp
            }
        }
    }
}