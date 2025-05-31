package Easy.ParkingLotSystem;

import Easy.ParkingLotSystem.fee.VehicleBasedFeeStrategy;
import Easy.ParkingLotSystem.vehicleType.Bike;
import Easy.ParkingLotSystem.vehicleType.Car;
import Easy.ParkingLotSystem.vehicleType.Vehicle;
import Easy.ParkingLotSystem.vehicleType.vehicleType;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotDemo {
    public static void main(String[] args) {
        ParkingLotSystem parkingLotSystem = getParkingLotSystem();

        // create Vehicles
        Vehicle car1 = new Car("ABC123");
        Vehicle car2 = new Car("XYZ789");
        Vehicle bike1 = new Bike("M1234");

        System.out.println("Available spots for car:");
        for(ParkingFloor floor: parkingLotSystem.getParkingFloor()) {
            System.out.println("Floor: " + floor.getFloorNumber() + " " + floor.getAvailableSpot(vehicleType.CAR));
        }
        List<String> parkingTickets = new ArrayList<>();
        try {
            Ticket ticket1 = parkingLotSystem.parkVehicle(car1);
            System.out.println("Car 1 Parked: " + ticket1.getTicketId());
            parkingTickets.add(ticket1.getTicketId());

            Ticket ticket2 = parkingLotSystem.parkVehicle(car2);
            System.out.println("Car 2 Parked: " + ticket2.getTicketId());
            parkingTickets.add(ticket2.getTicketId());

            Ticket ticket3 = parkingLotSystem.parkVehicle(bike1);
            System.out.println("Bike 1 parked: " + ticket3.getTicketId());
            parkingTickets.add(ticket3.getTicketId());

            Vehicle car3 = new Car("DL0432");
            parkingLotSystem.parkVehicle(car3);

        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        try {
            double fee = parkingLotSystem.unparkVehicle(parkingTickets.getFirst());
            System.out.println("Ticket: " + parkingTickets.getFirst() + ", fee: " + fee);
            fee = parkingLotSystem.unparkVehicle("1");
        }
        catch (Exception e) {
            System.out.println("Exception while unparking: " + e.getMessage());
        }



    }

    private static ParkingLotSystem getParkingLotSystem() {
        ParkingLotSystem parkingLotSystem = ParkingLotSystem.getInstance();
        List<ParkingSpot> parkingSpotsFloor1 = List.of(
                new ParkingSpot(101, vehicleType.CAR),
                new ParkingSpot(102,vehicleType.CAR),
                new ParkingSpot(103,vehicleType.BIKE)
                );
        List<ParkingSpot> parkingSpotsFloor2 = List.of(
                new ParkingSpot(201,vehicleType.BIKE),
                new ParkingSpot(202,vehicleType.TRUCK)
        );

        ParkingFloor floor1 = new ParkingFloor(1,parkingSpotsFloor1);
        ParkingFloor floor2 = new ParkingFloor(2,parkingSpotsFloor2);

        parkingLotSystem.addFloor(floor1);
        parkingLotSystem.addFloor(floor2);
        parkingLotSystem.setFeeStrategy(new VehicleBasedFeeStrategy());
        return parkingLotSystem;
    }
}
