package network.nettyimp;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import network.INetHandlerContext;
import protocols.engine.Engine;

public class NetHandlerContext implements INetHandlerContext {

    private ChannelHandlerContext ctx;

    public NetHandlerContext(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }

    @Override
    public void writeAndFlush(int protocolType, GeneratedMessageV3 msg){
        Engine.header.Builder builder = Engine.header.newBuilder();
        builder.setType(protocolType);
        builder.setBytes(msg.toByteString());
        ctx.writeAndFlush(builder.build());
    }
}
