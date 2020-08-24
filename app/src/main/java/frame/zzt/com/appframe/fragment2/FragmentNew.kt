package frame.zzt.com.appframe.fragment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import frame.zzt.com.appframe.R

/**
 * @author: zeting
 * @date: 2020/7/20
 * 新的界面
 */
class FragmentNew : Fragment() {
    // 这些是新fragment监听返回按键的
    val dispatcher by lazy { requireActivity().onBackPressedDispatcher }
    lateinit var callback: OnBackPressedCallback
    private lateinit var mRootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_new_view, container, false)

        return mRootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callback = dispatcher.addCallback(this) {
            Toast.makeText(activity, "11111", Toast.LENGTH_SHORT).show()
            onConfirm()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onConfirm() {
        callback.isEnabled = false
        dispatcher.onBackPressed()
    }

}