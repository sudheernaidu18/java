#!/usr/bin/env bash
set -e

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-25.jdk/Contents/Home
export PATH="$JAVA_HOME/bin:$PATH"

echo "================================================================================"
echo "CAMPUS SMART-PARKING SYSTEM: ALL-TASK COMPILATION & TEST SUITE"
echo "Student: Korasika Sudheer | Register Number: 192324313 | Java: 25.0.1"
echo "================================================================================"

echo -e "\n>>> RUNNING TASK 01..."
javac Task01/src/ModelAnalysisDemo.java -d Task01/bin
java -cp Task01/bin ModelAnalysisDemo

echo -e "\n>>> RUNNING TASK 02..."
javac Task02/src/DataRepresentationDemo.java -d Task02/bin
java -cp Task02/bin DataRepresentationDemo

echo -e "\n>>> RUNNING TASK 03..."
javac Task03/src/ParkingFacility.java -d Task03/bin
java -cp Task03/bin ParkingFacility

echo -e "\n>>> RUNNING TASK 04..."
javac Task04/src/ConstructorDemo.java -d Task04/bin
java -cp Task04/bin ConstructorDemo

echo -e "\n>>> RUNNING TASK 05..."
javac Task05/src/StaticLifeCycleDemo.java -d Task05/bin
java -cp Task05/bin StaticLifeCycleDemo

echo -e "\n>>> RUNNING TASK 06..."
javac Task06/src/InheritanceTraceDemo.java -d Task06/bin
java -cp Task06/bin InheritanceTraceDemo

echo -e "\n>>> RUNNING TASK 07..."
javac Task07/src/MethodOverridingDemo.java -d Task07/bin
java -cp Task07/bin MethodOverridingDemo

echo -e "\n>>> RUNNING TASK 08..."
javac Task08/src/FeeDemo.java -d Task08/bin
java -cp Task08/bin FeeDemo

echo -e "\n>>> RUNNING TASK 09..."
javac Task09/src/AbstractionDemo.java -d Task09/bin
java -cp Task09/bin AbstractionDemo

echo -e "\n>>> RUNNING TASK 10..."
javac -d Task10/bin Task10/src/parking/model/*.java Task10/src/parking/service/*.java Task10/src/parking/app/*.java
java -cp Task10/bin parking.app.ParkingApp

echo -e "\n>>> RUNNING TASK 11..."
javac Task11/src/*.java -d Task11/bin
java -cp Task11/bin SystemTestRunner

echo -e "\n>>> RUNNING TASK 12..."
javac Task12/src/*.java -d Task12/bin
java -cp Task12/bin AdaptiveSystemTest

echo -e "\n================================================================================"
echo ">>> ALL 12 TASKS COMPILED AND EXECUTED WITH 100% SUCCESS!"
echo "================================================================================"
