package com.zzt.netty.util;


import android.util.Log;


import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author: zeting
 * @date: 2020/12/10
 * Netty客户端
 */
public class NettyClient {

    private final static String TAG = NettyClient.class.getSimpleName();
    private static NettyClient nettyClient = new NettyClient();

    private EventLoopGroup group;

    private NettyListener listener;

    private Channel channel;

    private boolean isConnect = false;

    private int reconnectNum = Integer.MAX_VALUE;

    private long reconnectIntervalTime = 5000;


    public static NettyClient getInstance() {
        return nettyClient;
    }

    public synchronized NettyClient connect() {
        if (!isConnect) {
            group = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap().group(group)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .channel(NioSocketChannel.class)
                        .handler(new NettyClientInitializer(listener));
                bootstrap.connect(NettyConfig.HOST, NettyConfig.TCP_PORT).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            isConnect = true;
                            channel = channelFuture.channel();
                        } else {
                            isConnect = false;
                        }
                    }
                }).sync();


//                Bootstrap bootstrap = new Bootstrap();
//                //设置NioSocketChannel
//                bootstrap.channel(NioSocketChannel.class);
//                //设置心跳机制
//                bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
//                //设置eventLoopGroup
//                bootstrap.group(group);
//                //设置连接ip  端口
//                bootstrap.remoteAddress(NettyConfig.HOST, NettyConfig.TCP_PORT);
//                bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//                    @Override
//                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(2048, Unpooled.copiedBuffer("$_".getBytes())));
//                        socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("utf-8")));
//                        socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("utf-8")));
//                        socketChannel.pipeline().addLast("idleStateHandler", new IdleStateHandler(0, 0, 60));
//                        socketChannel.pipeline().addLast("timeOutHandler", new NettyTimeOutHandler());
//                        socketChannel.pipeline().addLast(new NettyClientHandler(new NettyListener() {
//                            @Override
//                            public void onMessageResponse(Object push) {
//
//                            }
//
//                            @Override
//                            public void onServiceStatusConnectChanged(int statusCode) {
//
//                            }
//
//                            @Override
//                            public void showMessage(String msg) {
//
//                            }
//                        }));
//
//                    }
//                });
//                //连接服务端
//                ChannelFuture future = bootstrap.connect().sync();
//                if (future.isSuccess()) {
//                    channel = (SocketChannel) future.channel();
//                }
            } catch (Exception e) {
                e.printStackTrace();
                listener.onServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
                reconnect();
            }
        }
        return this;
    }

    public void disconnect() {
        group.shutdownGracefully();
    }

    public void reconnect() {
        if (reconnectNum > 0 && !isConnect) {
            reconnectNum--;
            try {
                Thread.sleep(reconnectIntervalTime);
            } catch (InterruptedException e) {
            }
            Log.e(TAG, "重新连接");
            disconnect();
            connect();
        } else {
            disconnect();
        }
    }

    public boolean sendMsgToServer(String data, ChannelFutureListener listener) {
        if (channel != null) {
            Log.e(TAG, "服务器连接状态by：" + channel.isActive());
        }
        boolean flag = channel != null && isConnect;
        if (flag) {
            channel.writeAndFlush(data).addListener(listener);
        }
        return flag;
    }

    public void setReconnectNum(int reconnectNum) {
        this.reconnectNum = reconnectNum;
    }

    public void setReconnectIntervalTime(long reconnectIntervalTime) {
        this.reconnectIntervalTime = reconnectIntervalTime;
    }

    public boolean getConnectStatus() {
        return isConnect;
    }

    public void setConnectStatus(boolean status) {
        this.isConnect = status;
    }

    public void setListener(NettyListener listener) {
        this.listener = listener;
    }
}
