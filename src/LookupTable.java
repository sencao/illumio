
import java.util.HashMap;

public class LookupTable {

    private HashMap<String, String> map;

    public LookupTable() {
        map =  new HashMap<>(); 
    }

    public void index(int port, String protocol, String tag) {
        String key = PortProtocol.getKey(port, protocol);
        tag = tag.toLowerCase();
        map.put(key, tag);
    }

    public String getTag(int port, String protocol) {
        String key = PortProtocol.getKey(port, protocol);
        return map.get(key);
    }
}
