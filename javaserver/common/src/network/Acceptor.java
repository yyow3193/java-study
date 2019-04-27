package network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.channel.socket.SocketChannel;


public class Acceptor {

    static final boolean SSL = System.getProperty("ssl") != null;

    private String ip;
    private int port;
    private EventLoopGroup bossEventLoop;
    private EventLoopGroup workEventLoop;
    private AChannelManager channelManager;

    public Acceptor(String ip, int port, AChannelManager channelManager, EventLoopGroup bossEventLoop, EventLoopGroup workEventLoop) {
        this.ip = ip;
        this.port = port;
        this.bossEventLoop = bossEventLoop;
        this.workEventLoop = workEventLoop;
        this.channelManager = channelManager;
    }

    public void start() throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }
        // Configure the server.
        ServerBootstrap b = new ServerBootstrap();
        b.group(this.bossEventLoop, this.workEventLoop)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        if (sslCtx != null) {
                            p.addLast(sslCtx.newHandler(ch.alloc()));
                        }
                        p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                        p.addLast(new ProtocolHeaderDecoder());
                        p.addLast(channelManager.newChannelHandler());
                    }
                });

        try {
            b.bind(this.ip, this.port).sync();
            System.out.println("--------accept thread begin-------");
        } catch (InterruptedException e) {

        }
    }

}
