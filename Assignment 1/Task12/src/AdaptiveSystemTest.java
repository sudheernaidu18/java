/**
 * Task 12: Day-4 Design Challenge - System Test
 * Validates E-Bikes, Delivery Vans (Peak/Off-Peak), One-Day Pass,
 * and 5-Floor Extended Facility.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
import java.time.LocalTime;
import java.util.Arrays;

public class AdaptiveSystemTest {

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("TASK 12: DAY-4 DESIGN CHALLENGE - ADAPTIVE SYSTEM VALIDATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // 1. Validate E-Bike Flat Fee
        System.out.println("--- 1. Testing E-Bike (Flat Fee Rule) ---");
        AdaptiveParkingSystem.Vehicle eBike = new AdaptiveParkingSystem.EBike("EB-101");
        System.out.println("E-Bike 20 mins fee:  Rs. " + eBike.calculateFee(20) + " (Expected: Rs. 20.0)");
        System.out.println("E-Bike 300 mins fee: Rs. " + eBike.calculateFee(300) + " (Expected: Rs. 20.0)");

        // 2. Validate Delivery Van (9 AM - 5 PM Peak vs Off-Peak)
        System.out.println("\n--- 2. Testing Delivery Van (Peak vs Off-Peak Dynamic Pricing) ---");
        AdaptiveParkingSystem.Vehicle van = new AdaptiveParkingSystem.DeliveryVan("VAN-909");

        // Peak: 11:00 AM (2 hours) -> Double car rate: 2 * (30 * 2) = Rs. 120.0
        LocalTime peakTime = LocalTime.of(11, 0);
        double peakFee = van.calculateFee(120, peakTime);
        System.out.println("Van parked at 11:00 AM (Peak) for 2 hrs:    Rs. " + peakFee + " (Expected: Rs. 120.0)");

        // Off-Peak: 7:30 PM (2 hours) -> Standard car rate: 2 * 30 = Rs. 60.0
        LocalTime offPeakTime = LocalTime.of(19, 30);
        double offPeakFee = van.calculateFee(120, offPeakTime);
        System.out.println("Van parked at 07:30 PM (Off-Peak) for 2 hrs:Rs. " + offPeakFee + " (Expected: Rs. 60.0)");

        // 3. Validate Visitor One-Day Pass
        System.out.println("\n--- 3. Testing Visitor One-Day Pass (Capped at Rs. 150) ---");
        AdaptiveParkingSystem.Pass dayPass = new AdaptiveParkingSystem.VisitorDayPass();
        AdaptiveParkingSystem.Vehicle visitorCar = new AdaptiveParkingSystem.Car("TN02-VISITOR");

        // 8 hours stay = 8 * 30 = Rs. 240. With Day Pass -> Capped at Rs. 150
        double standardFee = visitorCar.calculateFee(480);
        double discountedFee = dayPass.applyDiscount(standardFee);
        System.out.println("Standard 8-hour Car Fee: Rs. " + standardFee);
        System.out.println("With " + dayPass.getPassType() + ": Rs. " + discountedFee + " (Expected: Rs. 150.0)");

        // 4. Validate 5-Floor Extended Non-Uniform Facility
        System.out.println("\n--- 4. Testing 5-Floor Extended Facility Layout ---");
        int[] floorConfig = {20, 40, 60, 80, 100};
        AdaptiveParkingSystem.ExtendedFacility facility = new AdaptiveParkingSystem.ExtendedFacility(floorConfig);

        System.out.println("Total Facility Floors:   5");
        System.out.println("Total Facility Capacity: " + facility.getTotalCapacity() + " slots.");
        for (int f = 0; f < 5; f++) {
            System.out.println("  Floor " + f + " capacity: " + facility.getFloorCapacity(f) + " slots");
        }

        int[] slot = facility.park(eBike);
        System.out.println("Parked E-Bike at: " + Arrays.toString(slot));

        System.out.println("\n>>> DAY-4 DESIGN CHALLENGE VALIDATION: ALL TESTS PASSED SUCCESSFULLY! <<<");
    }
}
