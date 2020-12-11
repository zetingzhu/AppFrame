package com.zzt.netty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zzt.netty.util.NettyClient;
import com.zzt.netty.util.NettyListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;


public class MainActivity extends AppCompatActivity implements NettyListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    private ScheduledExecutorService mScheduledExecutorService;
    String HEART_STR_REQUEST = "-1$_";

    private void shutdown() {
        if (mScheduledExecutorService != null) {
            mScheduledExecutorService.shutdown();
            mScheduledExecutorService = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendMsg = "{\"type\":\"rtc\",\"p\":{\"codes\":\"XTREND|USDCAD,XTREND|GBPUSD\"}}$_";
                sendMsg(sendMsg);
            }
        });

        // 自定义心跳，每隔30秒向服务器发送心跳包
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendMsg(HEART_STR_REQUEST);
            }
        }, 30, 30, TimeUnit.SECONDS);
    }

    public void sendMsg(String msg) {
        NettyClient.getInstance().sendMsgToServer(msg, new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                Log.e(TAG, "写入数据状态：" + future.isSuccess());
                if (future.cause() != null) {
                    future.cause().printStackTrace();
                }
                if (future.isSuccess()) {
                } else {
                    connect();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        NettyClient.getInstance().setListener(this);
        connect();

    }

    private void connect() {
        Log.e(TAG, "连接状态 connect:" + NettyClient.getInstance().getConnectStatus());
        if (!NettyClient.getInstance().getConnectStatus()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NettyClient.getInstance().connect();//连接服务器
                }
            }).start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shutdown();
    }


    @Override
    public void onMessageResponse(Object push) {
        Log.e(TAG, "推送信息" + push);
    }

    @Override
    public void onServiceStatusConnectChanged(int statusCode) {
        Log.e(TAG, String.format("连接状态:%d", statusCode));
        if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
        } else {
            Log.e(TAG, "连接失败");
        }
    }

    @Override
    public void showMessage(String msg) {
        Log.e(TAG, "提示信息" + msg);
    }
}