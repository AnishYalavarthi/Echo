package echo;

import java.io.IOException;
import java.net.Socket;

public class ProxyHandler extends RequestHandler {

    protected Correspondent peer;

    public ProxyHandler(Socket s) {
        super(s);
    }

    public ProxyHandler() {
        super();
    }

    public void initPeer(String host, int port) {
        peer = new Correspondent();
        peer.requestConnection(host, port);
    }

    @Override
    protected String response(String request) throws Exception {
        peer.send(request);
        return peer.receive();
    }
}