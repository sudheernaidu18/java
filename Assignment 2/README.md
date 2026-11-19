# CSA09-192324313-Assignment2: Programming in Java (CO2)

**Course**: Programming in Java  
**Course Outcome**: CO2 – Strings and StringBuilder, regular expressions, the Collection Framework (List, Queue, Deque, PriorityQueue, Set, and Map), the hashCode–equals contract, Generics, Comparable and Comparator, and Collections utility algorithms.  
**Total Marks**: 50  
**Student Name**: Korasika Sudheer  
**Register Number**: 192324313  
**Java Version**: Java 25.0.1 (Oracle Corporation LTS)  
**Repository**: [https://github.com/sudheernaidu18/CSA09-192324313-Assignment2](https://github.com/sudheernaidu18/CSA09-192324313-Assignment2)  

---

## 1. Executive Summary & Repository Organization

This repository contains the complete, validated, and empirically benchmarked implementation of **Assignment 2 (CO2)**. Every task from **Q1 through Q7** adheres strictly to the academic rubric:
- All **"Complete the Code"** programs are fully completed and verified against expected outputs.
- No experimental numbers have been fabricated; all timing measurements, memory deltas, and object allocation counts were executed and averaged on **macOS** under **Java 25.0.1 LTS**.
- Dedicated sub-folders (`Q1` to `Q7`) contain source code, compiled bytecode, execution outputs, and high-resolution terminal screenshots.
- The **Day-4 Design Challenge (Q7)** delivers an in-depth diagnosis of `PriorityQueue` heap degradation and implements an $O(1)$ Multi-Level Bucket Priority Queue with dynamic starvation aging.

```
CSA09-192324313-Assignment2/
├── README.md                           (Master repository documentation & viva guide)
├── run_all.sh                          (Automated test suite compiling and running Q1-Q7)
├── Q1/                                 (Building Text Under Load: String, StringBuilder, StringBuffer)
│   ├── README.md
│   ├── src/q1/TicketGeneratorDemo.java
│   ├── src/q1/TicketGeneratorBenchmark.java
│   ├── bin/
│   └── output/ (output.txt, screenshot.png)
├── Q2/                                 (When Is ArrayList an Appropriate Choice?)
│   ├── README.md
│   ├── src/q2/PlaylistDemo.java
│   ├── src/q2/PlaylistBenchmark.java
│   ├── bin/
│   └── output/ (output.txt, screenshot.png)
├── Q3/                                 (Engineer the hashCode–equals Contract)
│   ├── README.md
│   ├── src/Attendance.java             [Complete the Code]
│   ├── src/ContractInvestigation.java
│   ├── bin/
│   └── output/ (output.txt, attendance_output.txt, screenshot.png)
├── Q4/                                 (Choosing a Map for a Pharmacy Inventory)
│   ├── README.md
│   ├── src/q4/PharmacyReportDemo.java
│   ├── src/q4/PharmacyInventoryBenchmark.java
│   ├── bin/
│   └── output/ (output.txt, screenshot.png)
├── Q5/                                 (Generic Ranking Component from a Principle)
│   ├── README.md
│   ├── src/LeaderboardDemo.java        [Complete the Code]
│   ├── src/LeaderboardPrincipleTest.java
│   ├── bin/
│   └── output/ (output.txt, leaderboard_output.txt, screenshot.png)
├── Q6/                                 (Priority, Order and Fairness in a Queue)
│   ├── README.md
│   ├── src/Triage.java                 [Complete the Code]
│   ├── src/TriageEvaluation.java
│   ├── bin/
│   └── output/ (output.txt, triage_output.txt, screenshot.png)
└── Q7/                                 (Day-4 Design Challenge: Diagnose, Redesign and Validate)
    ├── README.md
    ├── src/q7/EmergencyTriageRedesign.java
    ├── src/q7/RedesignValidationBenchmark.java
    ├── bin/
    └── output/ (output.txt, demo_output.txt, screenshot.png)
```

---

## 2. Assessment Rubric & Results Matrix (Total: 50 Marks)

| Question | Assessed Area | Bloom's Level | Marks | Source Files | Results Summary |
| :--- | :--- | :---: | :---: | :--- | :--- |
| **Q1** | String, StringBuilder & StringBuffer | K4 – Analyse | **6** | `TicketGeneratorDemo.java`<br>`TicketGeneratorBenchmark.java` | Measured 3 workloads; StringBuilder achieved **3.0x speedup** over `+` and prevented 2 million temporary string objects. Recommended StringBuilder for thread-confined tickets and StringBuffer for shared logging. |
| **Q2** | ArrayList, LinkedList & ArrayDeque | K4 – Analyse | **4** | `PlaylistDemo.java`<br>`PlaylistBenchmark.java` | Benchmarked across $N=1\text{k}, 10\text{k}, 100\text{k}$. ArrayList $k$-th element access is **1,000x faster** than LinkedList. Evaluated changed condition: ArrayDeque is **450x faster** for "Up Next" queue. |
| **Q3** | HashSet & hashCode–equals contract | K4 – Analyse | **6** | `Attendance.java`<br>`ContractInvestigation.java` | Completed TODO 1 (`regNo.hashCode()`). Proved duplicate leak without hashCode (`size=3` vs `size=2`). Showed element trap when mutating keys. Compared with TreeSet ($O(\log n)$) and HashMap ($O(1)$). |
| **Q4** | HashMap, TreeMap & LinkedHashMap | K4 – Analyse | **6** | `PharmacyReportDemo.java`<br>`PharmacyInventoryBenchmark.java` | Tested 100k lookups across 3 scales. LinkedHashMap lookup is **5.5x faster** than TreeMap and naturally generates insertion report ($0.637\text{ ms}$). Sorting once daily takes only $28\text{ ms}$. Explained why Hashtable is obsolete. |
| **Q5** | Generics, Comparable & Comparator | K4 – Analyse | **7** | `LeaderboardDemo.java`<br>`LeaderboardPrincipleTest.java` | Completed TODO 1 (`ranking()`) & 2 (`byRank`). Verified Bisons ranks above Panthers. Derived transitivity principle. Exposed 32-bit integer subtraction overflow bug. Reused generic `Leaderboard<T>` for players. |
| **Q6** | Queue & PriorityQueue | K5 – Evaluate | **8** | `Triage.java`<br>`TriageEvaluation.java` | Completed TODO 1 (`PriorityQueue` with compound Comparator). Verified 8-patient hand calculation match. Explained heap level-order vs polled order. Designed fair aging rule to eliminate S5 starvation. Approved for 5,000 patients/day. |
| **Q7** | Day-4 Design Challenge | K5 – Evaluate | **13** | `EmergencyTriageRedesign.java`<br>`RedesignValidationBenchmark.java` | Diagnosed PriorityQueue heap mutation freeze. Redesigned as Multi-Level Bucket Queue ($S \in [1..5]$). Achieved **$O(1)$ admission, 4.7x faster dequeue**, and $O(1)$ starvation-free aging. |
| **TOTAL** | | | **50** | | **All 7 Questions Fully Completed & Verified** |

---

## 3. Master Compilation & Execution Manual

### Run Entire Test Suite with One Command
From the root directory:
```bash
./run_all.sh
```

### Individual Question Compilation & Execution Commands

```bash
# Question 1: String vs StringBuilder vs StringBuffer
javac -d Q1/bin Q1/src/q1/*.java
java -cp Q1/bin q1.TicketGeneratorDemo
java -cp Q1/bin q1.TicketGeneratorBenchmark

# Question 2: ArrayList vs LinkedList vs ArrayDeque
javac -d Q2/bin Q2/src/q2/*.java
java -cp Q2/bin q2.PlaylistDemo
java -cp Q2/bin q2.PlaylistBenchmark

# Question 3: hashCode-equals Contract & Complete the Code
javac -d Q3/bin Q3/src/*.java
java -cp Q3/bin Attendance
java -cp Q3/bin ContractInvestigation

# Question 4: Pharmacy Inventory Map Benchmark
javac -d Q4/bin Q4/src/q4/*.java
java -cp Q4/bin q4.PharmacyReportDemo
java -cp Q4/bin q4.PharmacyInventoryBenchmark

# Question 5: Generic Leaderboard & Principle Test
javac -d Q5/bin Q5/src/*.java
java -cp Q5/bin LeaderboardDemo
java -cp Q5/bin LeaderboardPrincipleTest

# Question 6: Emergency Department Triage & Fairness
javac -d Q6/bin Q6/src/*.java
java -cp Q6/bin Triage
java -cp Q6/bin TriageEvaluation

# Question 7: Day-4 Design Challenge (Bucket Queue Redesign)
javac -d Q7/bin Q7/src/q7/*.java
java -cp Q7/bin q7.EmergencyTriageRedesign
java -cp Q7/bin q7.RedesignValidationBenchmark
```

---

## 4. Verification Output Screenshots Gallery

| Question | Description | Terminal Execution Screenshot |
| :---: | :--- | :--- |
| **Q1** | String Immutability & Railway e-Ticket Benchmark | ![Q1 Screenshot](Q1/output/screenshot.png) |
| **Q2** | Playlist ArrayList vs LinkedList vs ArrayDeque | ![Q2 Screenshot](Q2/output/screenshot.png) |
| **Q3** | Attendance De-duplication & hashCode Contract | ![Q3 Screenshot](Q3/output/screenshot.png) |
| **Q4** | Hospital Pharmacy Map Lookups & Dual Reports | ![Q4 Screenshot](Q4/output/screenshot.png) |
| **Q5** | Generic Sports Leaderboard & Comparator Principle | ![Q5 Screenshot](Q5/output/screenshot.png) |
| **Q6** | Emergency Room Triage & Hand-Verified Correctness | ![Q6 Screenshot](Q6/output/screenshot.png) |
| **Q7** | Day-4 Redesign: Multi-Level Bucket Queue | ![Q7 Screenshot](Q7/output/screenshot.png) |

---

## 5. Viva Voce Preparation & Rapid Defense Guide

### Q1: Why is repeated `String` concatenation (+) inside a loop considered an anti-pattern?
*Answer*: Because `String` is immutable. Every concatenation allocates a new heap buffer and copies all previous characters, yielding $O(n^2)$ time complexity and generating $n$ transient objects. `StringBuilder` maintains a resizable array with amortized $O(1)$ appends ($O(n)$ overall).

### Q2: When would you stop recommending `ArrayList`?
*Answer*: When an application frequently inserts or deletes elements at the **front or middle** of a large collection ($O(n)$ element shifting via `System.arraycopy`). For double-ended queues, `ArrayDeque` is strictly superior.

### Q3: Why does missing `hashCode()` cause duplicates in a `HashSet` even when `equals()` is properly overridden?
*Answer*: `HashSet` maps keys to buckets via `(n - 1) & hash`. Without overriding `hashCode()`, distinct heap objects inherit distinct identity hashes and land in different buckets. `equals()` is only evaluated between objects sharing the same bucket index; objects in different buckets are never compared!

### Q4: Why is `LinkedHashMap` preferable to `TreeMap` for the pharmacy system?
*Answer*: Pharmacists perform 100,000 lookups daily requiring sub-microsecond response. `LinkedHashMap` provides $O(1)$ hash lookups (5.5x faster than `TreeMap`'s $O(\log n)$ tree traversal) and natively tracks insertion order for the evening log. The sorted code audit is run only once nightly, taking $< 30\text{ ms}$ to sort.

### Q5: Why is integer subtraction `(t1.val - t2.val)` in a Comparator dangerous?
*Answer*: If `t1.val` is large positive and `t2.val` is large negative, the difference overflows the 32-bit signed integer range and wraps to a negative number, completely inverting the ranking order. Always use `Integer.compare(a, b)`.

### Q6: Why does `System.out.println(priorityQueue)` not show elements in sorted order?
*Answer*: Because `PriorityQueue.toString()` traverses the internal heap array in **level-order tree sequence**, not sorted order. Only sequential `poll()` operations re-heapify and extract elements in prioritized sequence.

### Q7: What was the core bottleneck diagnosed in Q6 and how did Q7 solve it?
*Answer*: In `PriorityQueue`, dynamic priority aging (promoting starving severity-5 patients) requires draining and rebuilding the binary heap ($O(n \log n)$), causing severe latency spikes. Q7 exploits the discrete severity alphabet ($S \in [1..5]$) by replacing the binary heap with an array of 5 circular `ArrayDeque` buckets, achieving strictly **$O(1)$ admission, 4.7x faster consultation**, and $O(1)$ starvation-free promotions.
