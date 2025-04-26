package math;

import echo.RequestHandler;

import java.net.Socket;

public class MathHandler extends RequestHandler {

    public MathHandler(Socket sock) {
        super(sock);
        System.out.println("MathHandler");
    }

    public MathHandler() {
        super();
        System.out.println("MathHandler");
    }

    @Override
    protected String response(String request) throws Exception {
        String[] tokens = request.split(" ");
        double result;
        switch (tokens[0]) {
            case "add" -> {
                result = 0;
                for (int i = 1; i < tokens.length; i++) {
                    result += Double.parseDouble(tokens[i]);
                }
            }
            case "mul" -> {
                result = 1;
                for (int i = 1; i < tokens.length; i++) {
                    result *= Double.parseDouble(tokens[i]);
                }
            }
            case "sub" -> result = Double.parseDouble(tokens[1]) - Double.parseDouble(tokens[2]);
            case "div" -> result = Double.parseDouble(tokens[1]) / Double.parseDouble(tokens[2]);
            default -> {
                return "Not a valid request";
            }
        }
        return Double.toString(result);
    }
}
