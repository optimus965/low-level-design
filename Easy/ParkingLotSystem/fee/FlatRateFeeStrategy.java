package Easy.ParkingLotSystem.fee;

import Easy.ParkingLotSystem.Ticket;

public class FlatRateFeeStrategy implements FeeStrategy {
    private static final double RATE_PER_HOUR = 10.0;
    @Override
    public double calculate(Ticket ticket) {
        long duration = ticket.getExitTimeStamp() - ticket.getEntryTimeStamp();
        long hours = (duration/(1000*60*60)) + 1;
        return hours*RATE_PER_HOUR;
    }
}
