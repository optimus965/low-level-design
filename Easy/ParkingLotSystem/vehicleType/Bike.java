package Easy.ParkingLotSystem.vehicleType;

public class Bike extends  Vehicle {

    // when we extend a class first
    // in Parent class it has constructor so we should call it
    // the parent constructor is always called first
    public Bike(String license) {
        super(license,vehicleType.BIKE);
    }

}
