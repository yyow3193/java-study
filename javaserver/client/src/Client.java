import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import network.Connector;

public class Client {

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        try{
            Connector connector = new Connector("0.0.0.0", 8007, new GameChannelManager(), group);
            connector.start();

            while (true){
                Thread.sleep(100000000);
            }
        }finally {
            group.shutdownGracefully();
        }
    }
}
