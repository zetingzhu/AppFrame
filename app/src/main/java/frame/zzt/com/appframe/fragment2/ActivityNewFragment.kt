package frame.zzt.com.appframe.fragment2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.add
import androidx.fragment.app.commit
import frame.zzt.com.appframe.R

/**
 * @author: zeting
 * @date: 2020/7/20
 *
这里是新的fragment 使用方法
FragmentScenario，Fragment 的测试框架
FragmentFactory，统一的 Fragment 实例化组件
FragmentContainerView，Fragment 专属视图容器
OnBackPressedDispatcher，帮助您在 Fragment 或其他组件中处理返回按钮事件
链接：https://www.jianshu.com/p/b8fb92ddbcf2
 */
class ActivityNewFragment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_fragment)
        initView()
    }

    private fun initView() {
        supportFragmentManager.commit {
            add<FragmentNew>(R.id.fcv_view)
        }

        supportFragmentManager.commit {
            add<FragmentNew1>(R.id.fcv_view_1)
        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment2 = FragmentNew2()
        fragmentTransaction.add(R.id.fcv_view_2, fragment2)
        fragmentTransaction.commit()
    }

}