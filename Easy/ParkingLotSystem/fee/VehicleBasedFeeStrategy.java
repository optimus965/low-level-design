package Easy.ParkingLotSystem.fee;

import Easy.ParkingLotSystem.Ticket;
import Easy.ParkingLotSystem.vehicleType.*;

import java.util.Map;

public class VehicleBasedFeeStrategy implements  FeeStrategy {

    private final Map<vehicleType,Double> hourlyRates = Map.of(
            vehicleType.CAR,20.0,
            vehicleType.BIKE,10.0,
            vehicleType.TRUCK,30.0);
    @Override
    public double calculate(Ticket ticket) {
       long duration = ticket.getExitTimeStamp() -ticket.getEntryTimeStamp();
       long hours = (duration/(1000*60*60)) + 1;
       return hours*hourlyRates.get(ticket.getVehicle().getType());
    }
}
