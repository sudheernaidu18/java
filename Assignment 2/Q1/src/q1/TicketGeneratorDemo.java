package q1;

/**
 * Question 1: Railway e-Ticket Generation Demo
 * Compares String concatenation (+), StringBuilder, and StringBuffer.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
public class TicketGeneratorDemo {

    // Sample booking record
    public static class BookingRecord {
        public String pnr = "PNR-9824313";
        public String trainNo = "12626 - KERALA EXPRESS";
        public String fromStation = "NEW DELHI (NDLS)";
        public String toStation = "CHENNAI CENTRAL (MAS)";
        public String date = "2026-12-05";
        public String coach = "B2";
        public int berth = 37;
        public String berthType = "SIDE UPPER";
        public String passengerName = "Korasika Sudheer";
        public int age = 20;
        public String quota = "GENERAL";
        public double baseFare = 1850.00;
        public double reservationFee = 40.00;
        public double superfastCharge = 45.00;
        public double gst = 96.75;
        public double totalFare = 2031.75;
    }

    /**
     * Approach 1: Naive String Concatenation (+) inside a loop.
     * Each '+' creates a new String object on the heap and copies previous characters.
     */
    public static String generateUsingString(BookingRecord b) {
        String ticket = "";
        ticket += "========================================================\n";
        ticket += "           INDIAN RAILWAYS E-TICKETING SERVICE          \n";
        ticket += "========================================================\n";
        ticket += "PNR: " + b.pnr + "\n";
        ticket += "Train: " + b.trainNo + "\n";
        ticket += "Journey: " + b.fromStation + " -> " + b.toStation + "\n";
        ticket += "Date of Journey: " + b.date + "\n";
        ticket += "Passenger: " + b.passengerName + " (Age: " + b.age + ")\n";
        ticket += "Booking: Coach " + b.coach + ", Berth " + b.berth + " (" + b.berthType + ")\n";
        ticket += "Quota: " + b.quota + "\n";
        ticket += "Fare Details: Base=Rs." + b.baseFare + ", Reservation=Rs." + b.reservationFee + "\n";
        ticket += "Superfast=Rs." + b.superfastCharge + ", GST=Rs." + b.gst + "\n";
        ticket += "TOTAL AMOUNT PAID: Rs." + b.totalFare + "\n";
        ticket += "========================================================\n";
        return ticket;
    }

    /**
     * Approach 2: StringBuilder (Unsynchronized, Fast, Thread-Confined).
     * Modifies internal char buffer with amortized O(1) appends.
     */
    public static String generateUsingStringBuilder(BookingRecord b) {
        StringBuilder sb = new StringBuilder(512);
        sb.append("========================================================\n")
          .append("           INDIAN RAILWAYS E-TICKETING SERVICE          \n")
          .append("========================================================\n")
          .append("PNR: ").append(b.pnr).append("\n")
          .append("Train: ").append(b.trainNo).append("\n")
          .append("Journey: ").append(b.fromStation).append(" -> ").append(b.toStation).append("\n")
          .append("Date of Journey: ").append(b.date).append("\n")
          .append("Passenger: ").append(b.passengerName).append(" (Age: ").append(b.age).append(")\n")
          .append("Booking: Coach ").append(b.coach).append(", Berth ").append(b.berth).append(" (").append(b.berthType).append(")\n")
          .append("Quota: ").append(b.quota).append("\n")
          .append("Fare Details: Base=Rs.").append(b.baseFare).append(", Reservation=Rs.").append(b.reservationFee).append("\n")
          .append("Superfast=Rs.").append(b.superfastCharge).append(", GST=Rs.").append(b.gst).append("\n")
          .append("TOTAL AMOUNT PAID: Rs.").append(b.totalFare).append("\n")
          .append("========================================================\n");
        return sb.toString();
    }

    /**
     * Approach 3: StringBuffer (Thread-Safe, Synchronized methods).
     */
    public static String generateUsingStringBuffer(BookingRecord b) {
        StringBuffer sb = new StringBuffer(512);
        sb.append("========================================================\n")
          .append("           INDIAN RAILWAYS E-TICKETING SERVICE          \n")
          .append("========================================================\n")
          .append("PNR: ").append(b.pnr).append("\n")
          .append("Train: ").append(b.trainNo).append("\n")
          .append("Journey: ").append(b.fromStation).append(" -> ").append(b.toStation).append("\n")
          .append("Date of Journey: ").append(b.date).append("\n")
          .append("Passenger: ").append(b.passengerName).append(" (Age: ").append(b.age).append(")\n")
          .append("Booking: Coach ").append(b.coach).append(", Berth ").append(b.berth).append(" (").append(b.berthType).append(")\n")
          .append("Quota: ").append(b.quota).append("\n")
          .append("Fare Details: Base=Rs.").append(b.baseFare).append(", Reservation=Rs.").append(b.reservationFee).append("\n")
          .append("Superfast=Rs.").append(b.superfastCharge).append(", GST=Rs.").append(b.gst).append("\n")
          .append("TOTAL AMOUNT PAID: Rs.").append(b.totalFare).append("\n")
          .append("========================================================\n");
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("Q1: RAILWAY E-TICKET GENERATOR (STRING VS BUILDER VS BUFFER)");
        System.out.println("Student: Korasika Sudheer | Register Number: 192324313");
        System.out.println("===============================================================\n");

        BookingRecord record = new BookingRecord();
        String ticket = generateUsingStringBuilder(record);
        System.out.println("Generated Ticket Output Sample:\n" + ticket);

        // Verification of equivalence
        String s1 = generateUsingString(record);
        String s2 = generateUsingStringBuilder(record);
        String s3 = generateUsingStringBuffer(record);

        boolean match = s1.equals(s2) && s2.equals(s3);
        System.out.println("Semantic Equivalence Check (s1 == s2 == s3): " + match);
    }
}
