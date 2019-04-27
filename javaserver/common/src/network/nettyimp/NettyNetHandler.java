package network.nettyimp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import network.INetHandler;
import network.INetHandlerContext;
import protocols.engine.Engine;

public class NettyNetHandler implements ChannelInboundHandler {

    private INetHandler iNetHandler;

    public NettyNetHandler(INetHandler iNetHandler){
        this.iNetHandler = iNetHandler;
    }

    INetHandlerContext toNetHandlerContext(ChannelHandlerContext channelHandlerContext){
        return new NetHandlerContext(channelHandlerContext);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        System.out.println("channelRegistered " + channelHandlerContext.toString() );

        INetHandlerContext INetHandlerContext = toNetHandlerContext(channelHandlerContext);
        iNetHandler.netRegistered(INetHandlerContext);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        System.out.println("channelUnregistered " + channelHandlerContext.toString() );

        INetHandlerContext INetHandlerContext = toNetHandlerContext(channelHandlerContext);
        iNetHandler.netUnregistered(INetHandlerContext);


    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        System.out.println("channelActive " + channelHandlerContext.toString());
        INetHandlerContext INetHandlerContext = toNetHandlerContext(channelHandlerContext);

        iNetHandler.netActive(INetHandlerContext);
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        Engine.header header = (Engine.header)o;

        iNetHandler.netRead(toNetHandlerContext(channelHandlerContext), header);

        /*Class< ? extends AbstractMessage> clz = ProtocolStub.type2Protocol.get(header.getType());

        MsgHandlerRegistry msgHandlerRegistry = MsgHandlerRegistryFactory.INSTANCE.get();
        MsgHandler msgHandler = msgHandlerRegistry.getHandler(clz);
        Constructor constructor = clz.getDeclaredConstructor();
        constructor.setAccessible(true);
        AbstractMessage msg = (AbstractMessage)((AbstractMessage)constructor.newInstance()).getParserForType().parseFrom(header.getBytes());
        msgHandler.process(msg);*/
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
}
