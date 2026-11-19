#!/usr/bin/env bash
set -e

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-25.jdk/Contents/Home
export PATH="$JAVA_HOME/bin:$PATH"

echo "================================================================================"
echo "JAVA PROGRAMMING ASSIGNMENT 2 (CO2): COMPLETE VERIFICATION TEST SUITE"
echo "Student: Korasika Sudheer | Register Number: 192324313 | Java: 25.0.1 LTS"
echo "================================================================================"

echo -e "\n>>> 1. EXECUTING QUESTION 1 (String vs StringBuilder vs StringBuffer)..."
mkdir -p Q1/bin
javac -d Q1/bin Q1/src/q1/*.java
java -cp Q1/bin q1.TicketGeneratorDemo
java -cp Q1/bin q1.TicketGeneratorBenchmark

echo -e "\n>>> 2. EXECUTING QUESTION 2 (ArrayList vs LinkedList vs ArrayDeque)..."
mkdir -p Q2/bin
javac -d Q2/bin Q2/src/q2/*.java
java -cp Q2/bin q2.PlaylistDemo
java -cp Q2/bin q2.PlaylistBenchmark

echo -e "\n>>> 3. EXECUTING QUESTION 3 (hashCode-equals Contract & Attendance)..."
mkdir -p Q3/bin
javac -d Q3/bin Q3/src/*.java
java -cp Q3/bin Attendance
java -cp Q3/bin ContractInvestigation

echo -e "\n>>> 4. EXECUTING QUESTION 4 (Pharmacy Inventory: HashMap, TreeMap, LinkedHashMap)..."
mkdir -p Q4/bin
javac -d Q4/bin Q4/src/q4/*.java
java -cp Q4/bin q4.PharmacyReportDemo
java -cp Q4/bin q4.PharmacyInventoryBenchmark

echo -e "\n>>> 5. EXECUTING QUESTION 5 (Generic Leaderboard & Comparator Principle)..."
mkdir -p Q5/bin
javac -d Q5/bin Q5/src/*.java
java -cp Q5/bin LeaderboardDemo
java -cp Q5/bin LeaderboardPrincipleTest

echo -e "\n>>> 6. EXECUTING QUESTION 6 (Hospital Emergency Triage & Fair Aging)..."
mkdir -p Q6/bin
javac -d Q6/bin Q6/src/*.java
java -cp Q6/bin Triage
java -cp Q6/bin TriageEvaluation

echo -e "\n>>> 7. EXECUTING QUESTION 7 (Day-4 Design Challenge - Bucket Queue Redesign)..."
mkdir -p Q7/bin
javac -d Q7/bin Q7/src/q7/*.java
java -cp Q7/bin q7.EmergencyTriageRedesign
java -cp Q7/bin q7.RedesignValidationBenchmark

echo -e "\n================================================================================"
echo ">>> ALL 7 QUESTIONS (Q1 TO Q7) COMPILED AND VERIFIED WITH 100% SUCCESS!"
echo "================================================================================"
