package echo;

import java.io.*;
import java.lang.reflect.Constructor;
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
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } // catch
    }


    public void listen() throws IOException, InstantiationException, IllegalAccessException{
        while(true) {
            // accept a connection
            Socket socket = mySocket.accept();
            // make handler
            RequestHandler handler = makeHandler(socket);
            // start handler in its own thread
            Thread thread = new Thread(handler);
            thread.start();
        } // while
    }

    public RequestHandler makeHandler(Socket s) throws InstantiationException, IllegalAccessException{
        try {
            Constructor<?> constructor = handlerType.getConstructor(Socket.class);
            RequestHandler handler = (RequestHandler) constructor.newInstance();
            // set handler's socket to s
            handler.setSocket(s);
            // return handler
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