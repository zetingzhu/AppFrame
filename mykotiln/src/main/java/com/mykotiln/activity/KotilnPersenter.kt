package com.mykotiln.activity

import android.content.Context
import com.mykotiln.bean.DemoIn
import com.mykotiln.util.MyRecycleListItem
import com.mykotiln.util.MyRecycleOnClick
import com.mykotiln.util.MyRecyclerViewJava
import com.mykotiln.util.MyRecyclerViewKotlin
import java.lang.Integer.parseInt
import java.util.regex.Pattern

/**
 * persenter 类
 */
class KotilnPersenter : BasePersenter {

    // 列表数据
    var mList: MutableList<MyRecycleListItem>? = null
    var myROC: MyRecycleOnClick? = null

    constructor() : super()

    constructor(mContext: Context?) : super(mContext)

    /**
     *  设置kotlin语言的数据
     *  Int 参数，返回值 Int
     */
    fun setItemOperateKotlin(rvList: MyRecyclerViewKotlin): Int {
        setItemOperateData();
        rvList!!.setMyAdapter(mList!!, myROC!!)
        return 0;
    }

    /**
     *  设置java语言的数据
     */
    fun setItemOperateJava(rvList: MyRecyclerViewJava) {
        setItemOperateData();
        rvList!!.setMyAdapter(mList!!, myROC!!)
    }

    /**
     * 组成点击事件数据
     */
    fun setItemOperateData() {
        mList = ArrayList<MyRecycleListItem>()
        mList!!.add(MyRecycleListItem(0, "条件控制 if "))
        mList!!.add(MyRecycleListItem(1, "条件控制 if else "))
        mList!!.add(MyRecycleListItem(2, "条件控制 for until 递增"))
        mList!!.add(MyRecycleListItem(3, "条件控制 for .. 表示"))
        mList!!.add(MyRecycleListItem(4, "条件控制 for downTo 递减"))
        mList!!.add(MyRecycleListItem(5, "条件控制 for step 步长"))
        mList!!.add(MyRecycleListItem(6, "条件控制 for 迭代器 字符串"))
        mList!!.add(MyRecycleListItem(7, "条件控制 for 迭代器 数组"))
        mList!!.add(MyRecycleListItem(8, "条件控制 for indices 数组"))
        mList!!.add(MyRecycleListItem(9, "条件控制 for WithIndex 数组"))
        mList!!.add(MyRecycleListItem(10, "条件控制 for testForArrayIterator 数组"))
        mList!!.add(MyRecycleListItem(11, "条件控制 when "))
        mList!!.add(MyRecycleListItem(12, "条件控制 testForWhenIn "))
        mList!!.add(MyRecycleListItem(13, "条件控制 testForWhenIs "))
        mList!!.add(MyRecycleListItem(14, "条件控制 testForWhenOrIf "))
        mList!!.add(MyRecycleListItem(15, "条件控制 let操作符 "))
        mList!!.add(MyRecycleListItem(16, "条件控制 Evils ?:  操作符 "))
        mList!!.add(MyRecycleListItem(17, "条件控制 Evils !!  操作符 "))
        mList!!.add(MyRecycleListItem(18, "条件控制 Evils as? 操作符 "))
        mList!!.add(MyRecycleListItem(19, "vararg 修饰符 修饰可变参数 "))
        mList!!.add(MyRecycleListItem(20, "vararg 2 修饰符 伸展操作符 "))
        mList!!.add(MyRecycleListItem(21, "字符串的常用操作 first "))
        mList!!.add(MyRecycleListItem(22, "字符串的常用操作 last "))
        mList!!.add(MyRecycleListItem(23, "字符串的常用操作 find findLast "))
        mList!!.add(MyRecycleListItem(24, "字符串的常用操作 index substring replace "))
        mList!!.add(MyRecycleListItem(25, "字符串的常用操作 split plus isNullOrEmpty reversed "))
        mList!!.add(MyRecycleListItem(26, "Kotlin 操作符 "))
        mList!!.add(MyRecycleListItem(27, "Kotlin 复合 操作符 "))
        mList!!.add(MyRecycleListItem(28, "Kotlin 位运算 操作符 "))
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
                    -1, 0 -> {
                        testIfElse()
                    }
                    1 -> {
                        testIfElseSY()
                    }
                    2 -> testForUntil()
                    3 -> testForDian()
                    4 -> testForDownTo()
                    5 -> testForStep()
                    6 -> testForInString()
                    7 -> testForInArray()
                    8 -> testForArrayIndices()
                    9 -> testForArrayWithIndex()
                    10 -> testForArrayIterator()
                    11 -> testForWhen()
                    12 -> testForWhenIn()
                    13 -> testForWhenIs()
                    14 -> testForWhenOrIf()
                    15 -> testLen()
                    16 -> testEvils1()
                    17 -> testEvils2()
                    18 -> testEvils3()
                    19 -> testVararg()
                    20 -> testVararg2()
                    21 -> testStringFirst()
                    22 -> testStringLast()
                    23 -> testFindFindLast()
                    24 -> testIndex()
                    25 -> testSplit()
                    26 -> funOperator()
                    27 -> funFHCZF()
                    28 -> funWYSCZF()
                }
            }
        }
    }


    /**
     * 位运算操作符
     */
    fun funWYSCZF() {
        val a = 1
        val b = 5

        // 操作符实现
        val s = 3 in a..b     // true,因为3在区间[1,5]之内
        showToastW("s = $s")
        for (index in a..b) {
            showToastI("index = $index \t")
        }

        // 操作符重载方式实现
        val t = 6 in a.rangeTo(b)
        showToastW("t = $t")
        for (index in a.rangeTo(b)) {
            showToastI("index = $index \t")
        }
    }

    /**
     * 复合操作符
     */
    private fun funFHCZF() {
        var b = 2
        var a = 10
        var c = "Kotlin"

        c += a                          //等价于  c = c.plus(a)
        showToastW("c += $c \t")
        showToastI("c += ${c.plus(a)} \t")

        a += b                          //等价于  a = a.plus(b)
        showToastW("a += $a \t")
        showToastI("c += ${a.plus(b)} \t")

        a = 10
        a -= b                          //等价于  a = a.minus(b)
        showToastW("a -= $a \t")
        showToastI("c -= ${a.minus(b)} \t")

        a = 10
        a *= b                          //等价于  a = a.times(b)
        showToastW("a *= $a \t")
        showToastI("c *= ${a.times(b)} \t")

        a = 10
        a /= b                          //等价于  a = a.div(b)
        showToastW("a /= $a \t")
        showToastI("c /= ${a.div(b)} \t")

        a = 10
        a % b                          //等价于  a = a.rem(b)
        showToastW("a %= $a \t")
        showToastI("c %= ${a.rem(b)} \t")

    }

    /**
     * 数组操作符
     */
    fun funOperator() {
// 简单的二元操作
        val a = 10
        val b = 2
        val c = "2"
        val d = "Kotlin"

// 操作符实现
        showToast("a + d = " + a + d)
        showToast("c + d = " + c + d)
        showToast("a + b = ${a + b} \t a - b = ${a - b} \t a * b = ${a * b} \t a / b = ${a / b} \t a % b = ${a % b}")

// 操作符重载实现
        showToast("a + b = ${a.plus(b)} \t a - b = ${a.minus(b)} \t a * b = ${a.times(b)} \t a / b = ${a.div(b)} \t a % b = ${a.rem(b)}")
//        println("a + d = ${a + d}") //错误：字符串模板限制只能为数值型
//        println(a.plus(d))  //错误：因为第一个操作数`a`限制了其plus()方法的参数，
//        println(d.plus(a))  //正确：因为plus()方法的参数为超（Any）类型
    }


    /***
    isEmpty() : 其源码是判断其length是等于0，若等于0则返回true,反之返回false。不能直接用于可空的字符串
    isNotEmpty() : 其源码是判断其length是否大于0，若大于0则返回true,反之返回false。不能直接用于可空的字符串
    isNullOrEmpty() : 其源码是判断该字符串是否为null或者其length是否等于0。
    isBlank() : 其源码是判断其length是否等于0,或者判断其包含的空格数是否等于当前的length。不能直接用于可空的字符串
    isNotBlank() : 其源码是对isBlank()函数取反。不能直接用于可空的字符串
    isNotOrBlank() : 其源码判断该字符串是否为null。或者调用isBlank()函数
     */
    fun testSplit() {
        var str2 = "1 kotlin 2 java 3 Lua 4 JavaScript"

        val list3 = str2.split(Regex("[0-9]+"))
        for (str in list3) {
            showToast("$str \t")
        }


        val list4 = str2.split(Pattern.compile("[0-9]+"))
        for (str in list4) {
            showToast("$str \t")
        }

        val str1 = "Kotlin is a very good programming language"
        val list1 = str1.split(' ')
        for (str in list1) {
            showToastW("$str \t")
        }

        val list2 = str1.split(" ")
        for (str in list2) {
            showToastW("$str \t")
        }
        // 可变参数
        val str3 = "a b c d e f g h 2+3+4+5"
        val list5 = str3.split(' ', '+')
        for (str in list5) {
            showToast("$str \t")
        }

        val str4 = "kotlin"
        showToastE(str4.plus(" very good"))
        showToastE(str4 + " very good")
        showToast("字符串反转：${str4.reversed()}")

        // startsWith()判断其字符串是否由某一个字符或字符串起始。
        showToastW((str4.startsWith('k').toString()))      // 是否有字符`k`起始
        showToastW(str4.startsWith("Kot").toString())   // 是否由字符串`kot`起始
        showToastW(str4.startsWith("lin", 3).toString())  // 当起始位置为3时，是否由字符串`lin`起始
//        endsWith() 判断其字符串是否由某一个字符或字符串结尾。
        showToast(str4.endsWith("lin").toString())  // 是否由字符串`lin`结尾
        showToast(str4.endsWith('n').toString())    // 是否由字符`n`结尾
        // contains 包含某个字符串
        showToastE(str4.contains("tli").toString())    // 是否由字符`n`结尾

    }

    fun testIndex() {
        val str = "Kotlin is a very good programming language 1234a Kotlin 5678 3 is 4"
        showToast(str.indexOfFirst { it == 'o' })//查找某一个元素或字符串在原字符串中第一次出现的下标。
        showToast(str.indexOfLast { it == 'o' })//查找某一个元素或字符串在原字符串中最后一次出现的下标。
        showToast(str.indexOf('o', 0))//查找某一个元素或字符串在原字符串中第一次出现的下标。
        showToast(str.indexOf("very", 0))
        showToast(str.lastIndexOf('o'))
        showToast(str.lastIndexOf("good"))

        showToast("s = ${str.substring(10)}")  // 当只有开始下标时，结束下标为length - 1
        showToast(str.substring(0, 15))
        showToast(str.substring(IntRange(0, 15)))

        showToastI(str.replace('a', 'A'))
        // 正则的规则为检测数字，如果为数字则替换成字符串`kotlin`
        showToastI(str.replace(Regex("[0-9]+"), "kotlin"))
        showToastI(str.replace(Regex("[0-9]+"), {
            "abcd "
        }))
        //把满足条件的第一个字符或字符串替换成新的字符或字符串
        showToastI(str.replaceFirst('a', 'A'))
        showToastI(str.replaceFirst("Kotlin", "Java"))
        //截取满足条件的第一个字符或字符串后面的字符串，包含满足条件字符或字符串自身，并在其前面加上新的字符串。
        showToastI(str.replaceBefore('a', "AA"))
        showToastI(str.replaceBefore("Kotlin", "Java"))
        //截取满足条件的最后一个字符或字符串后面的字符串，包含满足条件字符或字符串自身，并在其前面加上新的字符串。
        showToastI(str.replaceBeforeLast('a', "AA"))
        showToastI(str.replaceBeforeLast("Kotlin", "Java"))
        //截取满足条件的第一个字符或字符串前面的字符串，包含满足条件字符或字符串自身，并在其后面加上新的字符串。
        showToastI(str.replaceAfter('a', "AA"))
        showToastI(str.replaceAfter("Kotlin", "Java"))
        //截取满足条件的最后一个字符或字符串前面的字符串，包含满足条件字符或字符串自身，并在其后面加上新的字符串。
        showToastI(str.replaceAfterLast('a', "AA"))
        showToastI(str.replaceAfterLast("Kotlin", "Java"))


    }

    fun testFindFindLast() {
        val str = "kotlin very good"
        showToast("查找一个参数1  ${str.find { it == 't' }} ")
        showToast("查找一个参数2  ${str.findLast { it == 'y' }} ")
        showToast("查找一个参数3  ${str.findLast { it == 'z' }} ")
    }

    fun testStringLast() {
        val str = "kotlin very good"
        showToast("获取最后一个参数1  ${str.last()} ")
        showToast("获取最后一个参数2  ${str.get(str.lastIndex)} ")
        showToast("获取最后一个参数3  ${str[str.lastIndex]} ")
        /**其中 lastIndex 是一个拓展属性，其实现是 length - 1 */
        showToast("获取最后一个参数5  ${str.lastOrNull { it == 'z' }} ")
        showToast("获取最后一个参数6  ${str.last { it == 'z' }} ")
    }

    fun testStringFirst() {
        val str = "kotlin very good"
        showToast("获取第一个参数1  ${str.first()} ")
        showToast("获取第一个参数2  ${str[0]} ")
        showToast("获取第一个参数3  ${str.get(0)} ")
        showToast("获取第一个参数4  " + str.firstOrNull())
        showToast("获取第一个参数5  " + str.firstOrNull { it == 'a' })
        showToast("获取第一个参数6  " + str.first { it == 'a' })
    }

    fun testVararg() {
        varargFun(1, "aaa", "bbb", "ccc", "ddd", "fff")
    }

    fun testVararg2() {
        val strArray = arrayOf("aaa", "bbb", "ccc", "ddd", "fff")
        varargFun(1, *strArray)
    }

    fun varargFun(numA: Int, vararg str: String) {
        // 遍历
        for (s in str) {
            showToast("这个传入值: $numA  ,  $s ")
        }

// 获取元素
//    str[index]
//    str.component1() ... str.component5()

// 或者其高阶函数用法
//    str.map {  }
//    str.filter {  }
//    str.sortBy {  }

    }

    fun testEvils1() {
        val testStr: String? = null

        var length = 0

// 例： 当testStr不为空时，输出其长度，反之输出-1

// 传统写法
        length = if (testStr != null) testStr.length else -1
        showToast(length)
// ?: 写法
        length = testStr?.length ?: -1
        showToast(length)
    }

    fun testEvils2() {
        try {
            val testStr: String? = null
            showToast("正常转换结果：" + testStr!!.length)
        } catch (e: Exception) {
            showToast("是不是异常了：" + e.message.toString())
        }
    }

    fun testEvils3() {
        try {// 会抛出ClassCastException异常
            val num1: Int? = "Koltin" as Int
            showToast("正常转换结果：nun1 = $num1")
        } catch (e: Exception) {
            showToast("是不是异常了： ${e.message as String}  ")
            val num1: Int? = "Koltin" as? Int
            showToast("正常转换结果：nun1 = $num1")
        }
    }

    fun testLen() {
        val arrTest: Array<Int?> = arrayOf(1, 2, null, 3, null, 5, 6, null)

        // 不去空
        for (index in arrTest) {
            showToast("index => $index")
        }

        // 传统写法
        for (index in arrTest) {
            if (index == null) {
                continue
            }
            showToast("index => $index")
        }


        // let写法
        for (index in arrTest) {
            index?.let { showToast("index => $it") }
        }
    }

    fun testForWhen() {
        var num: Int = 5
        when (num > 5) {
            true -> {
                showToast("num > 5")
            }
            false -> {
                showToast("num < 5")
            }
            else -> {
                showToast("num = 5")
            }
        }
    }

    fun testForWhenIn() {
        var arrayList = arrayOf(1, 2, 3, 4, 5)
        when (1) {
            in arrayList.toIntArray() -> {
                showToast("1 存在于 arrayList数组中")
            }
            in 0..10 -> showToast("1 属于于 0~10 中")
            !in 5..10 -> showToast("1 不属于 5~10 中")
            else -> {
                showToast("都错了 哈哈！")
            }
        }
    }

    fun testForWhenIs() {
        when ("abc") {
            is String -> showToast("abc是一个字符串")
            else -> {
                showToast("abc不是一个字符串")
            }
        }

        // 智能转换
        var a: Int = 2
        when (a) {
            !is Int -> {
                showToast("$a 不是一个Int类型的值")
            }
            else -> {
                a = a.shl(2)
                showToast("a => $a")
            }
        }
    }

    fun testForWhenOrIf() {
        val items = setOf("apple", "banana", "kiwi")
        when {
            "orange" in items -> showToast("这个值 在 数组中")
            else -> showToast("这个值 不在 数组中")
        }
    }

    /**
     *  for 循环使用
     *  until 递增    范围：until[n,m)   => 即大于等于n,小于m
     *  ..  递增      范围：..[n,m]      => 即大于等于n，小于等于m
     *  downto  递减  范围：downTo[n,m]  => 即小于等于n,大于等于m ,n > m
     *  step 设置步长
     */
    fun testForUntil() {
        // 循环5次，且步长为1的递增
        for (i in 0 until 5) {
            showToast("i => $i \t")
        }
    }

    fun testForDian() {
        for (i in 0..5) {
            showToast("i => $i \t")
        }
    }

    fun testForDownTo() {
        for (i in 10 downTo 5) {
            showToast("i => $i \t")
        }
    }

    fun testForStep() {
        for (i in 0..10 step 2) {
            showToast("i => $i \t")
        }
    }

    fun testForInString() {
        for (i in "abfshert") {
            showToast("i => $i \t")
        }
    }

    fun testForInArray() {
        var arrayListOne = arrayOf(10, 20, 30, 40, 50)
        for (i in arrayListOne) {
            showToast("i => $i \t")
        }
    }

    fun testForArrayIndices() {
        var arrayListTwo = arrayOf(1, 3, 5, 7, 9)
        for (i in arrayListTwo.indices) {
            showToast("arrayListTwo[$i] => " + arrayListTwo[i])
        }
    }

    fun testForArrayWithIndex() {
        var arrayListTwo = arrayOf(1, 3, 5, 7, 9, 11)
        for ((index, value) in arrayListTwo.withIndex()) {
            showToast("index => $index \t value => $value")
        }
    }

    fun testForArrayIterator() {
        var arrayListThree = arrayOf(2, 'a', 3, false, 9)
        var iterator: Iterator<Any> = arrayListThree.iterator()
        while (iterator.hasNext()) {
            println(iterator.next())
        }
    }


    /**
     *  if  else 使用
     */
    fun testIfElse() {
        var numA = 2
        if (numA == 2) {
            showToast("numA == $numA => true")
        } else {
            showToast("numA == ${numA} => false")
        }
    }

    /**
     *  if  else 使用 三元运算
     */
    fun testIfElseSY() {
        // kotlin中直接用if..else替代。例：
        var numA = 2
        var numB: Int = if (numA > 2) 3 else 5  // 当numA大于2时输出numB的值为3，反之为5
        showToast("numB = > $numB")
    }


    // 测试
    fun mainClazz(args: Array<String>) {
        var person: DemoIn = DemoIn(0, 0, null)

        person.lastName = "wang"

        showToast(" id:${person.id}  - lastName:${person.lastName}")

        person.no = 9
        showToast("no:${person.no}")

        person.no = 20
        showToast("no:${person.no}")

    }

    fun mainStr() {
        var a = 1
        // 模板中的简单名称：
        val s1 = "a is $a"

        showToast(s1)

        a = 2
        // 模板中的任意表达式：
        val s2 = "${s1.replace("is", "was")}, but now is $a"
        showToast(s2)
    }

    /**
     * NULL检查机制
     */
    fun mainNull(args: Array<String>) {
        if (args.size < 2) {
            showToast("Two integers expected")
            return
        }
        val x = parseInt(args[0])
        val y = parseInt(args[1])
        // 直接使用 `x * y` 会导致错误, 因为它们可能为 null.
        if (x != null && y != null) {
            // 在进行过 null 值检查之后, x 和 y 的类型会被自动转换为非 null 变量
            showToast(x * y)
        }
    }

    /**
     * 类型检测及自动类型转换
     */
    fun getStringLength0(obj: Any): Int? {
        if (obj is String) {
            // 做过类型判断以后，obj会被系统自动转换为String类型
            return obj.length
        }

        //在这里还有一种方法，与Java中instanceof不同，使用!is
        // if (obj !is String){
        //   // XXX
        // }

        // 这里的obj仍然是Any类型的引用
        return null
    }

    fun getStringLength1(obj: Any): Int? {
        if (obj !is String)
            return null
        // 在这个分支中, `obj` 的类型会被自动转换为 `String`
        return obj.length
    }

    fun getStringLength2(obj: Any): Int? {
        // 在 `&&` 运算符的右侧, `obj` 的类型会被自动转换为 `String`
        if (obj is String && obj.length > 0)
            return obj.length
        return null
    }

    /**
     * 区间表达
     */
    fun mainQJ(args: Array<String>) {
        print("循环输出：")
        for (i in 1..4) print(i) // 输出“1234”
        println("\n----------------")
        print("设置步长：")
        for (i in 1..4 step 2) print(i) // 输出“13”
        println("\n----------------")
        print("使用 downTo：")
        for (i in 4 downTo 1 step 2) print(i) // 输出“42”
        println("\n----------------")
        print("使用 until：")
        // 使用 until 函数排除结束元素
        for (i in 1 until 4) {   // i in [1, 4) 排除了 4
            print(i)
        }
        println("\n----------------")
    }

}
