package com.example.databindsample

import android.content.Context
import com.example.databindsample.R


/**
 * @author: zeting
 * @date: 2020/6/21
 * 签到数据处理工具类
 */
object SigningDataUtil {


    /**
     * 已经签到，未签订，漏签
     *
     * @param obj
     * @return
     */
    @JvmStatic
    fun getStatusSignBtn(obj: BindSingingObj?): Int {
        if (obj?.status == BindSingingObj.STATUS_SIGNING_TODAY_SIGNED) {
            return R.drawable.sign_status_btn_signed
        } else if (obj?.status == BindSingingObj.STATUS_SIGNING_TODAY_MISSING) {
            return R.drawable.sign_status_btn_miss
        }
        return R.drawable.sign_status_btn_not
    }

    /**
     * 根据签到按钮，显示按钮文本
     */
    @JvmStatic
    fun getStatusSignBtnText(obj: BindSingingObj?): Int {
        if (obj?.status == BindSingingObj.STATUS_SIGNING_TODAY_SIGNED) {
            return R.string.s40_12
        } else if (obj?.status == BindSingingObj.STATUS_SIGNING_TODAY_MISSING) {
            return R.string.s40_15
        }
        return R.string.s40_1
    }

    /**
     * 根据状态显示图片
     *
     * @param obj
     * @return
     */
    @JvmStatic
    fun getStatusShowImg(obj: BindSingingItemObj?): Int {
        if (obj?.status == BindSingingItemObj.STATUS_SIGNING_SUCCESS) {
            return R.drawable.sign_in_success
        } else if (obj?.status == BindSingingItemObj.STATUS_SIGNING_FAIL) {
            return R.drawable.sign_in_faile
        }
        if (obj?.type == BindSingingItemObj.TYPE_SIGN_SILVER) {
            return R.drawable.sign_in_3rd
        } else if (obj?.type == BindSingingItemObj.TYPE_SIGN_GOLD) {
            return R.drawable.sign_in_7th
        }
        return R.drawable.sign_in_default
    }

    /**
     * 是否显示金额
     */
    @JvmStatic
    fun getStatusShowAmount(obj: BindSingingItemObj?): Boolean {
        if (obj?.status == BindSingingItemObj.STATUS_SIGNING_NOT) {
            if (obj?.type == BindSingingItemObj.TYPE_SIGN_SILVER || obj?.type == BindSingingItemObj.TYPE_SIGN_GOLD) {
                return false
            }
        }
        return true
    }

    /**
     * 获取文字颜色
     *
     * @param obj
     * @return
     */
    @JvmStatic
    fun getStatusSetTextColor(obj: BindSingingItemObj?): Int {
        return if (obj?.status == BindSingingItemObj.STATUS_SIGNING_SUCCESS) {
            R.color.color_48E2C2
        } else R.color.color_7280A6
    }


    /**
     * 设置today背景颜色
     */
    @JvmStatic
    fun getStatusSetTodayTextColor(context: Context, obj: BindSingingItemObj?): Int {
        var today: String = context.resources.getString(R.string.s40_16)
        return if (obj?.day == today) {
            R.color.app_btn_bgcolor_v3
        } else {
            R.color.app_trade_gray
        }
    }

}
