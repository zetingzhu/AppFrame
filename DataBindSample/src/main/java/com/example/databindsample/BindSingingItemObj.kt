package com.example.databindsample

import java.io.Serializable

/**
 * @author: zeting
 * @date: 2020/12/4
 *
 */
class BindSingingItemObj(
        var sort: Long,//排序
        var day: String, // 天
        var credit: String,  //奖励
        var type: Int,// 奖励方式  0普通奖励，1银宝箱,2金宝箱
        var status: Int//签到状态 0 未签到 1已签到 2 漏签,

) : Serializable {
    companion object {
        //   奖励方式 0普通奖励，1银宝箱,2金宝箱
        val TYPE_SIGN_SILVER = 1
        val TYPE_SIGN_GOLD = 2
        val TYPE_SIGN_DEFAULT = 0

        // 签到状态 签到状态 0 未签到 1已签到 2 漏签
        var STATUS_SIGNING_SUCCESS = 1
        var STATUS_SIGNING_FAIL = 2
        var STATUS_SIGNING_NOT = 0
    }

}