package q1;

/**
 * Question 1: Empirical Benchmark of String vs StringBuilder vs StringBuffer.
 * Tests 3 deliberately contrasting workloads:
 *  - Workload 1: 1 short ticket (5 fields)
 *  - Workload 2: 10,000 tickets with 50 fields each
 *  - Workload 3: 50,000 tickets with 20 fields each
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class TicketGeneratorBenchmark {

    private static final String[] FIELD_VALUES = new String[50];
    static {
        for (int i = 0; i < 50; i++) {
            FIELD_VALUES[i] = "Field_" + i + ":Val_" + (i * 17);
        }
    }

    private static long getUsedMemory() {
        System.gc();
        System.gc();
        try { Thread.sleep(20); } catch (InterruptedException ignored) {}
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    // Benchmark loop for String (+)
    public static long benchmarkString(int numTickets, int numFields) {
        long start = System.nanoTime();
        for (int t = 0; t < numTickets; t++) {
            String s = "PNR:" + t;
            for (int f = 0; f < numFields; f++) {
                s = s + "|" + FIELD_VALUES[f];
            }
        }
        return System.nanoTime() - start;
    }

    // Benchmark loop for StringBuilder
    public static long benchmarkStringBuilder(int numTickets, int numFields) {
        long start = System.nanoTime();
        for (int t = 0; t < numTickets; t++) {
            StringBuilder sb = new StringBuilder();
            sb.append("PNR:").append(t);
            for (int f = 0; f < numFields; f++) {
                sb.append("|").append(FIELD_VALUES[f]);
            }
            String s = sb.toString();
        }
        return System.nanoTime() - start;
    }

    // Benchmark loop for StringBuffer
    public static long benchmarkStringBuffer(int numTickets, int numFields) {
        long start = System.nanoTime();
        for (int t = 0; t < numTickets; t++) {
            StringBuffer sb = new StringBuffer();
            sb.append("PNR:").append(t);
            for (int f = 0; f < numFields; f++) {
                sb.append("|").append(FIELD_VALUES[f]);
            }
            String s = sb.toString();
        }
        return System.nanoTime() - start;
    }

    public static void runWorkload(String label, int numTickets, int numFields) {
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("WORKLOAD: %s (%d tickets x %d fields)\n", label, numTickets, numFields);
        System.out.println("------------------------------------------------------------------------");

        // Warm up JVM
        benchmarkStringBuilder(Math.min(numTickets, 500), numFields);
        benchmarkString(Math.min(numTickets, 500), numFields);
        benchmarkStringBuffer(Math.min(numTickets, 500), numFields);

        // 1. String (+)
        long memBeforeStr = getUsedMemory();
        long timeStr = benchmarkString(numTickets, numFields);
        long memAfterStr = getUsedMemory();
        long memDiffStr = Math.max(0, memAfterStr - memBeforeStr);
        long intermediateObjectsStr = (long) numTickets * (numFields * 2L);

        // 2. StringBuilder
        long memBeforeSb = getUsedMemory();
        long timeSb = benchmarkStringBuilder(numTickets, numFields);
        long memAfterSb = getUsedMemory();
        long memDiffSb = Math.max(0, memAfterSb - memBeforeSb);
        long intermediateObjectsSb = numTickets; // only the final String + builder buffer

        // 3. StringBuffer
        long memBeforeSbuf = getUsedMemory();
        long timeSbuf = benchmarkStringBuffer(numTickets, numFields);
        long memAfterSbuf = getUsedMemory();
        long memDiffSbuf = Math.max(0, memAfterSbuf - memBeforeSbuf);
        long intermediateObjectsSbuf = numTickets;

        System.out.printf("%-15s | %-15s | %-18s | %-18s\n",
                "Approach", "Exec Time (ms)", "Est Objects Created", "Memory Delta (KB)");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-15s | %15.3f | %18d | %18.2f\n",
                "String (+)", timeStr / 1_000_000.0, intermediateObjectsStr, memDiffStr / 1024.0);
        System.out.printf("%-15s | %15.3f | %18d | %18.2f\n",
                "StringBuilder", timeSb / 1_000_000.0, intermediateObjectsSb, memDiffSb / 1024.0);
        System.out.printf("%-15s | %15.3f | %18d | %18.2f\n",
                "StringBuffer", timeSbuf / 1_000_000.0, intermediateObjectsSbuf, memDiffSbuf / 1024.0);

        double speedup = (double) timeStr / Math.max(1, timeSb);
        System.out.printf(">>> Observed Result: StringBuilder is %.1fx faster than String (+)\n\n", speedup);
    }

    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("Q1: EMPIRICAL BENCHMARK EXPERIMENT - STRING IMMUTABILITY UNDER LOAD");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313 | Java: 25.0.1");
        System.out.println("========================================================================\n");

        // Workload 1: One short ticket (5 fields)
        runWorkload("Workload 1 (Trivial Scale)", 1, 5);

        // Workload 2: 10,000 tickets with 50 fields each (Specified in prompt)
        runWorkload("Workload 2 (Heavy Load: 10k x 50)", 10000, 50);

        // Workload 3: 50,000 tickets with 20 fields each (Custom high-concurrency batch)
        runWorkload("Workload 3 (High Ticket Volume: 50k x 20)", 50000, 20);

        System.out.println("========================================================================");
        System.out.println("MULTITHREADED BOOKING SERVER ARCHITECTURAL DECISION:");
        System.out.println("1. Per-Ticket Generation: Recommended class is StringBuilder.");
        System.out.println("   Defense: Each ticket is generated within a single request worker thread");
        System.out.println("   (thread-confined). Synchronization is redundant and causes thread lock overhead.");
        System.out.println("2. Shared Audit Log: Recommended class is StringBuffer (or ConcurrentQueue).");
        System.out.println("   Defense: Multiple concurrent threads mutate the shared buffer simultaneously.");
        System.out.println("   StringBuffer's synchronized methods guarantee memory visibility and atomic appends.");
        System.out.println("========================================================================");
    }
}
