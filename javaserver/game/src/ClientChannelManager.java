import com.google.protobuf.AbstractMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import msghandler.MsgHandler;
import msghandler.MsgHandlerRegistry;
import msghandler.MsgHandlerRegistryFactory;
import network.AChannelManager;
import protocols.ProtocolStub;
import protocols.engine.Engine;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class ClientChannelManager extends AChannelManager {

    private Map<ChannelHandlerContext, ClientSession> channel2ClientSession = new HashMap<>();
    private Map<Long, ChannelHandlerContext> roleid2Channel = new HashMap<>();

    public ClientChannelManager(){

    }

    @Override
    public ChannelInboundHandler newChannelHandler(){
        return new ChannelInboundHandler() {
            @Override
            public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
                System.out.println("ClientChannelManager:channelRegistered " + channelHandlerContext.toString() );
            }

            @Override
            public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
                System.out.println("ClientChannelManager:channelUnregistered " + channelHandlerContext.toString() );
            }

            @Override
            public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {

            }

            @Override
            public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {

            }

            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
                Engine.header header = (Engine.header)o;
                MsgHandlerRegistry msgHandlerRegistry = MsgHandlerRegistryFactory.INSTANCE.get();
                Class< ? extends AbstractMessage> clz = ProtocolStub.type2Protocol.get(header.getType());
                MsgHandler msgHandler = msgHandlerRegistry.getHandler(clz);
                Constructor constructor = clz.getDeclaredConstructor();
                constructor.setAccessible(true);
                AbstractMessage msg = (AbstractMessage)((AbstractMessage)constructor.newInstance()).getParserForType().parseFrom(header.getBytes());
                msgHandler.process(msg);
            }

            @Override
            public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {

            }

            @Override
            public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

            }

            @Override
            public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

            }

            @Override
            public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                throwable.printStackTrace();
                channelHandlerContext.close();
            }

            @Override
            public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

            }

            @Override
            public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

            }
        };
    }

}
