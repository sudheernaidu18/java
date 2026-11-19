package q7;

import java.util.ArrayDeque;

/**
 * Question 7: Day-4 Design Challenge - Multi-Level Bucket Queue Redesign
 *
 * Diagnosed Bottlenecks in Original PriorityQueue:
 *  1. Dynamic Aging / Reprioritization is O(N^2) (Heap rebuild required).
 *  2. Enqueue and Dequeue suffer O(log N) comparator & sift overhead.
 *  3. Requires synthetic 'arrival' counter to enforce FIFO stability.
 *
 * Revised Architecture:
 *  Multi-Level Priority Queue (MLPQ) with discrete severity buckets [1..5],
 *  backed by cache-friendly circular ArrayDeques.
 *  - Enqueue: O(1)
 *  - Dequeue: O(1) (at most 5 bucket checks)
 *  - Aging:   O(1) promotion between adjacent queues
 *  - FIFO:    Strictly guaranteed by queue structure
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class EmergencyTriageRedesign {

    public static class PatientRecord {
        public final String id;
        public final String name;
        public int currentSeverity; // 1 (Critical) to 5 (Minor)
        public final long arrivalTimestamp;
        public long lastPromotedTimestamp;

        public PatientRecord(String id, String name, int severity, long timestamp) {
            this.id = id;
            this.name = name;
            this.currentSeverity = Math.max(1, Math.min(5, severity));
            this.arrivalTimestamp = timestamp;
            this.lastPromotedTimestamp = timestamp;
        }

        @Override
        public String toString() {
            return String.format("%s(%s, S%d)", name, id, currentSeverity);
        }
    }

    // 5 discrete circular FIFO queues (one for each severity level 1..5)
    @SuppressWarnings("unchecked")
    private final ArrayDeque<PatientRecord>[] buckets = new ArrayDeque[5];
    private int totalPatients = 0;

    public EmergencyTriageRedesign() {
        for (int i = 0; i < 5; i++) {
            buckets[i] = new ArrayDeque<>(64);
        }
    }

    /**
     * O(1) Admission: Directly pushes to tail of corresponding severity bucket.
     */
    public void admit(PatientRecord patient) {
        int idx = patient.currentSeverity - 1;
        buckets[idx].addLast(patient);
        totalPatients++;
    }

    /**
     * O(1) Doctor Consultation: Scans at most 5 buckets and polls from highest priority.
     */
    public PatientRecord serveNext() {
        if (totalPatients == 0) return null;
        for (int i = 0; i < 5; i++) {
            if (!buckets[i].isEmpty()) {
                totalPatients--;
                return buckets[i].pollFirst();
            }
        }
        return null;
    }

    /**
     * O(k) Starvation-Free Aging Rule:
     * Inspects head of buckets 2..5. If waiting time exceeds threshold,
     * promotes patient to next higher priority queue in O(1) per patient!
     */
    public int applyAgingPolicy(long currentSimTime, long maxWaitBeforePromotionMs) {
        int promotions = 0;
        for (int sev = 4; sev >= 1; sev--) { // Buckets 4, 3, 2, 1 (Severities 5, 4, 3, 2)
            ArrayDeque<PatientRecord> currentBucket = buckets[sev];
            while (!currentBucket.isEmpty()) {
                PatientRecord head = currentBucket.peekFirst();
                if (currentSimTime - head.lastPromotedTimestamp >= maxWaitBeforePromotionMs) {
                    PatientRecord promoted = currentBucket.pollFirst();
                    promoted.currentSeverity--;
                    promoted.lastPromotedTimestamp = currentSimTime;
                    buckets[sev - 1].addLast(promoted);
                    promotions++;
                } else {
                    // Head is earliest arrival in this bucket; if head hasn't expired, none have!
                    break;
                }
            }
        }
        return promotions;
    }

    public int size() {
        return totalPatients;
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("Q7: REDESIGNED MULTI-LEVEL EMERGENCY TRIAGE SYSTEM DEMO");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        EmergencyTriageRedesign triage = new EmergencyTriageRedesign();
        long now = System.currentTimeMillis();

        System.out.println("--- 1. Enqueueing Heterogeneous Patient Stream (O(1)) ---");
        triage.admit(new PatientRecord("P01", "Ravi", 3, now));
        triage.admit(new PatientRecord("P02", "Meena", 1, now + 10));
        triage.admit(new PatientRecord("P03", "John", 3, now + 20));
        triage.admit(new PatientRecord("P04", "Asha", 5, now + 30));
        triage.admit(new PatientRecord("P05", "Kumar", 1, now + 40));
        triage.admit(new PatientRecord("P06", "Lata", 2, now + 50));

        System.out.println("Total Patients in Triage: " + triage.size());

        // Demonstrate Aging: Asha (S5) has waited long enough
        System.out.println("\n--- 2. Simulating Time Passage (60 mins) & Applying Aging ---");
        long later = now + (60 * 60 * 1000L); // 60 mins later
        int promotedCount = triage.applyAgingPolicy(later, 30 * 60 * 1000L);
        System.out.println("Promoted Patients Count: " + promotedCount);

        System.out.println("\n--- 3. Serving Patients in Redesigned Order (O(1)) ---");
        PatientRecord p;
        int rank = 1;
        while ((p = triage.serveNext()) != null) {
            System.out.printf("  Doctor Call %d: %s\n", rank++, p);
        }
    }
}
