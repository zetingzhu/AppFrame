package com.mykotiln.activity

import android.content.Context
import android.widget.Button
import com.mykotiln.R
import com.mykotiln.bean.DemoIn
import com.mykotiln.util.MyRecycleListItem
import com.mykotiln.util.MyRecycleOnClick
import com.mykotiln.util.MyRecyclerViewKotlin

/**
 * class persenter 类
 */
class KotilnUsePersenter : BasePersenter {

    // 列表数据
    var mList: MutableList<MyRecycleListItem>? = null
    var myROC: MyRecycleOnClick? = null

    constructor() : super()

    constructor(mContext: Context?) : super(mContext)

    /**
     *  设置kotlin语言的数据
     *  Int 参数，返回值 Int
     */
    fun setItemKotlinClass(rvList: MyRecyclerViewKotlin) {
        setItemOperateData();
        rvList.setMyAdapter(mList!!, myROC!!)
    }

    /**
     * 组成点击事件数据
     */
    fun setItemOperateData() {
        mList = ArrayList<MyRecycleListItem>() as MutableList<MyRecycleListItem>?
        mList!!.add(MyRecycleListItem(0, "Kotlin Lambda 表达式 "))
        mList!!.add(MyRecycleListItem(1, "Kotlin Lambda 单个参数的隐式名称 "))
        mList!!.add(MyRecycleListItem(2, "Kotlin Lambda 表达式 2 "))
        mList!!.add(MyRecycleListItem(3, "Kotlin Lambda 遍历集合 "))
        mList!!.add(MyRecycleListItem(4, "Kotlin Lambda 传入函数实现值 "))
        mList!!.add(MyRecycleListItem(5, "Kotlin Lambda TODO "))
        mList!!.add(MyRecycleListItem(6, "Kotlin Lambda run1 "))
        mList!!.add(MyRecycleListItem(7, "Kotlin Lambda run2 "))
        mList!!.add(MyRecycleListItem(8, "Kotlin Lambda run3 "))
        mList!!.add(MyRecycleListItem(9, "Kotlin Lambda with1 "))
        mList!!.add(MyRecycleListItem(10, "Kotlin Lambda T.apply "))
        mList!!.add(MyRecycleListItem(11, "Kotlin Lambda T.also "))
        mList!!.add(MyRecycleListItem(12, "Kotlin Lambda T.let "))
        mList!!.add(MyRecycleListItem(13, "Kotlin Lambda T.takeIf "))
        mList!!.add(MyRecycleListItem(14, "Kotlin Lambda T.takeUnless "))
        mList!!.add(MyRecycleListItem(15, "Kotlin Lambda repeat "))
        mList!!.add(MyRecycleListItem(16, "Kotlin Array component "))
        mList!!.add(MyRecycleListItem(17, "Kotlin Array reverse "))
        mList!!.add(MyRecycleListItem(18, "Kotlin List MutableList set MutableSet "))
        mList!!.add(MyRecycleListItem(19, "Kotlin Map "))
        mList!!.add(MyRecycleListItem(20, "Kotlin List 赋值 "))



        /**
         * 接口实现方法 object : Class
         */
        myROC = object : MyRecycleOnClick {
            override fun onClickListener(position: Int) {
                /**
                 * when语句不仅可以替代掉switch语句，而且比switch语句更加强大
                 */
                when (position) {
                /**
                 * 和逗号结合使用即添加了都好后 position = 0,1 都执行第一个方法。
                 */
                    -1, 0  ->  funLambda()
                    1  ->  funLambda1()
                    2  ->  funLambda2()
                    3  ->  funLambda3()
                    4  ->  funLambda4()
                    5  ->  funLambdaTodo()
                    6  ->  testRun1()
                    7  ->  testRun2()
                    8  ->  testRun3()
                    9  ->  testWith1()
                    10  ->  testApply()
                    11  ->  testAlso()
                    12  ->  testLet()
                    13  ->  testTakeIf()
                    14  ->  testTakeUnless()
                    15  ->  testRepeat()
                    16  ->  testComponent()
                    17  ->  testReverse()
                    18  ->  testList()
                    19  ->  testMap()
                    20  ->  testListV()

                }
            }
        }
    }


    private fun testListV() {
        var listBasedao: List<Any>
        val listDemo : List<DemoIn> = listOf( DemoIn(1), DemoIn(2) )
        listBasedao = listDemo

        listBasedao.forEach { println(" list 赋值  ${it.toString() } " ) }
    }

    private fun testMap() {
        // 以键值对的形式出现，键与值之间使用to
        val map1 = mapOf("key1" to 2 , "key2" to 3)
        val map2 = mapOf<Int,String>(1 to "value1" , 2 to "value2")
        val mutableMap = mutableMapOf("key1" to 2 , "key2" to 3)
        val hashMap = hashMapOf("key1" to 2 , "key1" to 3)   // 同Java中的HashMap

        mutableMap.forEach{
            (key,value) -> println("map forEach key = $key \t value = $value")
        }
        for ((key, value) in map2 ) {
            println("map key = $key \t value = $value")
        }


        val map3 = mapOf("key1" to 2 , "key1" to 3 , "key1" to "value1" , "key2" to "value2")

        map3.forEach{
            (key,value) -> println("$key \t $value")
        }
    }

    /**
     * 在定义集合类型变量的时候如果使用List<E>、Set<E>、Map<K,V>声明的时候该集合则是不可变集合，
     * 而使用MutableList<E>、MutableSet<E>、MutableMap<K,V>的时候该集合才是可变类型集合
     */
    private fun testList() {
        /** 使用listOf()初始化不可变的List类型集合 */
        val arr = arrayOf("1","2",3,4,5)
        val list1 = listOf(1,2,"3",4,"5")                // 随意创建
        val list2 = listOf<String>("1","2","3","4","5")  // 确定元素的值类型
        val list3 = listOf(arr)                          // 可传入一个数组
        // 遍历
        for(value in list1){
            println(" list $value \t")
        }
        /**使用mutableListOf()初始化不可变的List类型集合使用mutableListOf()初始化不可变的List类型集合*/
        val mutableList1 = mutableListOf(1,2,"3",4,"5")                // 随意创建
        val mutableList2 = mutableListOf<String>("1","2","3","4","5")  // 确定元素的值类型
        val mutableList3 = mutableListOf(arr)                          // 可传入一个数组
        val mutableList : ArrayList<String>  // 这里的ArrayList<>和Java里面的ArrayList一致

        mutableList1.add("6")  // 添加元素
        mutableList1.add("7")
        mutableList1.remove(1)   // 删除某一元素

        // 遍历
        for(value in mutableList1){
            println(" mutable $value \t")
        }
        mutableList1.clear()   // 清空集合

        val set1 = setOf(1,2,"3","4","2",1,2,3,4,5)
        val mutableSet1 = mutableSetOf(1,2,"3","4","2",1,2,3,4,5)
        val mutableSet2 : HashSet<String>  // 这里的HashSet<>和Java里面的HashSet<>一致
        // 遍历
        for(value in set1){
            println(" set $value \t")
        }
    }

    /**
     * 反转元素
     */
    private fun testReverse() {
        val arr = arrayOf("1",2,3,4)
        arr.reverse()

        // 文章后面会讲解forEach高阶函数。比for循环简洁多了
        for (index in arr){
            println("$index \t")
        }
    }

    /**
     * 获取数组的前几个元素
     */
    private fun testComponent() {
        val arr = arrayOf("1",2,3,4)

        println(arr.component1())
        println(arr.component2())
        println(arr.component3())
        println(arr.component4())

        // 程序崩溃，因为元素只有4个，所以在不确定元素个数的情况，慎用这些函数，还是使用遍历安全些。
//        println(arr.component5())
    }
    /**
     * 根据传入的重复次数去重复执行一个我们想要的动作(函数)
     */
    private fun testRepeat() {
        repeat(5){
            println("我是重复的第${it + 1}次，我的索引为：$it")
        }
    }

    /**
     * 这个函数的作用和T.takeIf()函数的作用是一样的。只是和其的逻辑是相反的。即：传入一个你希望的一个条件，如果对象符合你的条件则返回null，反之，则返回自身。
     */
    private fun testTakeUnless() {
        val str = "kotlin"

        val result = str.takeUnless {
            it.startsWith("ko")
        }

        println("result = $result")
    }
    /**
     * 传入一个你希望的一个条件，如果对象符合你的条件则返回自身，反之，则返回null。
     */
    private fun testTakeIf() {
        val str = "kotlin"

        val result = str.takeIf {
            it.startsWith("ko")
        }

        println("result = $result")
    }
    /**
     * 它其实和T.also以及T.apply都很相似。而T.let的作用也不仅仅在使用空安全这一个点上
     */
    private fun testLet() {
        "kotlin".let {
            println("原字符串：$it")         // kotlin
            it.reversed()
        }.let {
            println("反转字符串后的值：$it")     // niltok
            it.plus("-java")
        }.let {
            println("新的字符串：$it")          // niltok-java
        }

        "kotlin".also {
            println("原字符串：$it")     // kotlin
            it.reversed()
        }.also {
            println("反转字符串后的值：$it")     // kotlin
            it.plus("-java")
        }.also {
            println("新的字符串：$it")        // kotlin
        }

        "kotlin".apply {
            println("原字符串：$this")     // kotlin
            this.reversed()
        }.apply {
            println("反转字符串后的值：$this")     // kotlin
            this.plus("-java")
        }.apply {
            println("新的字符串：$this")        // kotlin
        }
    }

    /**
     * T.also中只能使用it调用自身,而T.apply中只能使用this调用自身
     */
    private fun testAlso() {
        "kotlin".also {
            println("结果：${it.plus("-java")}")
        }.also {
            println("结果：${it.plus("-php")}")
        }

        "kotlin".apply {
            println("结果：${this.plus("-java")}")
        }.apply {
            println("结果：${this.plus("-php")}")
        }
    }
    private fun testApply() {
        val mTvBtn = R.id.btn_1 as Button
        mTvBtn.apply{
            text = "kotlin"
            textSize = 13f
        }.apply{
            // 这里可以继续去设置属性或一些TextView的其他一些操作
        }.apply{
            setOnClickListener{ view -> showToast("这个是点击事件")}
        }
    }
    private fun testWith1() {
        val newStr = "kotlin"
        with(newStr){
            println( "length = ${this?.length}" )
            println( "first = ${this?.first()}")
            println( "last = ${this?.last()}" )
        }

        newStr?.run {
            println( "length = $length" )
            println( "first = ${first()}")
            println( "last = ${last()}" )
        }
    }
    private fun testRun3() {
        val mTvBtn : Button =  R.id.btn_1 as Button
        mTvBtn.run{
            text = "kotlin"
            textSize = 13f
        }

        showToast( " Button 的字 ${mTvBtn.text} " )
    }

    private fun testRun2() {
        val str = "kotlin"
        str.run {
            println( "length = ${this.length}" )
            println( "first = ${first()}")
            println( "last = ${last()}" )
        }
    }
    private fun testRun1() {
        val str = "kotlin"

        run{
            val str = "java"   // 和上面的变量不会冲突
            println("str = $str")
        }

        println("str = $str")
    }
    private fun funLambdaTodo(){
        TODO("测试TODO函数，是否显示抛出错误")
    }


    // 源代码
    fun test(){
        println("无参数")
    }

    // lambda代码
    val test0 = {
        println("lambda 表达式 无参数")
    }

    // 源代码
    fun test1(a : Int , b : Int) : Int{
        return a + b
    }

    // lambda
    val test2 : (Int , Int) -> Int = {a , b -> a + b}
    // 或者
    val test2_1 = {a : Int , b : Int -> a + b}


    private fun funLambda() {
        test()
        test0()
        showToast("计算结果值1：${test1(1,2)}")
        showToast("计算结果值2：${test2(2,3)}")
        showToast("计算结果值3：${test2_1(3,4)}")

    }

    fun funLambda1() {
        // 这里举例一个语言自带的一个高阶函数filter,此函数的作用是过滤掉不满足条件的值。
        val arr = arrayOf(1, 3, 5, 7, 9)
        // 过滤掉数组中元素小于2的元素，取其第一个打印。这里的it就表示每一个元素。
        println(arr.filter { it < 5 }.component1())
    }

    fun test(num1 : Int, bool : (Int) -> Boolean) : Int{
        return if (bool(num1)){ num1 } else 0
    }
    fun funLambda2() {
        println(test(10, { it > 5 }))
        println(test(4, { it > 5 }))
    }

    var map = mapOf("key1" to "value1","key2" to "value2","key3" to "value3")
    fun funLambda3() {
        map.forEach{
            (key , value) -> println("$key \t $value")
        }

        // 不需要key的时候
        map.forEach{
            (_ , value) -> println("$value")
        }
    }

    fun resultByOpt(num1 : Int , num2 : Int , result : (Int ,Int) -> Int) : Int{
        return result(num1,num2)
    }

    fun funLambda4() {
        val result1 = resultByOpt(1,2){
            num1, num2 ->  num1 + num2
        }

        val result2 = resultByOpt(3,4){
            num1, num2 ->  num1 - num2
        }

        val result3 = resultByOpt(5,6){
            num1, num2 ->  num1 * num2
        }

        val result4 = resultByOpt(6,3){
            num1, num2 ->  num1 / num2
        }

        println("result1 = $result1")
        println("result2 = $result2")
        println("result3 = $result3")
        println("result4 = $result4")
    }
}
