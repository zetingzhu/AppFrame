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
        mList!!.add(MyRecycleListItem(21, "Kotlin 集合转换数组  "))
        mList!!.add(MyRecycleListItem(22, "Kotlin 数组转换集合  "))
        mList!!.add(MyRecycleListItem(23, "Kotlin 集合转换集合  "))
        mList!!.add(MyRecycleListItem(24, "Kotlin 元素操作符  "))
        mList!!.add(MyRecycleListItem(25, "Kotlin 顺序操作符  "))
        mList!!.add(MyRecycleListItem(26, "Kotlin 映射操作符  "))
        mList!!.add(MyRecycleListItem(27, "Kotlin 过滤操作符  "))
        mList!!.add(MyRecycleListItem(28, "Kotlin 生产操作符  "))
        mList!!.add(MyRecycleListItem(29, "Kotlin 统计操作符  "))



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
                    21  ->  listToArray()
                    22  ->  arrayToList()
                    23  ->  listToList()
                    24  ->  listOperator()
                    25  ->  listOperator1()
                    26  ->  listOperator2()
                    27  ->  listOperator3()
                    28  ->  listOperator4()
                    29 ->  listOperator5()

                }
            }
        }
    }

    /**
    2.6、统计操作符

    统计操作符包括：

    any() : 判断是不是一个集合，若是，则在判断集合是否为空，若为空则返回false,反之返回true,若不是集合，则返回hasNext
    any{...} : 判断集合中是否存在满足条件的元素。若存在则返回true,反之返回false
    all{...} : 判断集合中的所有元素是否都满足条件。若是则返回true,反之则返回false
    none() : 和any()函数的作用相反
    none{...} : 和all{...}函数的作用相反
    max() : 获取集合中最大的元素，若为空元素集合，则返回null
    maxBy{...} : 获取方法处理后返回结果最大值对应那个元素的初始值，如果没有则返回null
    min() : 获取集合中最小的元素，若为空元素集合，则返回null
    minBy{...} : 获取方法处理后返回结果最小值对应那个元素的初始值，如果没有则返回null
    sum() : 计算出集合元素累加的结果。
    sumBy{...} : 根据元素运算操作后的结果，然后根据这个结果计算出累加的值。
    sumByDouble{...} : 和sumBy{}相似，不过sumBy{}是操作Int类型数据，而sumByDouble{}操作的是Double类型数据
    average() : 获取平均数
    reduce{...} : 从集合中的第一项到最后一项的累计操作。
    reduceIndexed{...} : 和reduce{}作用相同，只是其可以操作元素的下标(index)
    reduceRight{...} : 从集合中的最后一项到第一项的累计操作。
    reduceRightIndexed{...} : 和reduceRight{}作用相同，只是其可以操作元素的下标(index)
    fold{...} : 和reduce{}类似，但是fold{}有一个初始值
    foldIndexed{...} : 和reduceIndexed{}类似，但是foldIndexed{}有一个初始值
    foldRight{...} : 和reduceRight{}类似，但是foldRight{}有一个初始值
    foldRightIndexed{...} : 和reduceRightIndexed{}类似，但是foldRightIndexed{}有一个初始值
     */
    private fun listOperator5() {
        val list1 = listOf(1,2,3,4,5)

        println("  ------   any -------")
        println(list1.any())
        println(list1.any{it > 10})

        println("  ------   all -------")
        println(list1.all { it > 2 })

        println("  ------   none -------")
        println(list1.none())
        println(list1.none{ it > 2})

        println("  ------   max -------")
        println(list1.max())
        println(list1.maxBy { it + 2 })

        println("  ------   min -------")
        println(list1.min())        // 返回集合中最小的元素
        println(list1.minBy { it + 2 })

        println("  ------   sum -------")
        println(list1.sum())
        println(list1.sumBy { it + 2 })
        println(list1.sumByDouble { it.toDouble() })

        println(" ------  average -----")
        println(list1.average())

        println("  ------   reduce  -------")
        println(list1.reduce { result, next -> result  + next})
        println(list1.reduceIndexed { index, result, next ->
            println(index.toString().plus(" : ").plus(result).plus("-").plus(next))
            result + next
        })
        println(list1.reduceRight { result, next -> result  + next })
        println(list1.reduceRightIndexed {index, result, next ->
            index + result + next
        })

        println("  ------   fold  -------")
        println(list1.fold(3){result, next -> result  + next})
        println(list1.foldIndexed(3){index,result, next ->
            index + result  + next
        })
        println(list1.foldRight(3){result, next -> result  + next})
        println(list1.foldRightIndexed(3){index,result, next ->
            index + result  + next
        })
    }

    /**
    2.5、生产操作符

    生产操作符包括：

    plus() : 合并两个集合中的元素，组成一个新的集合。也可以使用符号+
    zip : 由两个集合按照相同的下标组成一个新集合。该新集合的类型是：List<Pair>
    unzip : 和zip的作用相反。把一个类型为List<Pair>的集合拆分为两个集合。看下面的例子
    partition : 判断元素是否满足条件把集合拆分为有两个Pair组成的新集合。
     */
    private fun listOperator4() {
        val list1 = listOf(1,2,3,4)
        val list2 = listOf("kotlin","Android","Java","PHP","JavaScript")

// plus() 和 `+`一样
        println(list1.plus(list2))
        println(list1 + list2)

// zip
        println(list1.zip(list2))
        println(list1.zip(list2){       // 组成的新集合由元素少的原集合决定
            it1,it2-> it1.toString().plus("-").plus(it2)
        })

// unzip
        val newList = listOf(Pair(1,"Kotlin"),Pair(2,"Android"),Pair(3,"Java"),Pair(4,"PHP"))
        println(newList.unzip())

// partition
        println(list2.partition { it.startsWith("Ja") })

        var mPair : Pair<List<String>, List<String>>  =  list2.partition { it.startsWith("Ja") }
        var mList1 : List<String> = mPair.first
        var mList2 : List<String> = mPair.second
        println( mList1  )
        println( mList2  )

    }

    /**
    2.4、过滤操作符

    过滤操作符包括：

    filter{...} : 把不满足条件的元素过滤掉
    filterIndexed{...} : 和filter{}函数作用类似，只是可以操作集合中元素的下标（index）
    filterNot{...} : 和filter{}函数的作用相反
    filterNotNull() : 过滤掉集合中为null的元素。
    take(num) : 返回集合中前num个元素组成的集合
    takeWhile{...} : 循环遍历集合，从第一个元素开始遍历集合，当第一个出现不满足条件元素的时候，退出遍历。然后把满足条件所有元素组成的集合返回。
    takeLast(num) : 返回集合中后num个元素组成的集合
    takeLastWhile{...} : 循环遍历集合，从最后一个元素开始遍历集合，当第一个出现不满足条件元素的时候，退出遍历。然后把满足条件所有元素组成的集合返回。
    drop(num) : 过滤集合中前num个元素
    dropWhile{...} : 相同条件下，和执行takeWhile{...}函数后得到的结果相反
    dropLast(num) : 过滤集合中后num个元素
    dropLastWhile{...} : 相同条件下，和执行takeLastWhile{...}函数后得到的结果相反
    distinct() : 去除重复元素
    distinctBy{...} : 根据操作元素后的结果去除重复元素
    slice : 过滤掉所有不满足执行下标的元素。

     */
    private fun listOperator3() {
        val list1 = listOf(-1,-3,1,3,5,6,7,2,4,10,9,8)
        val list2 = listOf(1,3,4,5,null,6,null,10)
        val list3 = listOf(1,1,5,2,2,6,3,3,7,4,4,8)

        println(list1 )

        println("  ------   filter -------")
        println(list1.filter { it > 1  })
        println(list1.filterIndexed { index, result ->
            index < 5 && result > 3
        })
        println(list1.filterNot { it > 1 })
        println(list2.filterNotNull())

        println("  ------   take -------")
        println(list1.take(5))
        println(list1.takeWhile { it < 5 })
        println(list1.takeLast(5))
        println(list1.takeLastWhile { it > 5 })

        println("  ------   drop -------")
        println(list1.drop(5))
        println(list1.dropWhile { it < 5 })
        println(list1.dropLast(5))
        println(list1.dropLastWhile { it > 5 })

        println("  ------   distinct -------")
        println(list3.distinct())
        println(list3.distinctBy { it + 2 })

        println("  ------   slice -------")
        println(list1.slice(listOf(1,3,5,7)))
        println(list1.slice(IntRange(1,5)))
    }

    /**
    2.3、映射操作符

    映射操作符包括：

    map{...} : 把每个元素按照特定的方法进行转换，组成一个新的集合。
    mapNotNull{...} : 同map{}函数的作用相同，只是过滤掉转换之后为null的元素
    mapIndexed{index,result} : 把每个元素按照特定的方法进行转换，只是其可以操作元素的下标(index)，组成一个新的集合。
    mapIndexedNotNull{index,result} : 同mapIndexed{}函数的作用相同，只是过滤掉转换之后为null的元素
    flatMap{...} : 根据条件合并两个集合，组成一个新的集合。
    groupBy{...} : 分组。即根据条件把集合拆分为为一个Map<K,List<T>>类型的集合。具体看实例
     */
    private fun listOperator2() {
        val list1 = listOf("kotlin","Android","Java","PHP","JavaScript")

        println(list1.map { "str-".plus(it) })

        println(list1.mapNotNull { "str-".plus(it) })

        println(list1.mapIndexed { index, str ->
            index.toString().plus("-").plus(str)
        })

        println(list1.mapIndexedNotNull { index, str ->
            index.toString().plus("-").plus(str)
        })

        println( list1.flatMap { listOf(it,"new-".plus(it)) })

        println(list1.groupBy { if (it.startsWith("Java")) "big" else "latter" })
    }

    /**
    2.1、元素操作符

    元素操作符在这里包括：

    contains(元素) : 检查集合中是否包含指定的元素，若存在则返回true，反之返回false
    elementAt(index) : 获取对应下标的元素。若下标越界，会抛出IndexOutOfBoundsException（下标越界）异常，同get(index)一样
    elementAtOrElse(index,{...}) : 获取对应下标的元素。若下标越界，返回默认值，此默认值就是你传入的下标的运算值
    elementAtOrNull(index) : 获取对应下标的元素。若下标越界，返回null
    first() : 获取第一个元素，若集合为空集合，这会抛出NoSuchElementException异常
    first{} : 获取指定元素的第一个元素。若不满足条件，则抛出NoSuchElementException异常
    firstOrNull() : 获取第一个元素，若集合为空集合，返回null
    firstOrNull{} : 获取指定元素的第一个元素。若不满足条件，返回null
    getOrElse(index,{...}) : 同elementAtOrElse一样
    getOrNull(index) : 同elementAtOrNull一样
    last() : 同first()相反
    last{} : 同first{}相反
    lastOrNull{} : 同firstOrNull()相反
    lastOrNull() : 同firstOrNull{}相反
    indexOf(元素) : 返回指定元素的下标，若不存在，则返回-1
    indexOfFirst{...} : 返回第一个满足条件元素的下标，若不存在，则返回-1
    indexOfLast{...} : 返回最后一个满足条件元素的下标，若不存在，则返回-1
    single() : 若集合的长度等于0,则抛出NoSuchElementException异常，若等于1，则返回第一个元素。反之，则抛出IllegalArgumentException异常
    single{} : 找到集合中满足条件的元素，若元素满足条件，则返回该元素。否则会根据不同的条件，抛出异常。这个方法慎用
    singleOrNull() : 若集合的长度等于1,则返回第一个元素。否则，返回null
    singleOrNull{} : 找到集合中满足条件的元素，若元素满足条件，则返回该元素。否则返回null
    forEach{...} : 遍历元素。一般用作元素的打印
    forEachIndexed{index,value} : 遍历元素，可获得集合中元素的下标。一般用作元素以及下标的打印
    componentX() ： 这个函数在前面的章节中提过多次了。用于获取元素。其中的X只能代表1..5。详情可看下面的例子
     */
    fun listOperator(){
        val list = listOf("kotlin","Android","Java","PHP","Python","IOS")

        println("  ------   contains -------")
        println(list.contains("JS"))

        println("  ------   elementAt -------")

        println(list.elementAt(2))
        println(list.elementAtOrElse(10,{it}))
        println(list.elementAtOrNull(10))

        println("  ------   get -------")
        println(list.get(2))
        println(list.getOrElse(10,{it}))
        println(list.getOrNull(10))

        println("  ------   first -------")
        println(list.first())
        println(list.first{ it == "Android" })
        println(list.firstOrNull())
        println(list.firstOrNull { it == "Android" })

        println("  ------   last -------")
        println(list.last())
        println(list.last{ it == "Android" })
        println(list.lastOrNull())
        println(list.lastOrNull { it == "Android" })

        println("  ------   indexOf -------")
        println(list.indexOf("Android"))
        println(list.indexOfFirst { it == "Android" })
        println(list.indexOfLast { it == "Android" })

        println("  ------   single -------")
        val list2 = listOf("list")
        println(list2.single())     // 只有当集合只有一个元素时，才去用这个函数，不然都会抛出异常。
        println(list2.single { it == "list" }) //当集合中的元素满足条件时，才去用这个函数，不然都会抛出异常。若满足条件返回该元素
        println(list2.singleOrNull()) // 只有当集合只有一个元素时，才去用这个函数，不然都会返回null。
        println(list2.singleOrNull { it == "list" }) //当集合中的元素满足条件时，才去用这个函数，不然返回null。若满足条件返回该元素

        println("  ------   forEach -------")
        list.forEach { println(it) }
        list.forEachIndexed { index, it -> println("index : $index \t value = $it") }

        println("  ------   componentX -------")
        println(list.component1())  // 等价于`list[0]  <=> list.get(0)`
        println(list.component2())  // 等价于`list[1]  <=> list.get(1)`
        println(list.component3())  // 等价于`list[2]  <=> list.get(2)`
        println(list.component4())  // 等价于`list[3]  <=> list.get(3)`
        println(list.component5())  // 等价于`list[4]  <=> list.get(4)`
    }


    /**
    2.2、顺序操作符

    顺序操作符包括：

    reversed() : 反序。即和初始化的顺序反过来。
    sorted() : 自然升序。
    sortedBy{} : 根据条件升序，即把不满足条件的放在前面，满足条件的放在后面
    sortedDescending() : 自然降序。
    sortedByDescending{} : 根据条件降序。和sortedBy{}相反
     */
    fun listOperator1() {
        val list1 = listOf(-1, -3, 1, 3, 5, 6, 7, 2, 4, 10, 9, 8)

// 反序
        println(list1.reversed())

// 升序
        println(list1.sorted())

// 根据条件升序，即把不满足条件的放在前面，满足条件的放在后面
        println(list1.sortedBy { it % 2 == 0 })

// 降序
        println(list1.sortedDescending())

// 根据条件降序，和`sortedBy{}`相反
        println(list1.sortedByDescending { it % 2 == 0 })
    }

    // 数组转集合
    fun arrayToList() {
        val arr = arrayOf(1,3,5,7,9)
        val list = arr.toList()
        println("变量arr的类型为：${arr.javaClass}")
        println("变量list的类型为：${list.javaClass}")
        println(list[1])
    }

// 集合转集合，这里用Set转List

    fun listToList(){
        val set = setOf(1)
        val setTolist = set.toList()

        println("变量set的类型为：${set.javaClass}")
        println("变量setTolist的类型为：${setTolist.javaClass}")
        println(setTolist[0])
    }

    // 集合转数组
    private fun listToArray() {
        val list = listOf<Int>(1,2,3,4,5,6)         // 声明一个Int类型的List
        val listArray = list.toIntArray()           // 转换

        println(list.javaClass.toString())          // 打印list的类型
        println(listArray.javaClass.toString())     // 打印listArray的类型
        println(listArray[1])
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

        mutableMap.put("key3" , 4)

        mutableMap.forEach{
            (key,value) -> println("mutableMap forEach key = $key \t value = $value")
        }
        for ((key, value) in map2 ) {
            println("map2 key = $key \t value = $value")
        }

        map1.forEach{
            (key,value) -> println("map1 key = $key \t value = $value")
        }

        val map3 = mapOf("key1" to 2 , "key1" to 3 , "key1" to "value1" , "key2" to "value2")

        map3.forEach{
            (key,value) -> println("map3 --> $key \t $value")
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
        /**使用mutableListOf()初始化可变的List类型集合 */
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
