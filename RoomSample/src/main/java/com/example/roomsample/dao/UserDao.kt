package com.example.roomsample.dao

import androidx.room.*
import com.example.roomsample.data.User
import com.example.roomsample.data.UserAndLibrary
import com.example.roomsample.data.UserWithPlaylists

/**
 * @author: zeting
 * @date: 2020/9/14
 *
 */
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    /**
     *  * 最后，向 DAO 类添加一个方法，用于返回将父实体与子实体配对的数据类的所有实例。该方法需要 Room 运行两次查询，
     * 因此应向该方法添加 @Transaction 注释，以确保整个操作以原子方式执行。
     */
    @Transaction
    @Query("SELECT * FROM User")
    fun getUsersAndLibraries(): List<UserAndLibrary>


    /**
     * 最后，向 DAO 类添加一个方法，用于返回将父实体与子实体配对的数据类的所有实例。该方法需要 Room 运行两次查询，
     * 因此应向该方法添加 @Transaction 注释，以确保整个操作以原子方式执行。
     */
    @Transaction
    @Query("SELECT * FROM User")
    fun getUsersWithPlaylists(): List<UserWithPlaylists>
}