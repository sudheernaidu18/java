import java.util.*;

/**
 * Question 3: Deep Investigation of the hashCode-equals Contract
 * Tests 3 deliberate scenarios:
 *  1. Scans where HashSet appears to work correctly (Same object reference)
 *  2. Scans deliberately exposing duplicate entries (Missing hashCode override)
 *  3. Scans where a key field is mutated after insertion (Broken bucket invariants)
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class ContractInvestigation {

    // Faulty class: Overrides equals() but NOT hashCode()
    static class BrokenStudent {
        String regNo;
        String name;

        BrokenStudent(String regNo, String name) {
            this.regNo = regNo;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BrokenStudent)) return false;
            return regNo != null && regNo.equals(((BrokenStudent) o).regNo);
        }

        // hashCode() NOT overridden -> inherits System.identityHashCode from Object!
        @Override
        public String toString() { return regNo + ":" + name; }
    }

    // Mutable student for Test 3
    static class MutableStudent {
        String regNo;
        String name;

        MutableStudent(String regNo, String name) {
            this.regNo = regNo;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MutableStudent)) return false;
            return Objects.equals(regNo, ((MutableStudent) o).regNo);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(regNo);
        }

        @Override
        public String toString() { return regNo + ":" + name; }
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("Q3: HASHCODE - EQUALS CONTRACT INVESTIGATION & VALIDATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // -------------------------------------------------------------
        // TEST 1: Where HashSet APPEARS to work correctly
        // -------------------------------------------------------------
        System.out.println("--- TEST 1: Apparent False Correctness (Same Reference) ---");
        Set<BrokenStudent> test1Set = new HashSet<>();
        BrokenStudent s1 = new BrokenStudent("192311001", "Arun");
        test1Set.add(s1);
        test1Set.add(s1); // Adding identical reference

        System.out.println("Prediction: Size = 1 (Reference identity short-circuits hash check)");
        System.out.println("Actual Observed Size: " + test1Set.size());
        System.out.println("Why it appeared to work: In HashMap.putVal(), (p.hash == hash && ((k = p.key) == key ...))");
        System.out.println("Because (s1 == s1) evaluates to true, the entry was recognized without calling equals()!\n");

        // -------------------------------------------------------------
        // TEST 2: Deliberately exposing duplicate entries (Missing hashCode)
        // -------------------------------------------------------------
        System.out.println("--- TEST 2: Exposing Duplicate Leak (Different Heap References) ---");
        Set<BrokenStudent> test2Set = new HashSet<>();
        BrokenStudent scanGate1 = new BrokenStudent("192311001", "Arun");
        BrokenStudent scanGate2 = new BrokenStudent("192311002", "Divya");
        BrokenStudent scanGate3 = new BrokenStudent("192311001", "Arun"); // Logically duplicate!

        test2Set.add(scanGate1);
        test2Set.add(scanGate2);
        test2Set.add(scanGate3);

        System.out.println("Prediction: Size = 3 (Different System.identityHashCode values route to different buckets)");
        System.out.println("Actual Observed Size: " + test2Set.size());
        System.out.println("Resulting Elements in Set: " + test2Set);
        System.out.printf("Hashcode scanGate1: %d, scanGate3: %d (Mismatch!)\n",
                scanGate1.hashCode(), scanGate3.hashCode());
        System.out.println("Why duplicates appeared: Without hashCode(), scanGate1 and scanGate3 land in different");
        System.out.println("buckets; equals() is NEVER even evaluated between them!\n");

        // -------------------------------------------------------------
        // TEST 3: Mutating regNo after insertion (Bucket Trap)
        // -------------------------------------------------------------
        System.out.println("--- TEST 3: Mutating Key Field After Insertion (Bucket Trap) ---");
        Set<MutableStudent> test3Set = new HashSet<>();
        MutableStudent mutStudent = new MutableStudent("192311005", "Kiran");
        test3Set.add(mutStudent);
        System.out.println("Initial set contains Kiran: " + test3Set.contains(mutStudent));

        // Mutate regNo after addition!
        mutStudent.regNo = "192311999";
        System.out.println("After mutating regNo to 192311999:");
        System.out.println("Does set still contain mutStudent? " + test3Set.contains(mutStudent));
        System.out.println("Actual Set size: " + test3Set.size());
        System.out.println("Attempting remove(mutStudent): " + test3Set.remove(mutStudent));
        System.out.println("Set size after failed remove: " + test3Set.size());
        System.out.println("Why object is trapped: HashSet looks for Kiran in bucket index hash('192311999'),");
        System.out.println("but Kiran was stored in bucket index hash('192311005')! The element is permanently orphaned.\n");

        // -------------------------------------------------------------
        // VERIFICATION OF FIXED STUDENT CLASS
        // -------------------------------------------------------------
        System.out.println("--- VERIFICATION: Corrected Student Class with Contract Implemented ---");
        Set<Student> fixedSet = new HashSet<>();
        fixedSet.add(new Student("192311001", "Arun"));
        fixedSet.add(new Student("192311002", "Divya"));
        fixedSet.add(new Student("192311001", "Arun"));

        System.out.println("Fixed Set Expected Size: 2");
        System.out.println("Fixed Set Actual Size:   " + fixedSet.size());
        System.out.println("Verification Status: " + (fixedSet.size() == 2 ? "PASSED (100% DE-DUPLICATED)" : "FAILED"));
    }
}
