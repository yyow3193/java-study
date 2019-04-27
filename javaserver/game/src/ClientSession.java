import io.netty.channel.ChannelHandlerContext;

public class ClientSession {
    private ChannelHandlerContext ctx;

    public ClientSession(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }
}
