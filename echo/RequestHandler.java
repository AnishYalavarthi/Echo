package echo;

import java.net.Socket;

public class RequestHandler extends Correspondent implements Runnable {
    protected boolean active;
    public RequestHandler(Socket s) {
        super(s);
        active = true;
    }
    public RequestHandler() {
        super();
        active = true;
    }

    protected String response(String request) throws Exception {
        return "echo: " + request;
    }

    protected void shutDown() {
        if (Server.DEBUG) {
            System.out.println("handler shutting down");
            active = false;
        }
    }
    public void run() {
        while(active) {
            try {
                String request = receive();
                if(request.equals("quit")) {
                    shutDown();
                    break;
                }
                send(response(request));
                Thread.sleep(50);
            } catch(Exception e) {
                send(e.getMessage() + "... ending session");
                break;
            }
        }
        close();
    }
}