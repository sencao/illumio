#!/bin/bash

EXPECTED_ARGS=3
if [ $# -ne $EXPECTED_ARGS ]; then
    echo "Wrong command!"
    echo "Usage: ./parse.sh [lookup table filename] [flow log filename] [output filename]"
    exit 1
fi

OUT_DIR="../classes"

TEST_DIR="../tests"
OUTPUT_DIR="../tests/output"

lookupTableFileName="$1"
flowLogFileName="$2"
outputFileName="$3"
lookup="${TEST_DIR}/${lookupTableFileName}"
flowLog="${TEST_DIR}/${flowLogFileName}"
output="${OUTPUT_DIR}/${outputFileName}"

# echo $lookup
# echo $flowLog
# echo $output

java -cp $OUT_DIR FlowLog ${lookup} ${flowLog} ${output}
