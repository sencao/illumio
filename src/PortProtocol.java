public class PortProtocol {
    private static String SEPARATOR = " ";

    public static String getKey(int port, String protocol) {
        protocol = protocol.toLowerCase();
        return port + SEPARATOR + protocol;
    }

    public static int getPort(String portProtocolKey) {
        int index = portProtocolKey.indexOf(SEPARATOR);
        String port = portProtocolKey.substring(0, index);
        return Integer.valueOf(port);
    }

    public static String getProtocol(String portProtocolKey) {
        int index = portProtocolKey.indexOf(SEPARATOR);
        return portProtocolKey.substring(index + 1);
    }
}
