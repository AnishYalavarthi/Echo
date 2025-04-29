package echo;

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
            //System.out.println("query found in cache");
            return cache.get(request);
        }
        //System.out.println("query not found in cache, updating");
        peer.send(request);
        String response = peer.receive();
        cache.put(request, response);
        return response;
    }
}
