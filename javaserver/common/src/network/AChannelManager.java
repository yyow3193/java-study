package network;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import protocols.engine.Engine;

public abstract class AChannelManager {

    public abstract ChannelInboundHandler newChannelHandler();

    /**
     * @param ctx
     * @param protocolType  协议号
     * @param msg           协议
     */
    public void send(ChannelHandlerContext ctx, int protocolType, GeneratedMessageV3 msg){
        Engine.header.Builder builder = Engine.header.newBuilder();
        builder.setType(protocolType);
        builder.setBytes(msg.toByteString());
        ctx.writeAndFlush(builder.build());
    }
}
