package echo;

import echo.*;

public class ProxyHandler extends RequestHandler {

    protected Correspondent peer;

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