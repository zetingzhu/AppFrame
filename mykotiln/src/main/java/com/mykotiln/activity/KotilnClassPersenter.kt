package com.mykotiln.activity

import android.content.Context
import com.mykotiln.bean.*
import com.mykotiln.util.MyRecycleListItem
import com.mykotiln.util.MyRecycleOnClick
import com.mykotiln.util.MyRecyclerViewKotlin

/**
 * class persenter 类
 */
class KotilnClassPersenter : BasePersenter {

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
        rvList!!.setMyAdapter(mList!!, myROC!!)
    }

    /**
     * 组成点击事件数据
     */
    fun setItemOperateData() {
        mList = ArrayList<MyRecycleListItem>() as MutableList<MyRecycleListItem>?
        mList!!.add(MyRecycleListItem(0, "Class constructor 构造器 "))
        mList!!.add(MyRecycleListItem(1, "Class lateinit 修饰符 "))
        mList!!.add(MyRecycleListItem(2, "Class 四种修饰符 "))
        mList!!.add(MyRecycleListItem(3, "Class 继承 "))
        mList!!.add(MyRecycleListItem(4, "Class 覆盖 "))
        mList!!.add(MyRecycleListItem(5, "枚举常量的匿名类 "))
        mList!!.add(MyRecycleListItem(6, "枚举类的使用 "))
        mList!!.add(MyRecycleListItem(7, "接口类的使用 "))
        mList!!.add(MyRecycleListItem(8, "Data 数据类的使用 "))
        mList!!.add(MyRecycleListItem(9, "Sealed 密封类的使用 "))
        mList!!.add(MyRecycleListItem(10, "抽象类和接口使用 "))
        mList!!.add(MyRecycleListItem(11, "匿名内部类使用 "))


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
                    -1, 0  ->  funClassNew()
                    1 -> funLateinit()
                    2 -> funPublic()
                    3 -> funExtend()
                    4 -> funClassFG()
                    5 -> funEnumInner()
                    6 -> funEnumOperator()
                    7 -> funJkLSY()
                    8 -> funData()
                    9 -> funSealed()
                    10 -> funAbatract()
                    11 -> funListenerA()

                }
            }
        }
    }

    fun funListenerA(){
        // 测试匿名内部类
        val other = TestListenerClazz()
        other.setOnClickListener ( object : TestOnClickListener {
            override fun onItemClick(str: String) {
                    println(str)
            }

        })
        other.testListener()
    }

    /**
     * 接口和抽象类使用
     */
    fun funAbatract(){
        // val lanauage = Lanauage() 是错误的，因为抽象类不能直接被实例化

        val mTestAbstarctA = TestAbstractA()
        val mTestAbstarctB = TestAbstractA()

        println(mTestAbstarctA.name)
        mTestAbstarctA.init()
        mTestAbstarctA.test()

        println(mTestAbstarctB.name)
        mTestAbstarctB.init()
        mTestAbstarctB.test()

    }

    /**
     * 密封类的使用
     */
    fun funSealed(){
        val mPerson1 = TestSealedExample.User("name1",22)
        showToast(mPerson1)

        val mPerson2 = TestSealedExample.User("name2",23)
        showToast(mPerson2)

        showToast(mPerson1.hashCode())
        showToast(mPerson2.hashCode())
    }

    /**
     * 数据类的使用
     * Kotlin是使用其独有的copy()函数去修改属性值，而Java是使用setXXX()去修改
     */
    fun funData(){
        val mPreson = Preson("kotlin",1 , 2  )
        showToast(mPreson)
        val mNewUser = mPreson.copy(name = "new Kotlin")
        showToast(mNewUser)
        val mNewUser1 = mNewUser.copy(age = 20 )
        showToast(mNewUser1)
        val mNewUser2 = mPreson.copy( name = "Kotlin 2" , sex = 2 , age = 99 )
        showToast(mNewUser2)

        /**
         * 系统会默认自动根据参数的个数生成component1() ... componentN()函数。其...,componentN()函数就是用于解构声明的
         */
        val (name,age , sex ) = mNewUser1
        println("name = $name \t age = $age  \t sex = $sex ")
        val (name1,sex1,age1) = mNewUser2
        println("name = $name1 \t sex = $sex1 \t age = $age1 ")


        val pair = Pair(1,2)        // 实例
        val triple = Triple(1,2,3)  // 实例
        println("$pair \t $triple") // 打印：即调用了各自的toString()方法
        println(pair.toList())      // 转换成List集合
        println(triple.toList())    // 转换成List集合
        println(pair.to(3))         // Pair类特有: 其作用是把参数Pair类中的第二个参数替换
    }

    /**
     *  接口类的使用 ， 属性的单独使用
     */
    fun funJkLSY(){
        var calzzd : TestClazzD = TestClazzD(2,3)
        showToast( "接口属性的使用 $calzzd " )
        showToast( "接口属性的使用1 ${calzzd.num1 } " )
        showToast( "接口属性的使用2 ${calzzd.num2} " )

        showToast( "接口属性的使用3 ${calzzd.num3} " )
        showToast( "接口属性的使用4 ${calzzd.num4} " )


    }

    /**
     *  枚举的使用
     */
    private fun funEnumOperator(){
        showToast("name = " + TestEnumColor.RED.name + "\t  ordinal = " + TestEnumColor.RED.ordinal  + "\t String = " + TestEnumColor.RED.toString() + "\t hashCode = " + TestEnumColor.RED.hashCode())
        showToast("name = " + TestEnumColor.WHITE.name + "\tordinal = " + TestEnumColor.WHITE.ordinal+ "\t String = " + TestEnumColor.WHITE.toString() )
        showToast("name = " + TestEnumColor.BLACK.name + "\tordinal = " + TestEnumColor.BLACK.ordinal+ "\t String = " + TestEnumColor.BLACK.toString() )
        showToast("name = " + TestEnumColor.GREEN.name + "\tordinal = " + TestEnumColor.GREEN.ordinal+ "\t String = " + TestEnumColor.GREEN.toString() )
        showToast("name = " + TestEnumColor.BLUE.name + "\t ordinal = " + TestEnumColor.BLUE.ordinal + "\t String = " + TestEnumColor.BLUE.toString() )


        showToastD(enumValues<TestEnumColor>().joinToString { it.name })
        showToast(enumValueOf<TestEnumColor>("BLUE"))


        showToastA(TestEnumColor.valueOf("RED"))
        showToastA(TestEnumColor.values()[0])
        showToastA(TestEnumColor.values()[1])
        showToastA(TestEnumColor.values()[2])
        showToastA(TestEnumColor.values()[3])

    }

    /**
     * 枚举常量匿名类
     */
    private fun funEnumInner(){
        TestEnumColor1.BLACK.print()
    }

    private fun funClassFG(){
        var tcc :TestClassC = TestClassC()
        tcc.test1()
    }


    private fun funExtend() {
        var mDemoIn = DemoIn()
        showToast( "继承得到值：  ${mDemoIn.getId() }" )
    }


    private fun funClassNew() {
        var mTestBean0 : TestBean = TestBean( 0 )
        var mTestBean2 : TestBean2 = TestBean2()
        var mTestBean3 = TestBean( 301 , "次级构造函数" )
    }


    /**
    后期初始化属性
    通常，声明为非空类型的属性必须在构造函数中进行初始化。然而，这通常不方便。例如，可以通过依赖注入或单元测试的设置方法初始化属性。
    在这种情况下，不能在构造函数中提供非空的初始值设置，但是仍然希望在引用类的正文中的属性时避免空检查。故而，后期初始化属性就应运而生了。
     */
    var mTestBean1 : TestBean ?= null
    lateinit var mTestBean2 : TestBean
    private fun funLateinit() {
        mTestBean1
        mTestBean2
    }


    /***
    四种修饰符的说明
    public修饰符表示 公有 。此修饰符的范围最大。当不声明任何修饰符时，系统会默认使用此修饰符。
    internal修饰符表示 模块 。对于模块的范围在下面会说明。
    protected修饰符表示 私有+子类。值得注意的是，此修饰符不能用于顶层声明，在下面可以看到。
    private修饰符表示 私有 。此修饰符的范围最小，即可见性范围最低。
     */
    fun funPublic(){

    }



}
