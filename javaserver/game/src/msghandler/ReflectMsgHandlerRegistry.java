package msghandler;

import com.google.protobuf.AbstractMessage;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ReflectMsgHandlerRegistry extends MsgHandlerRegistry {
    public ReflectMsgHandlerRegistry(boolean isSupportHandlerOverride) {
        super(isSupportHandlerOverride);
    }

    public void register(Object msgHandlerObj) {
        Class<?> parentClazz = msgHandlerObj.getClass();
        for (Method method : parentClazz.getMethods()) {
            Parameter[] parameters = method.getParameters();
            if (parameters.length != 1) {
                continue;
            }
            if (!AbstractMessage.class.isAssignableFrom(parameters[0].getType())) {
                continue;
            }

            String name = parentClazz.getName() + method.getName();
            method.setAccessible(true);
            ReflectMsgHandler msgHandler = new ReflectMsgHandler(name, msgHandlerObj, method);
            @SuppressWarnings("unchecked")
            Class<? extends AbstractMessage> clazz = (Class<? extends AbstractMessage>) parameters[0].getType();

            register(clazz, msgHandler);
        }
    }

}
