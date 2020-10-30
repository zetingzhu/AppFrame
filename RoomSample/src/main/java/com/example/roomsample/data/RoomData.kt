package com.example.roomsample.data

import android.graphics.Bitmap
import androidx.room.*

/**
 * @author: zeting
 * @date: 2020/9/14
 *
 */


open class Base {
    var desc: String? = ""
}


/**
 * @Entity(tableName = "users")
 * 修改表名
 * SQLite 中的表名称不区分大小写。
 */
/**
 * 如果实体继承了父实体的字段，则使用 @Entity 属性的 ignoredColumns 属性来去掉类中的属性不创建在表中
 */
@Entity(
//        ignoredColumns = arrayOf("desc"),
        /**
         * 为实体添加索引，请在 @Entity 注释中添加 indices 属性
         * 数据库中的某些字段或字段组必须是唯一的。您可以通过将 @Index 注释的 unique 属性设为 true，强制实施此唯一性属性
         */
//        indices = arrayOf(Index(value = ["first_name", "last_name"], unique = true)   )
)
data class User(
        /**
         * autoGenerate = true
         * 代表主键自动生成
         */
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "_id")
        var userId: Int,
        /**
         * 请将 @ColumnInfo 注释添加到字段 表示修改列名
         */
        @ColumnInfo(name = "first_name") var firstName: String?,
        @ColumnInfo(name = "last_name") var lastName: String?,

        var age: Int = 0,

        var idNumber: String? = "",
        /**
         *   @Embedded 注释 ,可以 将另外一个 对象字段添加到当前对象表中
         */
//        @Embedded var address: Address? = null,

        /**
         * 使用 @Ignore 为这些字段添加注释 ,表示实体中的字段不想保留到表中
         */
//        @Ignore var picture: Bitmap? = null,

        // 区域
        var region: String? = ""

)
//        : Base() {
////    constructor(userId: Int, firstName: String?, lastName: String?) : this(userId, firstName, lastName, 0, "",   null, "")
//}

data class Address(
        val street: String?,
        val state: String?,
        val city: String?,
        @ColumnInfo(name = "post_code") val postCode: Int
)

@Entity
data class Library(
        @PrimaryKey val libraryId: Long,
        val userOwnerId: Long
)

/**
 * 如需查询用户列表和对应的库，您必须先在两个实体之间建立一对一关系。为此，请创建一个新的数据类，
 * 其中每个实例都包含父实体的一个实例和与之对应的子实体实例。将 @Relation 注释添加到子实体的实例，
 * 同时将 parentColumn 设置为父实体主键列的名称，并将 entityColumn 设置为引用父实体主键的子实体列的名称。
 *
 * 最后，向 DAO 类添加一个方法，用于返回将父实体与子实体配对的数据类的所有实例。该方法需要 Room 运行两次查询，
 * 因此应向该方法添加 @Transaction 注释，以确保整个操作以原子方式执行。
 */
data class UserAndLibrary(
        @Embedded val user: User,
        @Relation(
                parentColumn = "_id",
                entityColumn = "userOwnerId"
        )
        val library: Library
)


@Entity
data class Playlist(
        @PrimaryKey val playlistId: Long,
        val userCreatorId: Long,
        val playlistName: String
)

/**
 * 为了查询用户列表和对应的播放列表，您必须先在两个实体之间建立一对多关系。为此，请创建一个新的数据类，
 * 其中每个实例都包含父实体的一个实例和与之对应的所有子实体实例的列表。将 @Relation 注释添加到子实体的实例，
 * 同时将 parentColumn 设置为父实体主键列的名称，并将 entityColumn 设置为引用父实体主键的子实体列的名称。
 */
data class UserWithPlaylists(
        @Embedded val user: User,
        @Relation(
                parentColumn = "_id",
                entityColumn = "userCreatorId"
        )
        val playlists: List<Playlist>
)

/**
 * 大多数情况下，您只需获取实体的几个字段。例如，您的界面可能仅显示用户的名字和姓氏，而不是用户的每一条详细信息。
 * 通过仅提取应用界面中显示的列，您可以节省宝贵的资源，并且您的查询也能更快完成。
 * 借助 Room，您可以从查询中返回任何基于 Java 的对象，前提是结果列集合会映射到返回的对象。
 * 例如，您可以创建以下基于 Java 的普通对象 (POJO) 来获取用户的名字和姓氏：
 */
data class NameTuple(
        @ColumnInfo(name = "first_name") val firstName: String?,
        @ColumnInfo(name = "last_name") val lastName: String?
)

