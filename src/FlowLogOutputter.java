import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FlowLogOutputter {
    
    private static String HEADER_COUNT = "Count";
    private static String HEADER_TAG = "Tag";
    private static String HEADER_PORT = "Port";
    private static String HEADER_PROTOCOL = "Protocol";

    private static String TAG_UNTAGGED = "Untagged";

    private FlowLogParser parser;

    public FlowLogOutputter(FlowLogParser parser) {
        this.parser = parser;
    }

    private void writeTags(Writer output) throws IOException {
        String tagHeader = HEADER_TAG + "\t" + HEADER_COUNT + "\n";
        output.write(tagHeader);

        String untagged = TAG_UNTAGGED + "\t" + parser.getUntagged() + "\n";
        output.write(untagged);

        for (Map.Entry<String, Integer> entry : parser.getTags().entrySet()) {
            String line = entry.getKey() + "\t" + entry.getValue() + "\n";
            output.write(line);
        }
    }

    private void writePortProtocols(Writer output) throws IOException {
        String portProtocolHeader = HEADER_PORT + "\t" + HEADER_PROTOCOL + "\t" + HEADER_COUNT + "\n";
        output.write(portProtocolHeader);

        for (Map.Entry<String, Integer> entry : parser.getPortProtocols().entrySet()) {
            String portProtocol = entry.getKey();
            int port = PortProtocol.getPort(portProtocol);
            String protocol = PortProtocol.getProtocol(portProtocol);
            String line = port + "\t" + protocol + "\t" + entry.getValue() + "\n";
            output.write(line);
        }
    }

    public void write(String outputFileName) {
        FileWriter output = null;

        try { 
            output = new FileWriter(outputFileName); 

            System.out.println("Start writing output file: " + outputFileName + "......");
            writeTags(output);
            writePortProtocols(output);
            System.out.println("Finished writing output file: " + outputFileName + ".");

        } catch (Exception e) {
            System.err.print("Failed to write flow log output: " + outputFileName + "!!!");
            e.printStackTrace(); 
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ex) {
                System.err.println("Failed to close output file " + outputFileName + ".");
                ex.printStackTrace();
            }
        }       
    }
}
