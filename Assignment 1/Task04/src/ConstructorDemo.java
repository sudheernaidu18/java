/**
 * Task 04: Design Classes, Objects and Constructors
 * Demonstrates Parameterized Constructors, Constructor Chaining, Copy Constructor,
 * and Memory Representation.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class ConstructorDemo {

    static class ParkingTicket {
        private final String ticketId;
        private final String vehicleRegNo;
        private final int floor;
        private final int slot;
        private final long entryTimeMillis;
        private boolean active;

        // Overloaded constructor using this() chaining
        public ParkingTicket(String ticketId, String vehicleRegNo, int floor, int slot) {
            this(ticketId, vehicleRegNo, floor, slot, System.currentTimeMillis());
        }

        // Master parameterized constructor
        public ParkingTicket(String ticketId, String vehicleRegNo, int floor, int slot, long entryTimeMillis) {
            if (ticketId == null || ticketId.isBlank()) throw new IllegalArgumentException("Ticket ID cannot be empty");
            if (vehicleRegNo == null || vehicleRegNo.isBlank()) throw new IllegalArgumentException("RegNo cannot be empty");
            if (floor < 0 || slot < 0) throw new IllegalArgumentException("Invalid slot coordinates");

            this.ticketId = ticketId;
            this.vehicleRegNo = vehicleRegNo;
            this.floor = floor;
            this.slot = slot;
            this.entryTimeMillis = entryTimeMillis;
            this.active = true;
        }

        // Copy Constructor for duplicate issuance
        public ParkingTicket(ParkingTicket other) {
            this.ticketId = other.ticketId + "-DUP";
            this.vehicleRegNo = other.vehicleRegNo;
            this.floor = other.floor;
            this.slot = other.slot;
            this.entryTimeMillis = other.entryTimeMillis;
            this.active = other.active;
        }

        public String getTicketId() { return ticketId; }
        public String getVehicleRegNo() { return vehicleRegNo; }
        public int getFloor() { return floor; }
        public int getSlot() { return slot; }
        public long getEntryTimeMillis() { return entryTimeMillis; }
        public boolean isActive() { return active; }
        public void deactivate() { this.active = false; }

        @Override
        public String toString() {
            return String.format("Ticket[ID=%s, RegNo=%s, Slot=(F%d, S%d), Entry=%d, Active=%b]",
                    ticketId, vehicleRegNo, floor, slot, entryTimeMillis, active);
        }
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("TASK 04: CLASSES, OBJECTS & CONSTRUCTORS VALIDATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // 1. Instantiation via Constructor Chaining
        System.out.println("--- 1. Instantiation via Constructor Chaining ---");
        ParkingTicket t1 = new ParkingTicket("TKT-1001", "TN01AB1111", 0, 1);
        System.out.println("Original Ticket Created: " + t1);

        // 2. Instantiation via Copy Constructor
        System.out.println("\n--- 2. Instantiation via Copy Constructor (Duplicate Ticket) ---");
        ParkingTicket t1Duplicate = new ParkingTicket(t1);
        System.out.println("Duplicate Ticket Created: " + t1Duplicate);

        // Verify independent state
        t1.deactivate();
        System.out.println("\nAfter deactivating original ticket:");
        System.out.println("Original Ticket Active:  " + t1.isActive());
        System.out.println("Duplicate Ticket Active: " + t1Duplicate.isActive());
        System.out.println("Verdict: Copy constructor produced independent snapshot preventing state corruption.");

        // 3. Object Creation Invariant Verification
        System.out.println("\n--- 3. Defensive Invariant Verification ---");
        try {
            new ParkingTicket("", "TN01AB1111", 0, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught Expected Invariant Violation: " + e.getMessage());
        }
    }
}
