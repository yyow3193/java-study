package network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocols.engine.Engine;

public class ProtocolHeaderEncode extends MessageToByteEncoder<Engine.header> {

    protected void encode(ChannelHandlerContext ctx, Engine.header msg, ByteBuf out) throws Exception{
       out.writeInt(msg.toByteString().size()); // length
       out.writeBytes(msg.toByteArray());
    }
}
