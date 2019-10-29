package vehicles;

import routes.Route;

/**
 * Represents a bus in the transportation network.
 */
public class Bus extends PublicTransport {
    // the rego of the bus
    private String registrationNumber;

    /**
     * Creates a new Bus object with the given id, capacity, route, and
     * registration number.
     *
     * <p>Should meet the specification of
     * {@link PublicTransport#PublicTransport(int, int, Route)},
     * as well as extending it to include the following:
     *
     * <p>If the given registration number is null, an empty string should be
     * stored instead. If the registration number contains any newline
     * characters ('\n') or carriage returns ('\r'), they should be removed
     * from the string before it is stored.
     *
     * @param id The identifying number of the bus.
     * @param capacity The maximum capacity of the bus.
     * @param route The route this bus is on.
     * @param registrationNumber The registration number of the bus.
     */
    public Bus(int id, int capacity, Route route, String registrationNumber) {
        super(id, capacity, route);
        this.registrationNumber = registrationNumber == null ? "" :
                registrationNumber
                        .replace("\n", "")
                        .replace("\r", "");
    }

    /**
     * Returns the registration number of this bus.
     *
     * @return The registration number of the bus.
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Encodes this bus as a string in the same format as specified in
     * {@link PublicTransport#encode()}, but with an additional component at
     * the end, namely the bus registration number. The encoded format should
     * be as follows:
     *
     * <p>'{type},{id},{capacity},{route},{registration}'
     *
     * <p>where {registration} is replaced by the registration number of this
     * bus, and all of the other components are the same as defined in the
     * method being overridden. For example:
     *
     * <p>bus,1,30,1,ABC123
     *
     * @return This bus encoded as a string.
     */
    @Override
    public String encode() {
        return super.encode() + "," + registrationNumber;
    }
}
