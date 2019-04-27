package network;

import com.google.protobuf.GeneratedMessageV3;

public interface INetHandlerContext {

    void writeAndFlush(int protocolType, GeneratedMessageV3 msg);
}
