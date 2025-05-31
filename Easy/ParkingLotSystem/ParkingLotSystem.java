package Easy.ParkingLotSystem;

import Easy.ParkingLotSystem.vehicleType.*;
import Easy.ParkingLotSystem.fee.FeeStrategy;
import Easy.ParkingLotSystem.fee.FlatRateFeeStrategy;
import Easy.ParkingLotSystem.vehicleType.Vehicle;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLotSystem {
    // singleTon so that every one will be using only one thing
    private static ParkingLotSystem instance;

    // it will be declared only once
    private final List<ParkingFloor> floors;

    //it is better approach than using HashMap
    // using hashMap also, we can achieve this but it locks the whole map
    // only one thread will go
    // in ConcurrentMap it makes use of buckets so that, only that particular bucket  will be locked out
    private final Map<String,Ticket> activeTickets = new ConcurrentHashMap<>();

    //it will be decided at runtime
    private FeeStrategy feeStrategy;

    // it is private Constructor so that no one able to create a instance of it
    private ParkingLotSystem() {
        floors = new ArrayList<>();

        // we are giving default one as it is not final we can assign after some time
        feeStrategy = new FlatRateFeeStrategy();
    }
    public static synchronized ParkingLotSystem getInstance() {
        if(instance == null) {
            instance = new ParkingLotSystem();
        }
        return instance;
    }
    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }
    public void setFeeStrategy(FeeStrategy feeStrategy) {
        this.feeStrategy = feeStrategy;
    }

    public synchronized Ticket parkVehicle(Vehicle vehicle) throws Exception {
        for(ParkingFloor floor:floors) {
            Optional<ParkingSpot> spotOpt = floor.getAvailableSpot(vehicle.getType());
            if(spotOpt.isPresent()) {
                ParkingSpot spot = spotOpt.get();
                if(spot.park(vehicle)) {
                    String ticketId = UUID.randomUUID().toString();
                    Ticket ticket = new Ticket(ticketId,vehicle,spot);
                    activeTickets.put(ticketId,ticket);
                    return ticket;
                }
            }
        }
      throw new Exception("No available spot for " + vehicle.getType());
    }

    public synchronized double unparkVehicle(String ticketId) throws Exception {
        Ticket ticket = activeTickets.remove(ticketId);
        if(ticket == null) {
            throw new Exception("Invalid Ticket");
        }
        ParkingSpot spot= ticket.getSpot();
        spot.unpark();
        ticket.setExitTimeStamp();
        return feeStrategy.calculate(ticket);
    }

    public List<ParkingFloor> getParkingFloor() {
        return floors;
    }
}
