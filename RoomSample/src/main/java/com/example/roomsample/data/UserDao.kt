package com.example.roomsample.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: zeting
 * @date: 2020/9/17
 *
 */
@Entity
data class Book(
        @PrimaryKey
        val book_id: Long,
        val bookName: String?
)