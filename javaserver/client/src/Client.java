import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import network.INetConnector;
import network.nettyimp.NettyConnector;

public class Client {

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        try{
            INetConnector nettyConnector = new NettyConnector("0.0.0.0", 8007, group, new ServerNetHandler());
            nettyConnector.start();

            while (true){
                Thread.sleep(100000000);
            }
        }finally {
            group.shutdownGracefully();
        }
    }
}
