package com.zzt.netty.util;

import android.util.Log;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: zeting
 * @date: 2020/12/10
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

    private final static String TAG = NettyClientHandler.class.getSimpleName();
    private NettyListener listener;

    public NettyClientHandler(NettyListener listener) {
        this.listener = listener;
    }

    /**
     * IdleStateHandler 空闲时间超时会触发这里
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        Log.e(TAG, "userEventTriggered 超时没有连接过 ， 发个心跳");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyClient.getInstance().setConnectStatus(true);
        Log.e(TAG, "连接成功");
        listener.showMessage("连接成功");
        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_SUCCESS);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG, "连接失败 需要重连");
        listener.showMessage("连接失败 需要重连");
        NettyClient.getInstance().setConnectStatus(false);
        listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_CLOSED);
        NettyClient.getInstance().reconnect();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Log.e(TAG, "读取发过来的消息 Object:" + msg);
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        Log.e(TAG, "长连接发送消息 Object:" + o);
        listener.onMessageResponse(o);
    }

    //异常回调,默认的exceptionCaught只会打出日志，不会关掉channel
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
