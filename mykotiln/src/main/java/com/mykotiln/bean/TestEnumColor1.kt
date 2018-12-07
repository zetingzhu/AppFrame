package com.mykotiln.bean

/**
 * 颜色枚举
 * Created by zeting
 * Date 18/12/3.
 */
enum class TestEnumColor1(var argb : Int) {
    RED(0xFF0000){
        override fun print() {
            println("我是枚举常量 RED ")
        }
    },
    WHITE(0xFFFFFF){
        override fun print() {
            println("我是枚举常量 WHITE ")
        }
    },
    BLACK(0x000000){
        override fun print() {
            println("我是枚举常量 BLACK ")
        }
    },
    GREEN(0x00FF00){
        override fun print() {
            println("我是枚举常量 GREEN ")
        }
    },
    YELLOW(0x0000FF){
        override fun print(){
            println("我是枚举常量 YELLOW ")
        }
    }
    ;

    abstract fun print()
}