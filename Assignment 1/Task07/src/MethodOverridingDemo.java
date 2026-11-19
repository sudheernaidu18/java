/**
 * Task 07: Override and Specialise Behaviour
 * Demonstrates method overriding, @Override, equals/hashCode contracts, final keywords,
 * and compares with procedural switch.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
import java.util.HashSet;
import java.util.Set;

public class MethodOverridingDemo {

    abstract static class Vehicle {
        private final String regNo;

        protected Vehicle(String regNo) {
            this.regNo = regNo;
        }

        public final String getRegNo() { return regNo; }

        protected abstract double ratePerHour();

        // Final template method enforcing the billing ceiling rule
        public final double calculateFee(int minutes) {
            int hours = (minutes + 59) / 60;
            return hours * ratePerHour();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof Vehicle v) {
                return regNo.equals(v.regNo);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return regNo.hashCode();
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " [" + regNo + "]";
        }
    }

    static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo) { super(regNo); }
        @Override protected double ratePerHour() { return 10.0; }
    }

    static class Car extends Vehicle {
        public Car(String regNo) { super(regNo); }
        @Override protected double ratePerHour() { return 30.0; }
    }

    static class ElectricCar extends Car {
        public ElectricCar(String regNo) { super(regNo); }
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("TASK 07: METHOD OVERRIDING & OBJECT CONTRACT VALIDATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // 1. Method Overriding Execution
        Vehicle bike = new TwoWheeler("TN01-BK-1111");
        Vehicle car = new Car("TN02-CR-2222");
        System.out.println(bike + " fee for 130 mins: Rs. " + bike.calculateFee(130));
        System.out.println(car + " fee for 130 mins: Rs. " + car.calculateFee(130));

        // 2. equals() and hashCode() Contract Testing
        System.out.println("\n--- Testing equals() and hashCode() Contract ---");
        Vehicle car1 = new Car("TN02-CR-2222");
        Vehicle electricCarSameReg = new ElectricCar("TN02-CR-2222");
        Vehicle carDifferentReg = new Car("TN09-XX-9999");

        System.out.println("car1.equals(electricCarSameReg): " + car1.equals(electricCarSameReg) + " (Expected: true)");
        System.out.println("car1.equals(carDifferentReg):    " + car1.equals(carDifferentReg) + " (Expected: false)");
        System.out.println("car1.hashCode() == electricCarSameReg.hashCode(): " +
                (car1.hashCode() == electricCarSameReg.hashCode()) + " (Expected: true)");

        // 3. HashSet Uniqueness Verification
        Set<Vehicle> parkingRegistry = new HashSet<>();
        parkingRegistry.add(car1);
        parkingRegistry.add(electricCarSameReg); // duplicate registration number!
        System.out.println("HashSet size after adding duplicate regNo vehicle: " + parkingRegistry.size() + " (Expected: 1)");
        System.out.println("Successfully prevented duplicate physical vehicle registration in system.");
    }
}
