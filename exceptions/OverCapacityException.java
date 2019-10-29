package exceptions;

import passengers.Passenger;
import vehicles.PublicTransport;

/**
 * Exception thrown when a {@link Passenger} is added to a
 * {@link PublicTransport} which has reached capacity.
 *
 * (i.e. {@link PublicTransport#passengerCount()} &gt;=
 * {@link PublicTransport#getCapacity()})
 */
public class OverCapacityException extends TransportException {
}
