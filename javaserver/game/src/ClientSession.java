import network.INetHandlerContext;

public class ClientSession {
    private INetHandlerContext ctx;

    public ClientSession(INetHandlerContext ctx){
        this.ctx = ctx;
    }
}
