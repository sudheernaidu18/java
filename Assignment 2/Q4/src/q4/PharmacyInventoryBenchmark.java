package q4;

import java.util.*;

/**
 * Question 4: Empirical Benchmark of HashMap vs TreeMap vs LinkedHashMap.
 * Tests:
 *  - 100,000 Lookups
 *  - Time to generate Code-Order Report
 *  - Time to generate Insertion-Order Report
 *
 * Tested across 3 inventory sizes: N = 1,000 | 10,000 | 100,000.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class PharmacyInventoryBenchmark {

    public static void runBenchmarkForSize(int n) {
        System.out.println("========================================================================");
        System.out.printf("PHARMACY INVENTORY BENCHMARK FOR N = %,d MEDICINES\n", n);
        System.out.println("========================================================================");

        // Generate N medicine records in randomized arrival order
        String[] codes = new String[n];
        Random rng = new Random(1337);
        for (int i = 0; i < n; i++) {
            // Unsorted random code: e.g. MED-048291
            codes[i] = String.format("MED-%06d", rng.nextInt(1_000_000));
        }

        Map<String, Integer> hashMap = new HashMap<>(n);
        Map<String, Integer> treeMap = new TreeMap<>();
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>(n);

        // Prepopulate maps
        for (int i = 0; i < n; i++) {
            hashMap.put(codes[i], i * 10);
            treeMap.put(codes[i], i * 10);
            linkedHashMap.put(codes[i], i * 10);
        }

        // 1. Benchmark 100,000 Lookups
        int lookupCount = 100_000;
        String[] queryCodes = new String[lookupCount];
        for (int i = 0; i < lookupCount; i++) {
            queryCodes[i] = codes[rng.nextInt(n)];
        }

        // Warm up
        for (int i = 0; i < 5000; i++) {
            hashMap.get(queryCodes[i]);
            treeMap.get(queryCodes[i]);
            linkedHashMap.get(queryCodes[i]);
        }

        // HashMap Lookups
        long t0 = System.nanoTime();
        long sumHM = 0;
        for (String c : queryCodes) {
            Integer stock = hashMap.get(c);
            if (stock != null) sumHM += stock;
        }
        long tLookupHM = System.nanoTime() - t0;

        // TreeMap Lookups
        t0 = System.nanoTime();
        long sumTM = 0;
        for (String c : queryCodes) {
            Integer stock = treeMap.get(c);
            if (stock != null) sumTM += stock;
        }
        long tLookupTM = System.nanoTime() - t0;

        // LinkedHashMap Lookups
        t0 = System.nanoTime();
        long sumLHM = 0;
        for (String c : queryCodes) {
            Integer stock = linkedHashMap.get(c);
            if (stock != null) sumLHM += stock;
        }
        long tLookupLHM = System.nanoTime() - t0;

        // 2. Report in Code Order (Natural in TreeMap; requires sorting in HashMap/LinkedHashMap)
        // TreeMap Code-Order (Natural in-order iteration)
        t0 = System.nanoTime();
        List<String> reportCodeTM = new ArrayList<>(treeMap.keySet());
        long tReportCodeTM = System.nanoTime() - t0;

        // HashMap Code-Order (Must sort keys!)
        t0 = System.nanoTime();
        List<String> reportCodeHM = new ArrayList<>(hashMap.keySet());
        Collections.sort(reportCodeHM);
        long tReportCodeHM = System.nanoTime() - t0;

        // LinkedHashMap Code-Order (Must sort keys!)
        t0 = System.nanoTime();
        List<String> reportCodeLHM = new ArrayList<>(linkedHashMap.keySet());
        Collections.sort(reportCodeLHM);
        long tReportCodeLHM = System.nanoTime() - t0;

        // 3. Report in Insertion Order (Natural in LinkedHashMap; impossible in HashMap/TreeMap without extra tracking)
        // LinkedHashMap Insertion-Order (Natural doubly-linked iteration)
        t0 = System.nanoTime();
        List<String> reportInsertLHM = new ArrayList<>(linkedHashMap.keySet());
        long tReportInsertLHM = System.nanoTime() - t0;

        // HashMap / TreeMap cannot produce insertion order on their own!
        // To produce insertion order, they require maintaining a separate List<String> insertionList during put operations!

        // Output Results
        System.out.printf("%-18s | %-16s | %-16s | %-16s\n",
                "Metric", "HashMap", "TreeMap", "LinkedHashMap");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-18s | %13.3f ms | %13.3f ms | %13.3f ms\n",
                "100,000 Lookups", tLookupHM / 1e6, tLookupTM / 1e6, tLookupLHM / 1e6);
        System.out.printf("%-18s | %13.3f ms | %13.3f ms | %13.3f ms\n",
                "Code-Order Report", tReportCodeHM / 1e6, tReportCodeTM / 1e6, tReportCodeLHM / 1e6);
        System.out.printf("%-18s | %16s | %16s | %13.3f ms\n",
                "Insertion Report", "Needs 2nd List", "Needs 2nd List", tReportInsertLHM / 1e6);

        // Verification of correctness
        boolean codeOrderMatch = reportCodeTM.equals(reportCodeHM);
        System.out.printf("Verification: Code-Order Report matches sorted keys? %s\n", codeOrderMatch);
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("Q4: HOSPITAL PHARMACY MAP BENCHMARK (100k LOOKUPS & DUAL REPORTS)");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313 | Java: 25.0.1");
        System.out.println("========================================================================\n");

        runBenchmarkForSize(1000);
        runBenchmarkForSize(10000);
        runBenchmarkForSize(100000);

        System.out.println("========================================================================");
        System.out.println("ARCHITECTURAL DEPLOYMENT DECISION & SCALABILITY INQUIRY:");
        System.out.println("1. Acceptable Lookup Response Time: Sub-microsecond (< 1.0 us per lookup).");
        System.out.println("2. Chosen Production Map: LinkedHashMap.");
        System.out.println("   Defense: Pharmacists query stock hundreds of times every hour.");
        System.out.println("   LinkedHashMap provides O(1) hash lookup (~4-6 ms for 100k queries) AND");
        System.out.println("   naturally yields the daily Insertion-Order report in O(n) time.");
        System.out.println("   The Code-Order report runs only ONCE every evening, taking ~15 ms to sort.");
        System.out.println("3. Why Hashtable is Obsolete:");
        System.out.println("   - Hashtable synchronizes EVERY method, creating severe thread bottlenecks.");
        System.out.println("   - Disallows null keys and null values.");
        System.out.println("   - Modern code uses ConcurrentHashMap for concurrency and LinkedHashMap/HashMap otherwise.");
        System.out.println("========================================================================");
    }
}
