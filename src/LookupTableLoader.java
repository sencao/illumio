import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LookupTableLoader {
    private static String CSV_EXTENSION = ".csv";
    private static String CSV_SEPARATOR = ",";
    private static String HEADER_DST_PORT = "dstport";
    private static String HEADER_PROTOCOL = "protocol";
    private static String HEADER_TAG = "tag";

    private LookupTable lookup = null;

    public LookupTableLoader() {
    }

    public LookupTable getLookupTable() {
        return lookup;
    }

    private void checkFlowLogFileExtension(String filename) throws InvalidLookupTableException {
        if (!filename.toLowerCase().endsWith(CSV_EXTENSION)) {
            throw new InvalidLookupTableException();
        }
    }

    private void checkCVSHeader(String header) throws InvalidLookupTableException {
        String[] elements = null;
        try {
            elements = header.split(CSV_SEPARATOR);
        } catch (Exception ex) {
            throw new InvalidLookupTableException();
        }
        if (elements == null || elements.length != 3) {
            throw new InvalidLookupTableException();
        }
        if (!elements[0].trim().equalsIgnoreCase(HEADER_DST_PORT) || !elements[1].trim().equalsIgnoreCase(HEADER_PROTOCOL) || !elements[2].trim().equalsIgnoreCase(HEADER_TAG)) {
            throw new InvalidLookupTableException();
        }
    }

    private void parseLine(String line) throws InvalidLookupTableLineException {
        String[] elements = null;
        try {
            elements = line.split(CSV_SEPARATOR);
        } catch (Exception ex) {
            throw new InvalidLookupTableLineException();
        }

        if (elements.length != 3) {
            throw new InvalidLookupTableLineException();
        }

        int port;
        try {
            port = Integer.parseInt(elements[0]);
        } catch (Exception ex) {
            throw new InvalidLookupTableLineException();
        }

        String protocol = elements[1].trim();
        if (protocol.length() == 0) {
            throw new InvalidLookupTableLineException();
        }

        String tag = elements[2].trim();
        int indexOfFirstSpace = tag.indexOf(' ');
        if (indexOfFirstSpace != -1) {
            tag = tag.substring(0, indexOfFirstSpace);
        }

        lookup.index(port, protocol, tag);
    }

    public LookupTable load(String fileName) throws Exception {
        lookup = new LookupTable();
      
        FileInputStream fis = null;
        BufferedReader reader = null;
      
        try {
            System.out.println("Start parsing lookup table file: " + fileName + "......");        

            checkFlowLogFileExtension(fileName);

            fis = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(fis));
            
            String header = reader.readLine();
            checkCVSHeader(header);

            String line = reader.readLine();
            while(line != null){
                try {
                    parseLine(line);
                } catch (InvalidLookupTableLineException ex) {
                    System.out.println("Skipped an ill-formatted line: " + line);
                }
                line = reader.readLine();
            }   
            System.out.println("Finished parsing lookup table file: " + fileName + ".");        
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                System.err.println("Failed to close lookup table file " + fileName + ".");
                ex.printStackTrace();
            }
        }

        return lookup;
    }

}
