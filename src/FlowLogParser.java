import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FlowLogParser {

    private int untagged;
    private HashMap<String, Integer> tags;
    private HashMap<String, Integer> portProtocols;

    private LookupTable lookup;

    public FlowLogParser(LookupTable lookup) {
        this.lookup = lookup;
        untagged = 0;
        tags = new HashMap<>();
        portProtocols = new HashMap<>();
    }

    public int getUntagged() {
        return untagged;
    }

    public Map<String, Integer> getTags() {
        return tags;
    }

    public Map<String, Integer> getPortProtocols() {
        return portProtocols;
    }
    
    private void index(FlowLogRecord record) {
        int dstPort = record.getDstPort();
        String protocol = record.getProtocol();
        String tag = lookup.getTag(dstPort, protocol);
        if (tag == null) {
            untagged++;
        } else {
            tags.put(tag, tags.getOrDefault(tag, 0) + 1);
        }

        String portPortocolKey = PortProtocol.getKey(dstPort, protocol);
        portProtocols.put(portPortocolKey, portProtocols.getOrDefault(portPortocolKey, 0) + 1);
    }

    private void reset() {
        untagged = 0;
        tags.clear();
        portProtocols.clear();
    }

    public void parse(String fileName) throws Exception {
        reset();

        FileInputStream fis = null;
        BufferedReader reader = null;
      
        try {
            System.out.println("Start parsing flow log file: " + fileName + ".");    
            fis = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(fis));            
            String line = reader.readLine();
            while(line != null){
                try {
                    FlowLogRecord record = FlowLogRecord.parse(line);
                    index(record);
                } catch (InvalidFlowLogRecordException ex) {
                    System.out.println("Skipped an ill-formatted line: " + line);
                }
                line = reader.readLine();
            }   
            System.out.println("Finished parsing flow log file: " + fileName + ".");        
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                System.err.println("Failed to close flow log file " + fileName + ".");
                ex.printStackTrace();
            }
        }
    }

}
