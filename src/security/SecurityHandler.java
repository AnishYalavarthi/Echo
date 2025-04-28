package security;

import echo.*;

import java.net.Socket;

public class SecurityHandler extends ProxyHandler {
    protected static SafeTable userTable = new SafeTable();

    public SecurityHandler(Socket sock) {
        super(sock);
    }

    public SecurityHandler() {
        super();
    }

    @Override
    protected String response(String request) throws Exception {
        String[] info = request.split(" ");
        switch (info[0]) {
            case "new":
                if (userTable.get(info[1]) == null) userTable.put(info[1], info[2]);
                shutDown();
                return "User registered";
            case "login":
                if (userTable.get(info[1]) != info[2] || userTable.get(info[1]) == null){
                    shutDown();
                    return "Login invalid";
                }
                //add
        }
        return null;
    }
}
