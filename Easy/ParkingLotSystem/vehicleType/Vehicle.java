package Easy.ParkingLotSystem.vehicleType;

public abstract  class Vehicle {
    protected String licensePlate;
    protected vehicleType type;

    public Vehicle(String licensePlate,vehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }
    public String getLicensePlate() {
        return licensePlate;
    }

    public vehicleType getType() {
        return type;
    }
}
