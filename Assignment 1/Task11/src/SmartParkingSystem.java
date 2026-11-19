/**
 * Task 11: Implement, Test and Defend the Design
 * Complete Object-Oriented Smart-Parking System Implementation.
 *
 * Student Name: Korasika Sudheer
 * Register Number: 192324313
 */
import java.util.*;

public class SmartParkingSystem {

    public interface Chargeable {
        double chargingFee(int kWh);
    }

    public static abstract class Vehicle {
        private final String regNo;

        protected Vehicle(String regNo) {
            if (regNo == null || regNo.trim().isEmpty()) {
                throw new IllegalArgumentException("Registration number cannot be null or empty");
            }
            this.regNo = regNo.trim().toUpperCase();
        }

        public final String getRegNo() { return regNo; }
        protected abstract double ratePerHour();

        public double calculateFee(int minutes) {
            if (minutes <= 0) return 0.0;
            int hours = (minutes + 59) / 60;
            return hours * ratePerHour();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Vehicle && regNo.equals(((Vehicle) o).regNo);
        }

        @Override
        public int hashCode() { return regNo.hashCode(); }
    }

    public static class TwoWheeler extends Vehicle {
        public TwoWheeler(String regNo) { super(regNo); }
        @Override protected double ratePerHour() { return 10.0; }
    }

    public static class Car extends Vehicle {
        public Car(String regNo) { super(regNo); }
        @Override protected double ratePerHour() { return 30.0; }
    }

    public static class ElectricCar extends Car implements Chargeable {
        public ElectricCar(String regNo) { super(regNo); }
        @Override public double chargingFee(int kWh) { return kWh * 12.0; }
    }

    public static class ParkingTicket {
        private final String ticketId;
        private final Vehicle vehicle;
        private final int floor;
        private final int slot;
        private final boolean hasMonthlyPass;
        private final int energyConsumedKWh;

        public ParkingTicket(String ticketId, Vehicle vehicle, int floor, int slot, boolean hasMonthlyPass, int energyConsumedKWh) {
            this.ticketId = ticketId;
            this.vehicle = vehicle;
            this.floor = floor;
            this.slot = slot;
            this.hasMonthlyPass = hasMonthlyPass;
            this.energyConsumedKWh = energyConsumedKWh;
        }

        public String getTicketId() { return ticketId; }
        public Vehicle getVehicle() { return vehicle; }
        public int getFloor() { return floor; }
        public int getSlot() { return slot; }
        public boolean hasMonthlyPass() { return hasMonthlyPass; }
        public int getEnergyConsumedKWh() { return energyConsumedKWh; }
    }

    public static class CampusFacility {
        private final Vehicle[][] slots;
        private final Map<String, ParkingTicket> activeTicketsByRegNo = new HashMap<>();
        private int ticketSeq = 1000;

        public CampusFacility(int[] slotsPerFloor) {
            slots = new Vehicle[slotsPerFloor.length][];
            for (int f = 0; f < slotsPerFloor.length; f++) {
                slots[f] = new Vehicle[slotsPerFloor[f]];
            }
        }

        public ParkingTicket checkIn(Vehicle vehicle, boolean hasMonthlyPass, int estimatedKWh) {
            if (vehicle == null) throw new IllegalArgumentException("Cannot park null vehicle");
            if (activeTicketsByRegNo.containsKey(vehicle.getRegNo())) {
                System.out.println("  [ERROR] Vehicle " + vehicle.getRegNo() + " is ALREADY parked in facility!");
                return null;
            }

            for (int f = 0; f < slots.length; f++) {
                for (int s = 0; s < slots[f].length; s++) {
                    if (slots[f][s] == null) {
                        slots[f][s] = vehicle;
                        String tktId = "TKT-" + (++ticketSeq);
                        ParkingTicket ticket = new ParkingTicket(tktId, vehicle, f, s, hasMonthlyPass, estimatedKWh);
                        activeTicketsByRegNo.put(vehicle.getRegNo(), ticket);
                        return ticket;
                    }
                }
            }
            return null; // Facility full
        }

        public double checkOut(String regNo, int durationMinutes) {
            ParkingTicket ticket = activeTicketsByRegNo.get(regNo);
            if (ticket == null) {
                System.out.println("  [ERROR] No active parking ticket found for reg: " + regNo);
                return -1.0;
            }

            Vehicle v = ticket.getVehicle();
            double baseFee = ticket.hasMonthlyPass() ? 0.0 : v.calculateFee(durationMinutes);
            double chargingFee = 0.0;
            if (v instanceof Chargeable c && ticket.getEnergyConsumedKWh() > 0) {
                chargingFee = c.chargingFee(ticket.getEnergyConsumedKWh());
            }

            // Free the slot
            slots[ticket.getFloor()][ticket.getSlot()] = null;
            activeTicketsByRegNo.remove(regNo);

            return baseFee + chargingFee;
        }

        public boolean releaseDirect(int floor, int slot) {
            if (floor < 0 || floor >= slots.length) return false;
            if (slot < 0 || slot >= slots[floor].length) return false;
            if (slots[floor][slot] == null) return false;
            slots[floor][slot] = null;
            return true;
        }
    }
}
