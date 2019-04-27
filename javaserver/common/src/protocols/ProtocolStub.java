package protocols;

import protocols.message.Message;

import java.util.HashMap;
import java.util.Map;

public class ProtocolStub {
    public static Map<Integer, Class> type2Protocol = new HashMap<>();

    public static final int CRequest = 1;

    static {
        type2Protocol.put(CRequest, Message.CRequest.class);
    }
}
