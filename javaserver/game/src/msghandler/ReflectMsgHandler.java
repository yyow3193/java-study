package msghandler;

import com.google.protobuf.AbstractMessage;

import java.lang.reflect.Method;

public class ReflectMsgHandler implements MsgHandler {
    private final String name;
    private final Object obj;
    private final Method method;

    public ReflectMsgHandler(String name, Object obj, Method method) {
        this.name = name;
        this.obj = obj;
        this.method = method;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void process(AbstractMessage p) throws Exception {
        method.invoke(obj, p);
    }

}