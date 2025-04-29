package echo;

import java.io.*;
import java.net.*;

public class Server {

    protected ServerSocket mySocket;
    protected int myPort;
    public static boolean DEBUG = true;
    protected Class<?> handlerType;

    public Server(int port, String handlerTypeName) {
        try {
            myPort = port;
            mySocket = new ServerSocket(myPort);
            this.handlerType = Class.forName(handlerTypeName);
            System.out.println("I'm listening ..");
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }


    public void listen() throws IOException, IllegalAccessException, InstantiationException {
        while(true) {
            Socket socket = mySocket.accept();
            RequestHandler handler = makeHandler(socket);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }

    public RequestHandler makeHandler(Socket s) throws IllegalAccessException, InstantiationException {
        try {
            RequestHandler handler = (RequestHandler) handlerType.newInstance();
            handler.setSocket(s);
            return handler;
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {
        int port = 5555;
        String service = "echo.RequestHandler";
        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(port, service);
        server.listen();
    }
}