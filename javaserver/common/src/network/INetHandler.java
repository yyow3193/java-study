package network;

import protocols.engine.Engine;

public interface INetHandler {

    public void netRegistered(INetHandlerContext channelHandlerContext) throws Exception;

    public void netUnregistered(INetHandlerContext channelHandlerContext) throws Exception;

    public void netActive(INetHandlerContext channelHandlerContext) throws Exception;

    public void netRead(INetHandlerContext channelHandlerContext, Engine.header header) throws Exception;

    public void netInactive(INetHandlerContext channelHandlerContext) throws Exception;

    public void netReadComplete(INetHandlerContext channelHandlerContext) throws Exception;

    public void userEventTriggered(INetHandlerContext channelHandlerContext, Object o) throws Exception;

    public void netWritabilityChanged(INetHandlerContext channelHandlerContext) throws Exception;

    public void exceptionCaught(INetHandlerContext channelHandlerContext, Throwable throwable) throws Exception;

    public void handlerAdded(INetHandlerContext channelHandlerContext) throws Exception;

    public void handlerRemoved(INetHandlerContext channelHandlerContext) throws Exception;
}
