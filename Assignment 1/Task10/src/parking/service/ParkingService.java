package parking.service;

import parking.model.Vehicle;

public class ParkingService {
    private final String[][] facilitySlots;

    public ParkingService(int[] floorCapacities) {
        facilitySlots = new String[floorCapacities.length][];
        for (int f = 0; f < floorCapacities.length; f++) {
            facilitySlots[f] = new String[floorCapacities[f]];
        }
    }

    public int[] park(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Cannot park null vehicle");
        }
        for (int f = 0; f < facilitySlots.length; f++) {
            for (int s = 0; s < facilitySlots[f].length; s++) {
                if (facilitySlots[f][s] == null) {
                    facilitySlots[f][s] = vehicle.getRegNo();
                    return new int[]{f, s};
                }
            }
        }
        return null;
    }

    public boolean vacate(int floor, int slot) {
        if (floor < 0 || floor >= facilitySlots.length) return false;
        if (slot < 0 || slot >= facilitySlots[floor].length) return false;
        if (facilitySlots[floor][slot] == null) return false;
        facilitySlots[floor][slot] = null;
        return true;
    }
}
