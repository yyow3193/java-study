package msghandler;

public enum MsgHandlerRegistryFactory {
    INSTANCE;

    private static final ReflectMsgHandlerRegistry defaultMsgHandlerRegistry = new ReflectMsgHandlerRegistry(false);
    // 6v6公平竞技场
    private static final ReflectMsgHandlerRegistry pvp6MsgHandlerRegistry = new ReflectMsgHandlerRegistry(false);

    static {
        defaultMsgHandlerRegistry.register(new ClientMsgHandler());
        pvp6MsgHandlerRegistry.inherit(defaultMsgHandlerRegistry);
    }

    public MsgHandlerRegistry get() {
        boolean isPvp6 = false;
        if (isPvp6) {
            return pvp6MsgHandlerRegistry;
        }
        return defaultMsgHandlerRegistry;
    }
}
