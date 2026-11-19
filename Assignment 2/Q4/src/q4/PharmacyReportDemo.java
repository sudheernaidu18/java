package q4;

import java.util.*;

/**
 * Question 4: Hospital Pharmacy Stock Lookup & Reporting Demo
 * Compares HashMap, TreeMap, and LinkedHashMap.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class PharmacyReportDemo {

    public static class Medicine {
        public final String code;
        public final String name;
        public final int stock;
        public final double unitPrice;

        public Medicine(String code, String name, int stock, double unitPrice) {
            this.code = code;
            this.name = name;
            this.stock = stock;
            this.unitPrice = unitPrice;
        }

        @Override
        public String toString() {
            return String.format("[%s] %-16s | Stock: %4d | Rs. %6.2f", code, name, stock, unitPrice);
        }
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("Q4: PHARMACY INVENTORY - HASHMAP VS TREEMAP VS LINKEDHASHMAP");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // Medicines added in specific arrival order
        Medicine[] sampleMedicines = {
            new Medicine("MED-802", "Paracetamol", 500, 2.50),
            new Medicine("MED-105", "Amoxicillin", 200, 12.00),
            new Medicine("MED-940", "Cetirizine", 350, 4.00),
            new Medicine("MED-310", "Metformin", 400, 5.50),
            new Medicine("MED-220", "Azithromycin", 150, 25.00)
        };

        Map<String, Medicine> hashMap = new HashMap<>();
        Map<String, Medicine> treeMap = new TreeMap<>();
        Map<String, Medicine> linkedHashMap = new LinkedHashMap<>();

        for (Medicine m : sampleMedicines) {
            hashMap.put(m.code, m);
            treeMap.put(m.code, m);
            linkedHashMap.put(m.code, m);
        }

        // 1. Report in Code Order (Natural TreeMap order)
        System.out.println("--- REPORT 1: Medicines in Code Order (TreeMap Natural Traversal) ---");
        for (Medicine m : treeMap.values()) {
            System.out.println("  " + m);
        }

        // 2. Report in Insertion Order (Natural LinkedHashMap order)
        System.out.println("\n--- REPORT 2: Medicines in Insertion Order (LinkedHashMap Natural Traversal) ---");
        for (Medicine m : linkedHashMap.values()) {
            System.out.println("  " + m);
        }

        // 3. HashMap Demonstration (Arbitrary bucket order)
        System.out.println("\n--- HashMap Traversal (Arbitrary / Bucketed Order - Unordered!) ---");
        for (Medicine m : hashMap.values()) {
            System.out.println("  " + m);
        }
    }
}
