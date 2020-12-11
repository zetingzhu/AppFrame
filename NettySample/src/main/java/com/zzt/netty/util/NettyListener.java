package com.zzt.netty.util;


/**
 * @author: zeting
 * @date: 2020/12/10
 */
public interface NettyListener {

    public final static byte STATUS_CONNECT_SUCCESS = 1;

    public final static byte STATUS_CONNECT_CLOSED = 0;

    public final static byte STATUS_CONNECT_ERROR = 0;


    /**
     * 当接收到系统消息
     */
    void onMessageResponse(Object push);

    /**
     * 当服务状态发生变化时触发
     */
    void onServiceStatusConnectChanged(int statusCode);

    /**
     * 设置显示message
     *
     * @param msg
     */
    void showMessage(String msg);
}
