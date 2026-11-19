# Q6: Priority, Order and Fairness in a Queue

**Course Outcome**: CO2 – Queue, Deque, PriorityQueue  
**Bloom’s Level**: K5 – Evaluate  
**Marks**: 8  
**Student Name**: Korasika Sudheer  
**Register Number**: 192324313  

---

## 1. Problem Context: Emergency Room Medical Triage

Patients entering a hospital emergency department present with varying urgency levels ranging from **1 (Critical / Resuscitation)** to **5 (Non-urgent / Minor)**. Medical duty protocols require:
1. Doctors must always attend to the patient with the highest medical severity (lowest numerical level $1 \to 5$) first.
2. Among patients presenting with the identical severity level, the tie-breaker is strictly **First-Come, First-Served (FIFO)** based on arrival order.

We must evaluate queue data structures, enforce arrival ordering, analyze growth costs, prevent starvation, and evaluate suitability for a metropolitan hospital receiving 5,000 patients daily.

---

## 2. Queue Architecture: Why Plain Queues Fail

### 2.1 The Inadequacy of Plain Queues (`LinkedList` / `ArrayDeque`)
A standard FIFO queue (`LinkedList` or `ArrayDeque`) only inspects and removes the head element. To locate the most severe patient:
- The system must sequentially scan every patient currently in the waiting room ($O(n)$ search).
- Dequeuing requires removing an element from the middle of the queue ($O(n)$ element shifting or link pointer re-wiring).
- Under high patient volumes ($n > 1,000$), searching and removing on every doctor consultation scales as $O(n^2)$.

### 2.2 Enforcing Priority & FIFO Stability with `PriorityQueue`
`PriorityQueue` is backed by a balanced **binary min-heap** stored in an array `Object[] queue`. We supply a composite `Comparator`:
```java
private final PriorityQueue<Patient> queue = new PriorityQueue<>(
    Comparator.comparingInt((Patient p) -> p.severity)
              .thenComparingLong(p -> p.arrival)
);
```
- **Primary Order**: Numerical severity (1 is highest priority).
- **Secondary Tie-Breaker**: Monotonically increasing arrival counter (`long arrival = counter++`).
- **Heap Invariant**: For every node at index $k$, children are at $2k + 1$ and $2k + 2$. The parent is guaranteed $\le$ both children according to the comparator.

---

## 3. Why `System.out.println(priorityQueue)` Does Not Show Treatment Order

A frequent misconception is assuming that printing or iterating a `PriorityQueue` will output patients in sorted treatment order:
```java
System.out.println(priorityQueue); // Outputs: [5, 10, 30, 50, 20]
```
- **Explanation**: `PriorityQueue.iterator()` traverses the underlying `Object[]` array in **level-order tree sequence**, NOT in sorted priority order!
- The binary heap only guarantees that the root (index 0) is the minimum element. Relationships between sibling nodes at the same tree depth are unconstrained.
- To observe the true prioritized sequence, elements must be extracted sequentially using `poll()`, which executes $O(\log n)$ sift-down operations to re-surface the next global minimum.

---

## 4. Hand-Verified Correctness Check (8 Patients)

### 4.1 Input Arrival Log
1. **Ravi** (Severity 3, Arrival 0)
2. **Meena** (Severity 1, Arrival 1) - *Cardiac emergency*
3. **John** (Severity 3, Arrival 2) - *Fracture*
4. **Asha** (Severity 5, Arrival 3) - *Mild cough*
5. **Kumar** (Severity 1, Arrival 4) - *Acute trauma*
6. **Lata** (Severity 2, Arrival 5) - *Severe laceration*
7. **Deepak** (Severity 2, Arrival 6) - *Asthma attack*
8. **Priya** (Severity 4, Arrival 7) - *Sprained wrist*

### 4.2 Hand-Calculated vs. Program Verified Sequence

| Treatment Sequence | Patient | Severity Level | Arrival Order | Hand-Calculated Reason | Program Verification |
| :---: | :--- | :---: | :---: | :--- | :---: |
| **1** | **Meena** | S1 | 1 | Lowest severity (1); arrived before Kumar | **Match** |
| **2** | **Kumar** | S1 | 4 | Lowest severity (1); arrived after Meena | **Match** |
| **3** | **Lata** | S2 | 5 | Next severity (2); arrived before Deepak | **Match** |
| **4** | **Deepak** | S2 | 6 | Next severity (2); arrived after Lata | **Match** |
| **5** | **Ravi** | S3 | 0 | Next severity (3); arrived before John | **Match** |
| **6** | **John** | S3 | 2 | Next severity (3); arrived after Ravi | **Match** |
| **7** | **Priya** | S4 | 7 | Next severity (4) | **Match** |
| **8** | **Asha** | S5 | 3 | Lowest priority (5); waited longest | **Match** |

**Hand-Calculated Sequence**: `Meena(S1) Kumar(S1) Lata(S2) Deepak(S2) Ravi(S3) John(S3) Priya(S4) Asha(S5)`  
**Actual Program Output**: `Meena(S1) Kumar(S1) Lata(S2) Deepak(S2) Ravi(S3) John(S3) Priya(S4) Asha(S5)`  
**Audit Verification**: **100% Exact Match.**

---

## 5. Growth Investigation & Benchmark

Admit (`add`) and Serve (`poll`) operations were measured across three queue sizes on Java 25.0.1 LTS:

| Queue Capacity ($N$) | Enqueue Time (`add()`) | Dequeue Time (`poll()`) | Asymptotic Growth |
| :---: | :---: | :---: | :---: |
| **1,000** | 2.189 ms | 1.200 ms | $O(\log 1,000) \approx 10$ heap sifts |
| **10,000** | 1.838 ms | 2.238 ms | $O(\log 10,000) \approx 14$ heap sifts |
| **100,000** | 12.533 ms | 16.249 ms | $O(\log 100,000) \approx 17$ heap sifts |

---

## 6. Starvation Inquiry & Fair Aging Rule Design

### 6.1 The Starvation Problem
In medical triage, if critical cases (Severity 1 and 2) continue to arrive at the emergency gate continuously, a minor Severity-5 patient (e.g., Asha with mild fever) will **starve indefinitely**, suffering potentially fatal complications due to unaddressed medical deterioration.

### 6.2 Designing a Fair Aging Rule
To guarantee a mathematical upper bound on waiting time without compromising emergency care, we introduce **Priority Aging**:
$$\text{effectiveSeverity} = \max\left(1, \text{initialSeverity} - \left\lfloor \frac{\text{elapsedWaitMinutes}}{T_{\text{promote}}} \right\rfloor\right)$$
Where $T_{\text{promote}} = 30\text{ minutes}$.
- An S5 patient waiting for 60 minutes automatically ages to effective priority S3.
- After 120 minutes, the patient reaches S1, ensuring medical examination regardless of subsequent non-critical arrivals.

---

## 7. Deployment Decision for Metropolitan Hospital (5,000 Patients/Day)

- **Daily Throughput Requirement**: 5,000 arrivals over 24 hours = $\approx 3.5$ patients per minute (peak surge $\approx 25$ patients/min).
- **Processing Capacity**: Our benchmark demonstrates that `PriorityQueue` easily processes 10,000 enqueues and dequeues in **under 30 milliseconds**.
- **CPU Resource Utilization**: Consumes less than **0.001% of a single CPU core**.
- **Deployment Verdict**: **STRONGLY APPROVED FOR PRODUCTION DEPLOYMENT**.

---

## 8. Alternative Concept & Complexity Analysis

### Comparison: `PriorityQueue` vs. Sorted `LinkedList` vs. Array of 5 FIFO Queues

| Architecture | Enqueue / Admit | Dequeue / Serve Next | Aging Feasibility | Memory Overhead |
| :--- | :---: | :---: | :---: | :--- |
| **`PriorityQueue` (Min-Heap)** | $\mathbf{O(\log n)}$ | $\mathbf{O(\log n)}$ | Difficult ($O(n)$ heap rebuild) | Moderate (`Object[]` array) |
| **Sorted `LinkedList`** | $\mathbf{O(n)}$ (Linear insertion) | $\mathbf{O(1)}$ (Poll head) | Moderate | High (24B node overhead) |
| **Array of 5 FIFO Queues** | $\mathbf{O(1)}$ (`queues[sev].add()`) | $\mathbf{O(1)}$ (Check 5 buckets) | **Optimal** ($O(1)$ promote between buckets) | **Minimal** (5 circular array buffers) |

---

## 9. Compilation & Execution Instructions

```bash
javac -d Q6/bin Q6/src/*.java
java -cp Q6/bin Triage
java -cp Q6/bin TriageEvaluation
```

### Execution Output Screenshot
![Q6 Output Screenshot](output/screenshot.png)
