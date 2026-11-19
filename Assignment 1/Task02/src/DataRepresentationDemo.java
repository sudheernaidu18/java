/**
 * Task 02: Choose the Data Representation
 * Validates Jagged Array vs 1D Array mapping and demonstrates integer division risks.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class DataRepresentationDemo {

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("TASK 02: DATA REPRESENTATION & NUMERICAL PRECISION VALIDATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // 1. Jagged Array Representation
        System.out.println("--- 1. Jagged Array Representation (Non-Uniform Floors) ---");
        int[] slotsPerFloor = {20, 50, 100};
        String[][] jaggedSlots = new String[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            jaggedSlots[f] = new String[slotsPerFloor[f]];
        }
        for (int f = 0; f < jaggedSlots.length; f++) {
            System.out.println("Floor " + f + " capacity: " + jaggedSlots[f].length + " slots.");
        }

        // Test slot assignment and retrieval
        jaggedSlots[0][5] = "TN01-EV-1000";
        jaggedSlots[2][99] = "TN09-ZZ-9999";
        System.out.println("Slot (0, 5) holds: " + jaggedSlots[0][5]);
        System.out.println("Slot (2, 99) holds: " + jaggedSlots[2][99]);

        // 2. Flat 1D Array Representation with Prefix Sum Mapping
        System.out.println("\n--- 2. Flat 1D Array Representation with Prefix-Sum Offsets ---");
        int totalSlots = 0;
        int[] floorOffsets = new int[slotsPerFloor.length];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            floorOffsets[f] = totalSlots;
            totalSlots += slotsPerFloor[f];
        }
        String[] flatSlots = new String[totalSlots];
        System.out.println("Total flat array capacity: " + flatSlots.length + " slots.");

        // Map (2, 99) to flat index
        int targetFloor = 2, targetSlot = 99;
        int flatIndex = floorOffsets[targetFloor] + targetSlot;
        flatSlots[flatIndex] = "TN09-ZZ-9999";
        System.out.println("Mapped Floor 2, Slot 99 -> Flat Index " + flatIndex + " holds: " + flatSlots[flatIndex]);

        // 3. Integer Division Bug vs Safe Ceiling Demonstration
        System.out.println("\n--- 3. Fee Computation Precision Analysis ---");
        int[] testDurations = {15, 60, 61, 75, 120, 121};
        double ratePerHour = 30.0;

        System.out.printf("%-10s | %-16s | %-16s | %-16s%n",
                "Minutes", "Buggy Fee (int div)", "Safe Int Ceiling", "Math.ceil Fee");
        System.out.println("------------------------------------------------------------------");

        for (int minutes : testDurations) {
            // Buggy calculation: integer division truncates
            int buggyHours = minutes / 60;
            double buggyFee = buggyHours * ratePerHour;

            // Safe integer ceiling: (minutes + 59) / 60
            int safeHours = (minutes + 59) / 60;
            double safeFee = safeHours * ratePerHour;

            // Floating point ceiling
            int ceilHours = (int) Math.ceil((double) minutes / 60.0);
            double ceilFee = ceilHours * ratePerHour;

            System.out.printf("%-10d | Rs. %-12.2f | Rs. %-12.2f | Rs. %-12.2f%n",
                    minutes, buggyFee, safeFee, ceilFee);
        }

        System.out.println("\nConclusion: Integer division loses up to Rs. 30 per vehicle on partial hours.");
        System.out.println("Safe formula (minutes + 59) / 60 correctly charges every started hour.");
    }
}
