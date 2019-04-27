package msghandler;

public enum MsgHandlerRegistryFactory {
    INSTANCE;

    private static final ReflectMsgHandlerRegistry defaultMsgHandlerRegistry = new ReflectMsgHandlerRegistry(false);


    static {
        defaultMsgHandlerRegistry.register(new ClientMsgHandler());
    }

    public MsgHandlerRegistry get() {
        return defaultMsgHandlerRegistry;
    }
}
