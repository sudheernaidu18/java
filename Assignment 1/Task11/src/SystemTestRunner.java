/**
 * Task 11: Implement, Test and Defend the Design
 * Test Suite Runner executing Normal, Boundary, and Invalid Test Cases,
 * plus 1-Day Financial Revenue Verification.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class SystemTestRunner {

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("TASK 11: FULL SYSTEM IMPLEMENTATION, TESTING & DEFENCE");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        SmartParkingSystem.CampusFacility facility =
                new SmartParkingSystem.CampusFacility(new int[]{2, 2}); // 2 floors with 2 slots each = 4 total capacity

        System.out.println("=== PART 1: 1-DAY FINANCIAL SCENARIO REVENUE VALIDATION ===");
        // 1. Visitor Two-Wheeler: 75 mins -> 2 hrs @ Rs 10 = Rs 20.0
        SmartParkingSystem.Vehicle v1 = new SmartParkingSystem.TwoWheeler("TN01-BK-001");
        facility.checkIn(v1, false, 0);
        double fee1 = facility.checkOut("TN01-BK-001", 75);
        System.out.println("Vehicle 1 (TwoWheeler, 75 mins):  Rs. " + fee1 + " [Expected: 20.0]");

        // 2. Visitor Car: 120 mins -> 2 hrs @ Rs 30 = Rs 60.0
        SmartParkingSystem.Vehicle v2 = new SmartParkingSystem.Car("TN02-CR-002");
        facility.checkIn(v2, false, 0);
        double fee2 = facility.checkOut("TN02-CR-002", 120);
        System.out.println("Vehicle 2 (Car, 120 mins):         Rs. " + fee2 + " [Expected: 60.0]");

        // 3. Visitor ElectricCar: 130 mins + 15 kWh -> 3 hrs @ 30 (90) + 15*12 (180) = Rs 270.0
        SmartParkingSystem.Vehicle v3 = new SmartParkingSystem.ElectricCar("TN03-EV-003");
        facility.checkIn(v3, false, 15);
        double fee3 = facility.checkOut("TN03-EV-003", 130);
        System.out.println("Vehicle 3 (ElectricCar, 130m, 15k):Rs. " + fee3 + " [Expected: 270.0]");

        // 4. Student Car with Monthly Pass: 180 mins -> Rs 0.0
        SmartParkingSystem.Vehicle v4 = new SmartParkingSystem.Car("TN04-ST-004");
        facility.checkIn(v4, true, 0);
        double fee4 = facility.checkOut("TN04-ST-004", 180);
        System.out.println("Vehicle 4 (Student Pass, 180 mins):Rs. " + fee4 + " [Expected: 0.0]");

        // 5. Staff ElectricCar with Monthly Pass: 120 mins + 10 kWh -> Rs 0 + 120 = Rs 120.0
        SmartParkingSystem.Vehicle v5 = new SmartParkingSystem.ElectricCar("TN05-SF-005");
        facility.checkIn(v5, true, 10);
        double fee5 = facility.checkOut("TN05-SF-005", 120);
        System.out.println("Vehicle 5 (Staff Pass EV, 10 kWh): Rs. " + fee5 + " [Expected: 120.0]");

        double totalCollection = fee1 + fee2 + fee3 + fee4 + fee5;
        System.out.println("-----------------------------------------------------------------");
        System.out.println("TOTAL REVENUE COLLECTED:           Rs. " + totalCollection + " [Hand-Calculated: 470.0]");
        if (Double.compare(totalCollection, 470.0) == 0) {
            System.out.println(">>> FINANCIAL AUDIT VERIFICATION: 100% MATCH SUCCESSFUL! <<<");
        }

        System.out.println("\n=== PART 2: BOUNDARY & EDGE CASE VALIDATION ===");
        // Fill all 4 slots of facility
        facility.checkIn(new SmartParkingSystem.Car("CAR-01"), false, 0); // (0,0)
        facility.checkIn(new SmartParkingSystem.Car("CAR-02"), false, 0); // (0,1)
        facility.checkIn(new SmartParkingSystem.Car("CAR-03"), false, 0); // (1,0)
        SmartParkingSystem.ParkingTicket lastTicket = facility.checkIn(new SmartParkingSystem.Car("CAR-04"), false, 0); // (1,1)
        System.out.println("Boundary Check: Last Slot Allocated at Floor " + lastTicket.getFloor() + ", Slot " + lastTicket.getSlot());

        // Attempt 5th vehicle when capacity is 4
        SmartParkingSystem.ParkingTicket overflowTicket = facility.checkIn(new SmartParkingSystem.Car("CAR-OVERFLOW"), false, 0);
        System.out.println("Boundary Check: Allocation on Full Facility: " + overflowTicket + " (Expected: null)");

        // Zero minute parking
        double zeroMinFee = new SmartParkingSystem.Car("TEMP-01").calculateFee(0);
        System.out.println("Boundary Check: Zero-minute parking fee: Rs. " + zeroMinFee + " (Expected: 0.0)");

        // Stay exactly at fee slab boundary (60 min vs 61 min)
        double fee60 = new SmartParkingSystem.Car("TEMP-02").calculateFee(60);
        double fee61 = new SmartParkingSystem.Car("TEMP-03").calculateFee(61);
        System.out.println("Boundary Check: Stay 60 min -> Rs. " + fee60 + " (Expected: 30.0)");
        System.out.println("Boundary Check: Stay 61 min -> Rs. " + fee61 + " (Expected: 60.0)");

        System.out.println("\n=== PART 3: INVALID INPUT & ERROR HANDLING ===");
        // Duplicate registration check-in
        System.out.println("Testing Duplicate Check-in of CAR-01:");
        facility.checkIn(new SmartParkingSystem.Car("CAR-01"), false, 0);

        // Invalid direct slot release
        boolean invalidRelease1 = facility.releaseDirect(9, 9);
        boolean invalidRelease2 = facility.releaseDirect(0, 0); // occupied, frees it
        boolean invalidRelease3 = facility.releaseDirect(0, 0); // already free!
        System.out.println("Invalid release tests: [9,9]=" + invalidRelease1 + ", [0,0 second time]=" + invalidRelease3);

        // Blank registration validation
        try {
            new SmartParkingSystem.Car("");
        } catch (IllegalArgumentException ex) {
            System.out.println("Caught Expected Error for blank plate: " + ex.getMessage());
        }
    }
}
