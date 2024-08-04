#!/bin/bash

SRC_DIR="../src"
OUT_DIR="../classes"

rm -r "$OUT_DIR"/*.class
javac -d $OUT_DIR $(find $SRC_DIR -name "*.java")
