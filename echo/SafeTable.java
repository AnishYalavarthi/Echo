package echo;

import java.util.HashMap;

public class SafeTable extends HashMap<String, String> {

    @Override
    public synchronized String get (Object request){
        return super.get((String) request);
    }

    @Override
    public synchronized String put (String request, String response){
        return super.put(request, response);
    }
}
