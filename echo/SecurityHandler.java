package echo;

import java.net.Socket;
import java.util.Objects;

public class SecurityHandler extends ProxyHandler {
    protected static SafeTable userTable = new SafeTable();
    protected boolean logged = false;

    public SecurityHandler(Socket sock) {
        super(sock);
    }

    public SecurityHandler() {
        super();
    }

    @Override
    protected String response(String request) {
        String[] info = request.split(" ");
        if (!logged) {
            switch (info[0]) {
                case "new" -> {
                    userTable.computeIfAbsent(info[1], k -> info[2]);
                    shutDown();
                    return "User registered.. terminating session";
                }
                case "login" -> {
                    if (!Objects.equals(userTable.get(info[1]), info[2]) || userTable.get(info[1]) == null) {
                        shutDown();
                        return "Login invalid.. terminating session";
                    }
                    logged = true;
                    return "Logged in successfully";
                }
                default -> {
                    return "Please provide login information";
                }
            }
        } else {
            peer.send(request);
            return peer.receive();
        }
    }
}
