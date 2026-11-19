/**
 * Task 09: Introduce Abstraction
 * Validates abstract class vs interface capabilities, default/static methods,
 * and multiple interface implementation.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class AbstractionDemo {

    // Orthogonal Capabilities as Interfaces
    interface Chargeable {
        double chargingFee(int kWh);

        // Default method: Fast-charge premium calculation
        default double fastChargingPremium(int kWh) {
            return kWh * 3.50;
        }

        // Static utility method: Sanity check for battery intake
        static boolean isValidChargingSession(int kWh) {
            return kWh > 0 && kWh <= 200;
        }
    }

    interface Monitored {
        String getSecurityTag();
    }

    // Core Domain Abstraction as Abstract Class
    abstract static class Vehicle {
        private final String regNo;

        protected Vehicle(String regNo) {
            if (regNo == null || regNo.isBlank()) {
                throw new IllegalArgumentException("Registration number required");
            }
            this.regNo = regNo;
        }

        public final String getRegNo() { return regNo; }

        protected abstract double ratePerHour();

        // Concrete template method
        public final double calculateFee(int minutes) {
            int hours = (minutes + 59) / 60;
            return hours * ratePerHour();
        }
    }

    static class Car extends Vehicle {
        public Car(String regNo) { super(regNo); }
        @Override protected double ratePerHour() { return 30.0; }
    }

    // Demonstrates Multiple Behaviors via Interfaces
    static class ConnectedElectricCar extends Car implements Chargeable, Monitored {
        private final String rfidTag;

        public ConnectedElectricCar(String regNo, String rfidTag) {
            super(regNo);
            this.rfidTag = rfidTag;
        }

        @Override
        public double chargingFee(int kWh) {
            return kWh * 12.0;
        }

        @Override
        public String getSecurityTag() {
            return "RFID-" + rfidTag;
        }
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("TASK 09: ABSTRACTION & MULTIPLE CAPABILITY VALIDATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        ConnectedElectricCar ev = new ConnectedElectricCar("TN03-EV-9999", "TAG-7788");

        System.out.println("Vehicle Plate:       " + ev.getRegNo());
        System.out.println("Security Tag:        " + ev.getSecurityTag());
        System.out.println("Parking Fee (90m):   Rs. " + ev.calculateFee(90));
        System.out.println("Charging Fee (15kWh):Rs. " + ev.chargingFee(15));
        System.out.println("Fast Charge Premium: Rs. " + ev.fastChargingPremium(15) + " (Inherited from default method)");
        System.out.println("Is 15 kWh valid?     " + Chargeable.isValidChargingSession(15) + " (Validated via static interface method)");

        System.out.println("\nConclusion: Abstract class enforces single-inheritance identity while interfaces enable flexible capabilities.");
    }
}
