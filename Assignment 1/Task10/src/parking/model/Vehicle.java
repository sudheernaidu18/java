package parking.model;

public abstract class Vehicle {
    private final String regNo;

    protected Vehicle(String regNo) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle registration number cannot be null or blank");
        }
        this.regNo = regNo.trim().toUpperCase();
    }

    public final String getRegNo() {
        return regNo;
    }

    protected abstract double ratePerHour();

    public final double calculateFee(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes cannot be negative: " + minutes);
        }
        int hours = (minutes + 59) / 60;
        return hours * ratePerHour();
    }
}
