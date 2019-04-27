import com.google.protobuf.AbstractMessage;
import msghandler.MsgHandler;
import msghandler.MsgHandlerRegistry;
import msghandler.MsgHandlerRegistryFactory;
import network.INetHandler;
import network.INetHandlerContext;
import protocols.ProtocolStub;
import protocols.engine.Engine;

import java.lang.reflect.Constructor;

public class ClientNetHandler implements INetHandler {



    public ClientNetHandler(){

    }

    public void netRegistered(INetHandlerContext channelHandlerContext) throws Exception{
        System.out.println("ClientNetHandler:channelRegistered " + channelHandlerContext.toString() );
    }


    public void netUnregistered(INetHandlerContext channelHandlerContext) throws Exception{

    }



    public void netActive(INetHandlerContext channelHandlerContext) throws Exception{

    }


    public void netInactive(INetHandlerContext channelHandlerContext) throws Exception{

    }


    public void netRead(INetHandlerContext channelHandlerContext, Engine.header header) throws Exception{
        MsgHandlerRegistry msgHandlerRegistry = MsgHandlerRegistryFactory.INSTANCE.get();
        Class< ? extends AbstractMessage> clz = ProtocolStub.type2Protocol.get(header.getType());
        MsgHandler msgHandler = msgHandlerRegistry.getHandler(clz);
        Constructor constructor = clz.getDeclaredConstructor();
        constructor.setAccessible(true);
        AbstractMessage msg = (AbstractMessage)((AbstractMessage)constructor.newInstance()).getParserForType().parseFrom(header.getBytes());
        msgHandler.process(msg);
    }

    public void netReadComplete(INetHandlerContext channelHandlerContext) throws Exception{

    }

    public void userEventTriggered(INetHandlerContext channelHandlerContext, Object o) throws Exception{

    }

    public void netWritabilityChanged(INetHandlerContext channelHandlerContext) throws Exception{

    }

    public void exceptionCaught(INetHandlerContext channelHandlerContext, Throwable throwable) throws Exception{

    }

    public void handlerAdded(INetHandlerContext channelHandlerContext) throws Exception{

    }

    public void handlerRemoved(INetHandlerContext channelHandlerContext) throws Exception{

    }

}
