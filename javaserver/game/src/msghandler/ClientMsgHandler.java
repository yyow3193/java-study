package msghandler;

import protocols.message.Message;

public class ClientMsgHandler {
    public void process(Message.CRequest cRequest){
        System.out.println(cRequest.getA());
        System.out.println(cRequest.getB());
    }
}
