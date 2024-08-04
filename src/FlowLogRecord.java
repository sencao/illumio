/**
 * Assume the format of flow log is in the format as below:
 * <version> <account-id> <interface-id> <srcaddr> <dstaddr> <srcport> <dstport> <protocol> <packets> <bytes> <start> <end> <action> <log-status>
 * 
 * For simplicity, only check if dstport is a number and protocol code is defined in class Protocols; 
 * and other fields are not validated.
 */

public class FlowLogRecord {

    private static String FLOWLOG_RECORD_SEPARATOR = " ";

    private int dstPort;
    private String protocol;

    private FlowLogRecord(int dstPort, String protocol) {
        this.dstPort = dstPort;
        this.protocol = protocol;
    }

    public int getDstPort() {
        return dstPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public static FlowLogRecord parse(String line) throws InvalidFlowLogRecordException {
        FlowLogRecord record = null;
        try {
            String[] elements = line.split(FLOWLOG_RECORD_SEPARATOR);
            int dstPort = Integer.parseInt(elements[6]);
            int protocolCode = Integer.parseInt(elements[7]);
            String protocol = Protocols.getProtocol(protocolCode);
            record = new FlowLogRecord(dstPort, protocol);
        } catch (Exception ex) {
            throw new InvalidFlowLogRecordException();
        }

        return record;
    }

}
