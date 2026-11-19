/**
 * Task 05: Apply static Members and the Object Life Cycle
 * Validates static vs instance counters across North/South campus gates,
 * demonstrates Java pass-by-value semantics, and traces GC eligibility.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class StaticLifeCycleDemo {

    // Global static counter approach
    static class GlobalTicketSystem {
        private static int globalSequence = 5000;

        public static String nextTicketNumber() {
            return "GLOBAL-" + (++globalSequence);
        }
    }

    // Facility instance counter approach (North Gate / South Gate)
    static class FacilityGate {
        private final String gateName;
        private final String codePrefix;
        private int gateLocalCounter;

        public FacilityGate(String gateName, String codePrefix) {
            this.gateName = gateName;
            this.codePrefix = codePrefix;
            this.gateLocalCounter = 1000;
        }

        public String issueTicket(String vehicleRegNo) {
            gateLocalCounter++;
            return String.format("[%s] Issued Ticket #%s-%d for %s",
                    gateName, codePrefix, gateLocalCounter, vehicleRegNo);
        }

        public int getLocalCount() { return gateLocalCounter - 1000; }
    }

    // Passing semantics demonstration
    static void modifyDurationPrimitive(int minutes) {
        minutes = minutes + 60; // modifies only local stack frame copy
    }

    static class TicketWrapper {
        String status = "ACTIVE";
    }

    static void updateTicketStatus(TicketWrapper ticket) {
        ticket.status = "PAID_AND_RELEASED"; // Mutates heap object via copied reference
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("TASK 05: STATIC MEMBERS & OBJECT LIFE CYCLE VALIDATION");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        // 1. Global Static Counter
        System.out.println("--- 1. Shared Global Static Counter ---");
        System.out.println(GlobalTicketSystem.nextTicketNumber());
        System.out.println(GlobalTicketSystem.nextTicketNumber());

        // 2. Multi-Facility Instance Counters (North Gate vs South Gate)
        System.out.println("\n--- 2. Decentralized Facility Gate Counters ---");
        FacilityGate northGate = new FacilityGate("North Gate", "NG");
        FacilityGate southGate = new FacilityGate("South Gate", "SG");

        System.out.println(northGate.issueTicket("TN01AA1111"));
        System.out.println(southGate.issueTicket("TN02BB2222"));
        System.out.println(northGate.issueTicket("TN01AA3333"));
        System.out.println(southGate.issueTicket("TN02BB4444"));

        System.out.println("North Gate Total Issues: " + northGate.getLocalCount());
        System.out.println("South Gate Total Issues: " + southGate.getLocalCount());

        // 3. Strict Pass-By-Value Demonstration
        System.out.println("\n--- 3. Java Pass-By-Value Semantics Verification ---");
        int duration = 60;
        modifyDurationPrimitive(duration);
        System.out.println("Primitive 'duration' after method call: " + duration + " (Unchanged - Pass-by-value confirmed)");

        TicketWrapper wrapper = new TicketWrapper();
        System.out.println("Ticket status before: " + wrapper.status);
        updateTicketStatus(wrapper);
        System.out.println("Ticket status after:  " + wrapper.status + " (Heap mutated via copied pointer value)");

        // 4. Object Dereferencing & GC Eligibility
        System.out.println("\n--- 4. Object Life Cycle & GC Eligibility ---");
        wrapper = null; // Severing heap reference: wrapper object is now unreferenced and eligible for GC
        System.out.println("Reference set to null. Object is now an unreachable garbage candidate for JVM G1/ZGC.");
    }
}
