package msghandler;


import com.google.protobuf.AbstractMessage;

public interface MsgHandler {

    default String getName() {
        return "";
    }

    void process(AbstractMessage p) throws Exception;

}
