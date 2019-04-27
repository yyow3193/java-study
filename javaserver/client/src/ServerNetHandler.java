import network.INetHandler;
import network.INetHandlerContext;
import protocols.ProtocolStub;
import protocols.engine.Engine;
import protocols.message.Message;

public class ServerNetHandler implements INetHandler {



    public void netRegistered(INetHandlerContext channelHandlerContext) throws Exception{
        System.out.println("ClientNetHandler:channelRegistered " + channelHandlerContext.toString() );
    }


    public void netUnregistered(INetHandlerContext channelHandlerContext) throws Exception{

    }



    public void netActive(INetHandlerContext channelHandlerContext) throws Exception{

        Message.CRequest.Builder builder = Message.CRequest.newBuilder();
        builder.setA(111);
        builder.setB(222);
        Message.CRequest request = builder.build();

        channelHandlerContext.writeAndFlush(ProtocolStub.CRequest, request);
    }


    public void netInactive(INetHandlerContext channelHandlerContext) throws Exception{

    }


    public void netRead(INetHandlerContext channelHandlerContext, Engine.header header) throws Exception{

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
