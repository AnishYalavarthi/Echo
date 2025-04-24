package echo;

import java.io.IOException;
import java.net.Socket;

public class ProxyServer extends Server{
    String peerHost;
    int peerPort;

    public ProxyServer(int port, String handlerTypeName, int peerPort, String peerHost) {
        super(port, handlerTypeName);
        this.peerPort = peerPort;
        this.peerHost = peerHost;
    }

    @Override
    public RequestHandler makeHandler(Socket s) throws InstantiationException, IllegalAccessException {
        ProxyHandler handler = (ProxyHandler) handlerType.newInstance();
        // set handler's socket to s
        handler.setSocket(s);
        handler.initPeer(peerHost, peerPort);
        // return handler
        return handler;
    }

    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
        int port = 5555;
        int peerPort = 6666;
        String peerHost = "localhost";
        String service = "echo.ProxyHandler";

        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            peerPort = Integer.parseInt(args[1]);
        }
        if (3 <= args.length) {
            port = Integer.parseInt(args[2]);
        }
        if (4 <= args.length) {
            peerHost = args[3];
        }
        Server server = new ProxyServer(port, service, peerPort, peerHost);
        server.listen();
    }
}
