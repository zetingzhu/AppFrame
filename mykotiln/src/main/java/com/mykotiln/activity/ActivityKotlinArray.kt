package com.mykotiln.activity

import android.os.Bundle
import android.util.Log
import com.mykotiln.R
import com.mykotiln.util.MyRecycleListItem
import com.mykotiln.util.MyRecycleOnClick
import com.mykotiln.util.MyRecyclerViewKotlin
import kotlinx.android.synthetic.main.activity_kotlin_array.*

class ActivityKotlinArray : BaseActivity() {
    companion object {
        val TAG = ActivityKotlinArray::class.java.simpleName
    }

    // 列表数据
    var mList: MutableList<MyRecycleListItem>? = null
    var myROC: MyRecycleOnClick? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_array)
        setItemKotlinClass(rv_kotlin_list)
    }

    fun setItemKotlinClass(rvList: MyRecyclerViewKotlin) {
        setItemOperateData();
        rvList.setMyAdapter(mList!!, myROC!!)
    }

    /**
     * 组成点击事件数据
     */
    fun setItemOperateData() {
        mList = ArrayList<MyRecycleListItem>() as MutableList<MyRecycleListItem>?
        mList!!.add(MyRecycleListItem(0, "Kotlin Array 使用 "))
        myROC = object : MyRecycleOnClick {
            override fun onClickListener(position: Int) {
                when (position) {
                    -1, 0 -> {
                        // 创建数组
                        val arr1 = arrayOf(1, 2, 3)
                        for (i in arr1) {
                            Log.d(TAG, "Array arr1 i:$i")
                        }

                        //基本类型数组:ByteArray,LongArray等这些类是基本类型数组，
                        // 但是跟Array类没有继承关系，但是它们有相同的属性集。
                        // Kotlin中的数组是不能型变得，也就是说Array<Int>不能赋值给Array<Any>
                        val intArr: IntArray = intArrayOf(1, 2, 3)
                        val longArr: LongArray = longArrayOf(1L, 2L, 3L)
                        val floatArr: FloatArray = floatArrayOf(1.0f, 2.0f, 3.0f)
                        val doubleArr: DoubleArray = doubleArrayOf(1.0, 2.02, 3.03333)
                        val booleanArr: BooleanArray = booleanArrayOf(false, true, false)

                        //创建指定长度的数组且无元素，相当于 java中的 int[] intArray = new int[6]
                        //arrayOfNulls<数据类型>(长度)，默认值都是null
                        val fixedSizeArr = arrayOfNulls<Int>(6)

                        //使用闭包进行初始化，
                        //参数1：数组大小，参数2：一个函数参数的工厂函数
                        //--->>1 -- 3
                        val arr2 = Array(3) { it -> it.inc() }
                        for (i in arr2) {
                            Log.d(TAG, "Array arr2 i:$i")
                        }
                        //--->>0 1 2 3 4 5 6 7 8 9
                        var a = Array(10) { i -> i }
                        for (i in a) {
                            Log.d(TAG, "---a:$i")//--->>0 1 2 3 4 5 6 7 8 9
                        }
                        //--->>1 -- 10
                        var b = Array(10) { i -> i + 1 }
                        for (i in b) {
                            Log.d(TAG, "---b:$i")
                        }

                        val c = arrayOfNulls<Int>(10)
                        for (add in 0 until 10) {
                            c[add] = add
                        }
                        for (i in c) {
                            Log.d(TAG, "---c:$i")
                        }

                        //创建空数组
                        val empty = emptyArray<Int>()

                        //访问数组的元素
                        val intArr1 = intArrayOf(1, 2, 3)
                        for (item in intArr1) {
                            Log.d(TAG, "Array intArr1 item: " + item)//遍历intArr里面的元素，item就是元素本身
                        }
                        for (index in intArr1.indices) {
                            Log.d(
                                TAG,
                                "Array intArr1 item kotlin: " + intArr[index]
                            )//遍历initArr索引的元素，从0开始
                            Log.d(
                                TAG,
                                "Array intArr1 item java: " + intArr.get(index)
                            )//可以通过get(索引)来获取元素
                        }

                        val intArray = IntArray(3) { a -> (10 + a) }
                        for (i in intArray) {
                            Log.d(TAG, "intArray:$i")
                        }
                        //基本类型的二维数组
                        val arr = Array(3) { intArray }//三个长度为3的Int数组的二维数组
                        for (one in arr) {
                            for (two in one) {
                                Log.d(TAG, "二维数组：" + two)
                            }
                        }


                        //三个长度为3的Demo类型的二维数组，自定义类型的话需要在大括号里面操作
                        val arrClass = Array(3) { Array<Demo>(3) { i: Int -> Demo(i) } }
                        for (demos in arrClass) {
                            for (demo in demos) {
                                Log.d(TAG, "二位数组对象：${demo.mNum}  ")
                            }
                        }

                        // 三维数组以及多维数组
                        //嫌弃  (ノ｀Д)ノ
                        val arrA = Array<Array<IntArray>>(3) { Array<IntArray>(3) { IntArray(3) } }
                        //正解
                        val arrB = Array(3) { Array(3) { IntArray(3) { b: Int -> (100 + b) } } }
                        for (one in arrB) {
                            for (two in one) {
                                for (three in two) {
                                    Log.d(TAG, "三位数组对象:" + three)
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    class Demo(num: Int) {
        var mNum: Int? = null

        init {
            this.mNum = num
        }
    }
}