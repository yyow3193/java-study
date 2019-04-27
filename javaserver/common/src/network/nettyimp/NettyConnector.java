package network.nettyimp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import network.INetConnector;
import network.INetHandler;

import java.util.concurrent.TimeUnit;

public class NettyConnector implements INetConnector {
    static final boolean SSL = System.getProperty("ssl") != null;

    private EventLoopGroup bossEventLoop;
    private Bootstrap b = null;
    private String ip;
    private int port;
    private INetHandler iNetHandler;

    public NettyConnector(String ip,
                          int port,
                          EventLoopGroup bossEventLoop,
                          INetHandler iNetHandler
                          ) {
        this.bossEventLoop = bossEventLoop;
        this.ip = ip;
        this.port = port;
        this.iNetHandler = iNetHandler;
    }

    public void doConnect() {
        ChannelFuture future = null;
        try {
            future = b.connect(this.ip, this.port);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture f) throws Exception {
                    if (f.isSuccess()) {
                        System.out.println("重新连接服务器成功");

                    } else {
                        System.out.println("重新连接服务器失败");
                        //  3秒后重新连接
                        f.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                doConnect();
                            }
                        }, 3, TimeUnit.SECONDS);
                    }
                }
            });
            future.sync();

        } catch (Exception e) {
            e.printStackTrace();
            //future.addListener(channelFutureListener);
            System.out.println("关闭连接");
        }

    }

    @Override
    public void start() throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        this.b = new Bootstrap();
        b.group(this.bossEventLoop)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        if (sslCtx != null) {
                            p.addLast(sslCtx.newHandler(ch.alloc(), ip, port));
                        }
                        p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(new ProtocolHeaderEncode());
                        p.addLast(new NettyNetHandler(iNetHandler));
                    }
                });

        // Start the client.
        doConnect();
    }
}
