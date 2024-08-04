public class FlowLog {
    public static void main(String args[]) {
        if (args.length != 3) {
            System.out.println("Wrong Command!");
            System.out.println("Usage: java FlowLog [lookup table file] [flow log filename] [output filename]");
            System.exit(-1);
        }

        String lookupTableFileName = args[0];
        String flowLogFileName = args[1];
        String outputFileName = args[2];

        try {
            LookupTable lookup = new LookupTableLoader().load(lookupTableFileName);
            FlowLogParser parser = new FlowLogParser(lookup);
            parser.parse(flowLogFileName);
            FlowLogOutputter outputter = new FlowLogOutputter(parser);
            outputter.write(outputFileName);    
        } catch (InvalidLookupTableException ex) {
            System.err.println("File " + lookupTableFileName + " is NOT a valid lookup table!");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Failed to parse flow log file " + flowLogFileName + " to " + outputFileName + "!");
            ex.printStackTrace();
        }
    }

}
