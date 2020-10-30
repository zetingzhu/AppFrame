package com.example.roomsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.roomsample.data.Book
import com.example.roomsample.data.User
import com.example.roomsample.database.AppDatabase
//import androidx.room.Room
//import com.example.roomsample.data.User
//import com.example.roomsample.database.AppDatabase
import com.zzt.logutil.CustomPrinterForGetBlockInfo
import kotlinx.android.synthetic.main.activity_main_room.*

class RoomMainActivity : AppCompatActivity() {

    companion object {
        const val USER_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_room)

        var button1 = Button(this)
        button1.setText("添加数据1")
        button1.setOnClickListener {
            val database = AppDatabase.getInstance(this@RoomMainActivity)
            for (i in 0..10) {
                val user1 = User(USER_ID, "z", "zt$i")
                database.userDao().insertAll(user1)
            }
        }
        ll_add_layout.addView(button1)

        var button2 = Button(this)
        button2.setText("添加数据2")
        button2.setOnClickListener {
            Thread(Runnable {
                val database = AppDatabase.getInstance(this@RoomMainActivity)
                val book = Book(2, "z")
                database.BookDao().insertBook(book)
            }).start()
        }
        ll_add_layout.addView(button2)
    }

}
