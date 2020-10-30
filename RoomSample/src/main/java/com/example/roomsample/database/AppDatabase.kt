package com.example.roomsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomsample.dao.BookDao
import com.example.roomsample.dao.UserDao
import com.example.roomsample.data.*
import com.example.roomsample.data.TableValues.LIBRARY_LIBRARYID
import com.example.roomsample.data.TableValues.LIBRARY_USEROWNERID
import com.example.roomsample.data.TableValues.USER_AGE
import com.example.roomsample.data.TableValues.USER_DESC
import com.example.roomsample.data.TableValues.USER_FIRST_NAME
import com.example.roomsample.data.TableValues.USER_IDNUMBER
import com.example.roomsample.data.TableValues.USER_LAST_NAME
import com.example.roomsample.data.TableValues.USER_REGION
import com.example.roomsample.data.TableValues.USER_USERID


/**
 * @author: zeting
 * @date: 2020/9/14
 *
 */
//
@Database(entities = [Book::class, User::class, Library::class, Playlist::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun BookDao(): BookDao

    companion object {

        const val DATABASE_NAME = "Room_Sample.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                        .build()


        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS ${TableValues.TABLE_USER} ( " +
                        "$USER_USERID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  $USER_LAST_NAME TEXT ," +
                        " $USER_FIRST_NAME TEXT, $USER_AGE TEXT , $USER_IDNUMBER  TEXT, $USER_REGION  TEXT  )")
            }
        }
        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS ${TableValues.TABLE_LIBRARY} (  $LIBRARY_LIBRARYID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  $LIBRARY_USEROWNERID TEXT  )")
            }
        }
    }
}