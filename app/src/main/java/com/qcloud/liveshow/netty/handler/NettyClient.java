package com.qcloud.liveshow.netty.handler;

import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.netty.callback.ResponseListener;
import com.qcloud.qclib.rxutil.RxScheduler;
import com.qcloud.qclib.rxutil.task.IOTask;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * 类说明：长链接
 * Author: Kuzan
 * Date: 2017/11/1 11:50.
 */
public class NettyClient extends ClientImpl {
    /**连接Socket*/
    private SocketChannel mSocketChannel;
    /**重连Timber*/
    private Disposable mDisposable;
    /**线程池*/
    private NioEventLoopGroup mEventLoopGroup;
    /**响应数据处理*/
    private ResponseChannelHandler mHandler;
    /**添加数据队列*/
    private BlockingDeque<String> mMessageSupers = new LinkedBlockingDeque<>();
    /**是否已清*/
    private boolean isDestroy = false;
    /**服务器ip*/
    private String host;
    /**服务器端口*/
    private int port;

    @Override
    public void onConnectChange(boolean isConnect) {
        if (!getConnectState() && isConnect) {
            reconnect();
        }
    }

    private static class ClientHelper {
        final static NettyClient CLIENT = new NettyClient();
    }

    private NettyClient() {
        mEventLoopGroup = new NioEventLoopGroup();
        mHandler = new ResponseChannelHandler();
        mHandler.disposeJson();
        request();
    }

    @Override
    public void Initialization() {
        reconnect();
    }

    public void InitializationWithWorkThread() {
        if (mSocketChannel != null && mSocketChannel.isOpen() && mSocketChannel.isActive()) {
            Timber.i("socket 已经建立了链接");
            return;
        }

        isDestroy = false;

        if (host == null || port == 0) {
            return;
        }

        Bootstrap bootstrap = new Bootstrap();
        if (mEventLoopGroup == null) {
            mEventLoopGroup = new NioEventLoopGroup();
        }
        try {
            bootstrap
                    .channel(NioSocketChannel.class)
                    .group(mEventLoopGroup)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(60, 60, 90));
                            // 编解码格式
                            ch.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));

                            // 处理粘包
                            ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(1024*10,  Delimiters.lineDelimiter()));
                            ch.pipeline().addLast(new CloseChannelHandler(NettyClient.this));
                            ch.pipeline().addLast(mHandler);
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            if (future.isSuccess()) {
                mSocketChannel = (SocketChannel) future.channel();
                Timber.i("SocketChannel 连接成功");
                Timber.i("SocketChannel.host = %s, SocketChannel.port = %d", host, port);
                //连接成功后鉴权
                auth();

                if (mDisposable != null && !mDisposable.isDisposed()) {
                    mDisposable.dispose();
                }
            } else {
                try {
                    //预防连接数超出系统上线
                    future.channel().disconnect();
                    future.channel().close();
                    mEventLoopGroup.rebuildSelectors();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                throw new InterruptedException("connection fail.");
            }
        } catch (Exception e) {
            Timber.i("SocketChannel连接失败");
            Timber.i(e);
            onDestroy();
            //重置,重启
            reconnect();
        }
    }

    /**
     * 鉴权
     * */
    public  void auth() {
        RxScheduler.doOnIOThread((IOTask<Void>) () -> new IMModelImpl().auth());
    }

    /**
     * 添加发送数据
     * */
    @Override
    public void request(String message) {
        try {
            mMessageSupers.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据到服务器
     * */
    private void request() {
        RxScheduler.doOnIOThread((IOTask<Void>) () -> {
            while (!isDestroy) {
                try {
                    String message = mMessageSupers.take();
                    if (message != null && getConnectState()) {
                        Timber.e("write: " + message);
                        mSocketChannel.writeAndFlush(message).sync();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean getConnectState() {
        return mSocketChannel != null && mSocketChannel.isActive();
    }

    @Override
    protected void restart(boolean restart) {
        reset();
        if (!getConnectState() && restart && !isDestroy && ClientNetWork.newInstance().isNetworkAvailable()) {
            reconnect();
        }
    }

    @Override
    protected void close() {
        mSocketChannel = null;
    }

    @Override
    public void onDestroy() {
        isDestroy = true;
        mMessageSupers.clear();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        if (mEventLoopGroup != null) {
            mEventLoopGroup.rebuildSelectors();
        }
        reset();
    }

    @Override
    public ClientImpl addResponseListener(ResponseListener response) {
        mHandler.addListener(response);
        return this;
    }

    @Override
    public ClientImpl addResponseListener(ResponseChannelHandler response) {
        mHandler = response;
        return this;
    }

    @Override
    public ClientImpl bind(String host, int port) {
        this.host = host;
        this.port = port;
        return this;
    }

    private void reset() {
        if (mSocketChannel != null) {
            mSocketChannel.close();
        }
    }

    /**
     * 重连
     * */
    private void reconnect() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            return;
        }
        mDisposable = Observable.interval(2, TimeUnit.SECONDS).subscribe(aLong -> {
            Timber.i("socket 连接2s后建立");
            InitializationWithWorkThread();
        });
    }

    public static ClientImpl newInstances() {
        return ClientHelper.CLIENT;
    }
}
