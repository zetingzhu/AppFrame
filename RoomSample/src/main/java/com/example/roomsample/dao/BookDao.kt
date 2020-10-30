package com.example.roomsample.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.roomsample.data.Book

/**
 * @author: zeting
 * @date: 2020/9/17
 *
 */

@Dao
interface BookDao {
    @Insert
    fun insertBook(book: Book)
}