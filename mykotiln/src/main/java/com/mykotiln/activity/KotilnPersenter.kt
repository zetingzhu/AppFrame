package com.mykotiln.activity

import com.mykotiln.DemoIn
import java.lang.Integer.parseInt

/**
 * Created by allen on 18/9/21.
 */

class KotilnPersenter{

    // 测试
    fun mainClazz (args: Array<String>) {
        var person: DemoIn = DemoIn(0,0,null)

        person.lastName = "wang"

        println( " id:${person.id}  - lastName:${person.lastName}")

        person.no = 9
        println("no:${person.no}")

        person.no = 20
        println("no:${person.no}")

    }

    fun mainStr() {
        var a = 1
        // 模板中的简单名称：
        val s1 = "a is $a"
        println(s1)

        a = 2
        // 模板中的任意表达式：
        val s2 = "${s1.replace("is", "was")}, but now is $a"
        println(s2)
    }

    /**
     * NULL检查机制
     */
    fun mainNull(args: Array<String>){
        if (args.size < 2) {
            print("Two integers expected")
            return
        }
        val x = parseInt(args[0])
        val y = parseInt(args[1])
        // 直接使用 `x * y` 会导致错误, 因为它们可能为 null.
        if (x != null && y != null) {
            // 在进行过 null 值检查之后, x 和 y 的类型会被自动转换为非 null 变量
            print(x * y)
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
