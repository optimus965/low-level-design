package Easy.ParkingLotSystem.fee;

import Easy.ParkingLotSystem.Ticket;

public interface FeeStrategy {
    public double calculate(Ticket ticket);
}
