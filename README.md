[Design and Implementation Considerations]

1, Since it is required to avoid using non-default libraries, I picked Java as a bare programming language. Otherwise, it would be much less coding.

2, Beside functionality, it is not clear what the assessment is looking for. I put some effort on object-oriented design with a class hierarchy. Hopefully it is not over engineering.

3, I assume there is no concurrency requirement. So, I did not handle race conditions.

4, Speed and Space performance are considered. The design and implementation can load a larger lookup table to handle a larger flow log with speed than the required max sizes.

5, I assume a flow log contains records in the format as below:   
    <version> <account-id> <interface-id> <srcaddr> <dstaddr> <srcport> <dstport> <protocol> <packets> <bytes> <start> <end> <action> <log-status>
I only checked if dstPort is a number and protocol code is valid. The flow logs used in the testing are just composed by following the above syntax. The traffic could be irrealistic. 

6, I assume tag is case insensitive.

7, when loading lookup table, if the tag field contains space, I only use the substring before the first space as tag. This is illustrated by the given example.

8, Protocols are defined by https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml. I only hardcoded a few protocols, and such hardcoding would not affect speed and space performance.

9, I assume there may be some ill formatted lines in the lookup table file and flow log files. The implementation catches wrong file format in the lookup table file, and skips wrong lines in the lookup table file and flow logs.

10, I assume that Tag Counts are in an arbitrary order but with untagged first, and Port/Protocol Combination Counts are in an arbitrary order.

[Repository]

    git clone https://github.com/sencao/illumio.git

[Code Structure]

1. the source code is under folder src.
2, build and execution scripts are under folder bin.
3, class files are under folder classes
4, the input files for lookup tables and flow logs are under folder tests. Lookup tables are named as lookup[x].csv. Flow logs are name as flow[y].log.
5, the output files are under folder tests\output. Output files are named as output[x]_[y].txt.

[Environment]

1, JDK is required to compile and run java code.
2, scripts for build and execution commands require BASH shell.

[Commands]

1, build project

    cd bin
    
    ./build.sh

2, parse flow logs

    under folder bin
    
    ./parse.sh lookup5.csv flow4.log output5_4.txt

[Tests]

1, lookup tables

    lookup.csv: no such lookup table
    
    lookup0.inp: invalid lookup table due to wrong file extension
    
    lookup1.csv: invalid lookup table due to missing csv header
    
    lookup2.csv: valid but empty lookup table
    
    lookup3.csv: valid lookup table with a few mappings
    
    lookup4.csv: lookup table containing some ill-formatted lines
    
    lookup5.csv: lookup table containing more than 10000 mappings 
    

2, flow logs

    flow0.log: empty flow log
    
    flow1.log: flow log with tagged and untagged mappings
    
    flow2.log: flow log with some ill-formatted lines
    
    flow4.log: flow log with file size more than 10MB. 
    

I have tested different test cases with different combinations of the above lookup tables and flow logs, which are supposed to cover all possible cases.

I have tested all the above combinations, by running command as the following pattern:

    ./parse.sh lookup[x].csv flow[y].log output[x]_[y].txt
    
Most output files are included.

The combination of lookup5.csv and flow4.log would trigger max memory usage by the requirement.

The following commands were executed for testing:

    ./parse.sh lookup flow0.log output0_0.txt
    
    ./parse.sh lookup0.inp flow0.log output0_0.txt
    
    ./parse.sh lookup1.csv flow0.log output1_0.txt
    
    ./parse.sh lookup2.csv flow0.log output2_0.txt
    
    ./parse.sh lookup2.csv flow1.log output2_1.txt
    
    ./parse.sh lookup2.csv flow2.log output2_2.txt
    
    ./parse.sh lookup2.csv flow3.log output2_3.txt
    
    ./parse.sh lookup2.csv flow4.log output2_4.txt
    
    ./parse.sh lookup3.csv flow0.log output3_0.txt
    
    ./parse.sh lookup3.csv flow1.log output3_1.txt
    
    ./parse.sh lookup3.csv flow2.log output3_2.txt
    
    ./parse.sh lookup3.csv flow3.log output3_3.txt
    
    ./parse.sh lookup3.csv flow4.log output3_4.txt
    
    ./parse.sh lookup4.csv flow0.log output4_0.txt
    
    ./parse.sh lookup4.csv flow1.log output4_1.txt
    
    ./parse.sh lookup4.csv flow2.log output4_2.txt
    
    ./parse.sh lookup4.csv flow3.log output4_3.txt
    
    ./parse.sh lookup4.csv flow4.log output4_4.txt
    
    ./parse.sh lookup5.csv flow0.log output5_0.txt
    
    ./parse.sh lookup5.csv flow1.log output5_1.txt
    
    ./parse.sh lookup5.csv flow2.log output5_2.txt
    
    ./parse.sh lookup5.csv flow3.log output5_3.txt
    
    ./parse.sh lookup5.csv flow4.log output5_4.txt
