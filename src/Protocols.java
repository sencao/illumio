import java.util.HashMap;

public class Protocols {
    private static HashMap<Integer, String> map = null; 

    /*
     * Protocol Numbers are defined by: 
     *   https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml
     * 
     * Protocol numbers can be stored in a file. All can be loaded from the file.
     * Here, we only hardcode a few.
     */
    private static void init() {
        map = new HashMap<>();
        map.put(0, "hopopt");
        map.put(1, "icmp");
        map.put(2, "igmp");
        map.put(6, "tcp");
        map.put(17, "udp");
    }

    public static String getProtocol(int code) throws InvalidProtocolException{
        if (map == null) {
            init();
        }

        String protocol = map.get(code);
        if (protocol == null) {
            throw new InvalidProtocolException();
        }
        return protocol;
    }

}
