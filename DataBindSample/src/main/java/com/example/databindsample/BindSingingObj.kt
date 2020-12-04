package com.example.databindsample

import androidx.databinding.BaseObservable
import java.io.Serializable

/**
 * @author: zeting
 * @date: 2020/12/4
 *
 */
class BindSingingObj(
        // 奖励金
        var creditAmount: String,
        // 连续签到天数
        var continuousDay: Int,
        // 签到日期的数据
        var modelList: MutableList<BindSingingItemObj>? = null
) : Serializable, BaseObservable() {
    companion object {
        // 当天签到状态   0 未签到 ,1 已签到, 2 申请补签，3 多日断签
        var STATUS_SIGNING_TODAY_NOT = 0
        var STATUS_SIGNING_TODAY_SIGNED = 1
        var STATUS_SIGNING_TODAY_MISSING = 2
        var STATUS_SIGNING_TODAY_MULTIDAY = 3
    }

    // 签到状态
    var status: Int = STATUS_SIGNING_TODAY_NOT

    // 日历权限
    var calendarPermissions: Boolean = false
}