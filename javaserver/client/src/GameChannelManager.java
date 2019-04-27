import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import network.AChannelManager;
import protocols.ProtocolStub;
import protocols.message.Message;

public class GameChannelManager extends AChannelManager {

    public ChannelInboundHandler newChannelHandler(){
        return new ChannelInboundHandler() {
            @Override
            public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {

            }

            @Override
            public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {

            }

            @Override
            public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
                System.out.println("channelActive " + channelHandlerContext.toString());
                Message.CRequest.Builder builder = Message.CRequest.newBuilder();
                builder.setA(111);
                builder.setB(222);
                Message.CRequest request = builder.build();
                send(channelHandlerContext, ProtocolStub.CRequest, request);
            }

            @Override
            public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {

            }

            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

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
