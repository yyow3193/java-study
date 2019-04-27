package msghandler;


import com.google.protobuf.AbstractMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MsgHandlerRegistry {
    protected final Logger logger = Logger.getLogger(MsgHandlerRegistry.class.getName());
    private final boolean isSupportHandlerOverride;
    private final Map<Class<? extends AbstractMessage>, MsgHandler> originalHandlerMap = new HashMap<>();
    private volatile Map<Class<? extends AbstractMessage>, MsgHandler> handlerMap;

    public MsgHandlerRegistry(boolean isSupportHandlerOverride) {
        this.isSupportHandlerOverride = isSupportHandlerOverride;
        this.handlerMap = originalHandlerMap;
    }

    public void inherit(MsgHandlerRegistry parent) {
        parent.originalHandlerMap.forEach(this::register);
    }

    public void register(Class<? extends AbstractMessage> clazz, MsgHandler handler) {
        MsgHandler old = originalHandlerMap.get(clazz);
        if (old != null) {
            if (isSupportHandlerOverride) {
                //logger.debug("MsgHandler {} is override by {}", old.getName(), handler.getName());
            } else {
                throw new IllegalStateException("duplicated MsgHandler = " + clazz.getName() + ". old = " + old.getName() + ", new = " + handler.getName());
            }
        }
        originalHandlerMap.put(clazz, handler);
    }

    public void unregister(Class<? extends AbstractMessage> clazz) {
        originalHandlerMap.remove(clazz);
    }

    public MsgHandler getHandler(Class<? extends AbstractMessage> clazz) {
        return handlerMap.get(clazz);
    }

    public MsgHandler getOriginalHandler(Class<? extends AbstractMessage> clazz) {
        return originalHandlerMap.get(clazz);
    }

    /**
     * 运行对handler进行热更新
     * @param clazz
     * @param handler null表示删除，或者表示新增或者替换
     */
    public synchronized void hotUpdate(Class<? extends AbstractMessage> clazz, MsgHandler handler) {
        Map<Class<? extends AbstractMessage>, MsgHandler> newHandlerMap = new HashMap<>(handlerMap);
        MsgHandler old = handler == null ? newHandlerMap.remove(clazz) : newHandlerMap.put(clazz, handler);
        this.handlerMap = newHandlerMap;

        //logger.info("hotUpdateHandler, protocol={}, oldHandler={}, newHander={}", clazz.getName(), old == null ? "NULL" : old.getName(), handler == null ? "NULL" : handler.getName());
    }

    public interface HandlerT<T extends AbstractMessage> {

        void process(T p) throws Exception;

    }

    public <T extends AbstractMessage> void hotUpdate(String name, Class<T> clazz, HandlerT<T> handler) {
        hotUpdate(clazz, new MsgHandler() {

            @Override
            public String getName() {
                return name;
            }

            @SuppressWarnings("unchecked")
			@Override
            public void process(AbstractMessage p) throws Exception {
                handler.process((T)p);
            }
        });
    }
}
