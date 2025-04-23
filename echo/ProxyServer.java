package echo;

import java.net.Socket;

public class ProxyServer extends Server{
    protected String peerHost;
    protected int peerPort;

    public ProxyServer(int port, String handlerTypeName) {
        super(port, handlerTypeName);
    }

    @Override
    public ProxyHandler makeHandler(Socket s) throws InstantiationException, IllegalAccessException {
        ProxyHandler handler = (ProxyHandler) handlerType.newInstance();
        // set handler's socket to s
        handler.setSocket(s);
        // return handler
        return handler;
    }

    public static void main(String[] args){
        //add
    }
}
