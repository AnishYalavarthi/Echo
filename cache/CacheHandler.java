package cache;

import echo.*;

import java.net.Socket;

public class CacheHandler extends ProxyHandler {
    protected static SafeTable cache = new SafeTable();

    public CacheHandler(Socket sock) {
        super(sock);
    }

    public CacheHandler() {
        super();
    }

    @Override
    protected String response(String request) throws Exception {
        if (cache.get(request) != null){
            return cache.get(request);
        }
        peer.send(request);
        cache.put(request, peer.receive());
        return peer.receive();
    }
}
