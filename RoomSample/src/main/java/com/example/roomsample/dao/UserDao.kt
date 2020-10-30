package com.example.roomsample.dao

import android.database.Cursor
import androidx.room.*
import com.example.roomsample.data.*

/**
 * @author: zeting
 * @date: 2020/9/14
 *
 */
@Dao
interface UserDao {
//    @Query("SELECT * FROM user")
//    fun getAll(): List<User>
//
//    @Query("SELECT * FROM user WHERE _id IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User

//    @Query("SELECT * FROM user WHERE age > :minAge")
//    fun loadAllUsersOlderThan(minAge: Int): Array<User>

    @Insert
    fun insertAll(vararg users: User?)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertUsers(vararg users: User)
//
//    @Insert
//    fun insertBothUsers(user1: User, user2: User)
//
//    @Insert
//    fun insertUsersAndFriends(user: User, friends: List<User>)
//
//    @Update
//    fun updateUsers(vararg users: User)
//
//    @Delete
//    fun deleteUsers(vararg users: User)
//
//    @Delete
//    fun delete(user: User)
//
//
//    /**
//     *  * 最后，向 DAO 类添加一个方法，用于返回将父实体与子实体配对的数据类的所有实例。该方法需要 Room 运行两次查询，
//     * 因此应向该方法添加 @Transaction 注释，以确保整个操作以原子方式执行。
//     */
//    @Transaction
//    @Query("SELECT * FROM User")
//    fun getUsersAndLibraries(): List<UserAndLibrary>
//
//
//    /**
//     * 最后，向 DAO 类添加一个方法，用于返回将父实体与子实体配对的数据类的所有实例。该方法需要 Room 运行两次查询，
//     * 因此应向该方法添加 @Transaction 注释，以确保整个操作以原子方式执行。
//     */
//    @Transaction
//    @Query("SELECT * FROM User")
//    fun getUsersWithPlaylists(): List<UserWithPlaylists>
//
//    @Query("SELECT first_name, last_name FROM user")
//    fun loadFullName(): List<NameTuple>
//
//    @Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
//    fun loadUsersFromRegions(regions: List<String>): List<NameTuple>
//
//    @Query("SELECT * FROM user WHERE age > :minAge LIMIT 5")
//    fun loadRawUsersOlderThan(minAge: Int): Cursor
//
//    @Query(
//            "SELECT * FROM book " +
//                    "INNER JOIN loan ON loan.book_id = book.id " +
//                    "INNER JOIN user ON user.id = loan.user_id " +
//                    "WHERE user.name LIKE :userName"
//    )
//    fun findBooksBorrowedByNameSync(userName: String): List<Book>
}