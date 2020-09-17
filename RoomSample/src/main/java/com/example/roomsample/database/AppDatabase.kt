package com.example.roomsample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomsample.dao.UserDao
import com.example.roomsample.data.User

/**
 * @author: zeting
 * @date: 2020/9/14
 *
 */
@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}