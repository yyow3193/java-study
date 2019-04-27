package network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocols.engine.Engine;

import java.util.List;

public class ProtocolHeaderDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
        byte []bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        Engine.header header = Engine.header.parseFrom(bytes);
        out.add(header);
    }
}
