package q2;

import java.util.*;

/**
 * Question 2: Empirical Performance Benchmark of ArrayList vs LinkedList vs ArrayDeque.
 * Evaluates:
 *  1. Add at end
 *  2. Insert and remove at front
 *  3. Insert and remove in middle
 *  4. Access k-th element (Random Access)
 *  5. Full iteration
 *
 * Tested across 3 playlist sizes: N = 1,000 | 10,000 | 100,000.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class PlaylistBenchmark {

    public static void runBenchmarkForSize(int n) {
        System.out.println("========================================================================");
        System.out.printf("PLAYLIST BENCHMARK FOR N = %,d SONGS\n", n);
        System.out.println("========================================================================");

        // Prepopulate lists
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            arrayList.add(i);
            linkedList.add(i);
            arrayDeque.add(i);
        }

        int ops = Math.min(n, 2000); // number of targeted test operations for front/mid

        // 1. Add at end (Append)
        long t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) arrayList.add(i);
        long tAddEndAL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) linkedList.add(i);
        long tAddEndLL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) arrayDeque.addLast(i);
        long tAddEndAD = System.nanoTime() - t0;

        // 2. Insert at Front (add at index 0)
        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) arrayList.add(0, i);
        long tInsertFrontAL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) linkedList.add(0, i);
        long tInsertFrontLL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) arrayDeque.addFirst(i);
        long tInsertFrontAD = System.nanoTime() - t0;

        // 3. Remove from Front (remove index 0)
        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) arrayList.remove(0);
        long tRemoveFrontAL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) linkedList.remove(0);
        long tRemoveFrontLL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) arrayDeque.pollFirst();
        long tRemoveFrontAD = System.nanoTime() - t0;

        // 4. Insert in Middle (index = size / 2)
        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) arrayList.add(arrayList.size() / 2, i);
        long tInsertMidAL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) linkedList.add(linkedList.size() / 2, i);
        long tInsertMidLL = System.nanoTime() - t0;

        // 5. Remove from Middle (index = size / 2)
        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) arrayList.remove(arrayList.size() / 2);
        long tRemoveMidAL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        for (int i = 0; i < ops; i++) linkedList.remove(linkedList.size() / 2);
        long tRemoveMidLL = System.nanoTime() - t0;

        // 6. Access k-th element (Random Access: 5,000 lookups)
        int lookupCount = 5000;
        Random rng = new Random(42);
        int[] indices = new int[lookupCount];
        for (int i = 0; i < lookupCount; i++) indices[i] = rng.nextInt(n);

        long dummy = 0;
        t0 = System.nanoTime();
        for (int idx : indices) dummy += arrayList.get(idx);
        long tGetAL = System.nanoTime() - t0;

        long dummy2 = 0;
        t0 = System.nanoTime();
        for (int idx : indices) dummy2 += linkedList.get(idx);
        long tGetLL = System.nanoTime() - t0;

        // 7. Full Iteration
        t0 = System.nanoTime();
        long sumAL = 0;
        for (int val : arrayList) sumAL += val;
        long tIterAL = System.nanoTime() - t0;

        t0 = System.nanoTime();
        long sumLL = 0;
        for (int val : linkedList) sumLL += val;
        long tIterLL = System.nanoTime() - t0;

        // Print Results Table
        System.out.printf("%-26s | %-15s | %-15s | %-15s\n",
                "Operation (" + ops + " ops)", "ArrayList (ms)", "LinkedList (ms)", "ArrayDeque (ms)");
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-26s | %15.3f | %15.3f | %15.3f\n",
                "Add at End", tAddEndAL / 1e6, tAddEndLL / 1e6, tAddEndAD / 1e6);
        System.out.printf("%-26s | %15.3f | %15.3f | %15.3f\n",
                "Insert at Front", tInsertFrontAL / 1e6, tInsertFrontLL / 1e6, tInsertFrontAD / 1e6);
        System.out.printf("%-26s | %15.3f | %15.3f | %15.3f\n",
                "Remove from Front", tRemoveFrontAL / 1e6, tRemoveFrontLL / 1e6, tRemoveFrontAD / 1e6);
        System.out.printf("%-26s | %15.3f | %15.3f | %15s\n",
                "Insert in Middle", tInsertMidAL / 1e6, tInsertMidLL / 1e6, "N/A (Deque)");
        System.out.printf("%-26s | %15.3f | %15.3f | %15s\n",
                "Remove from Middle", tRemoveMidAL / 1e6, tRemoveMidLL / 1e6, "N/A (Deque)");
        System.out.printf("%-26s | %15.3f | %15.3f | %15s\n",
                "Get k-th Element (5k lookups)", tGetAL / 1e6, tGetLL / 1e6, "N/A (Deque)");
        System.out.printf("%-26s | %15.3f | %15.3f | %15s\n",
                "Full Iteration (" + n + " items)", tIterAL / 1e6, tIterLL / 1e6, "N/A");
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("Q2: EXPERIMENTAL BENCHMARK: ARRAYLIST VS LINKEDLIST VS ARRAYDEQUE");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313 | Java: 25.0.1");
        System.out.println("========================================================================\n");

        runBenchmarkForSize(1000);
        runBenchmarkForSize(10000);
        runBenchmarkForSize(100000);

        System.out.println("========================================================================");
        System.out.println("ARCHITECTURAL EVALUATION & BOUNDARY DECISION:");
        System.out.println("1. When is ArrayList a reasonable engineering choice?");
        System.out.println("   - When operations are predominantly random access (get(k)), append at end,");
        System.out.println("   - and full sequential iteration, benefiting from contiguous L1/L2 cache prefetching.");
        System.out.println("2. When to STOP recommending ArrayList?");
        System.out.println("   - When frequent insertions/removals occur at the FRONT or MIDDLE of large lists,");
        System.out.println("   - because System.arraycopy forces O(n) element shifting per operation.");
        System.out.println("3. Changed Condition Response (Up Next Queue):");
        System.out.println("   - ArrayDeque is strictly superior to both LinkedList and ArrayList.");
        System.out.println("   - It provides O(1) amortized add/remove at BOTH ends without pointer node overhead.");
        System.out.println("========================================================================");
    }
}
