package passengers;

import stops.Stop;

/**
 * A base passenger in the transport network.
 */
public class Passenger {
    // name of the passenger
    private String name;
    // intended destination of the passenger
    private Stop destination;

    /**
     * Construct a new base passenger with the given name, and without a
     * destination.
     *
     * <p>If the given name is null, an empty string should be stored instead.
     *
     * <p>If the given name contains any newline characters ('\n') or carriage
     * returns ('\r'), they should be removed from the string before it is
     * stored.
     *
     * @param name The name of the passenger.
     */
    public Passenger(String name) {
        this.name = name == null ? "" : name.replace("\n", "")
                .replace("\r", "");
        this.destination = null;
    }

    /**
     * Construct a new base passenger with the given name and destination.
     *
     * <p> Should meet the specification of {@link Passenger#Passenger(String)},
     * as well as storing the given destination stop.
     *
     * @param name The name of the passenger.
     * @param destination The destination of the passenger.
     */
    public Passenger(String name, Stop destination) {
        this(name);
        this.destination = destination;
    }

    /**
     * Returns the name of the passenger.
     *
     * @return The name of the passenger.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the destination of the passenger.
     *
     * <p>A value of null for the given stop simply indicates that the passenger
     * has no destination.
     *
     * @param destination The intended destination of the passenger, or null if
     *                    the passenger has no destination.
     */
    public void setDestination(Stop destination) {

        this.destination = destination;
    }

    /**
     * Gets the destination of the passenger.
     *
     * @return The intended destination of the passenger, or null if the
     * passenger has no destination.
     */
    public Stop getDestination() {
        return destination;
    }

    /**
     * Creates a string representation of the passenger in the format:
     *
     * <p>'Passenger named {name}'
     *
     * <p>without surrounding quotes and with {name} replaced by the name of the
     * passenger instance. For example:
     *
     * <p>Passenger named Agatha
     *
     * <p>If the passenger's name is empty, the method should instead return the
     * following:
     *
     * <p>Anonymous passenger
     *
     * @return A string representation of the passenger.
     */
    @Override
    public String toString() {

        return name.isEmpty() ? "Anonymous passenger" : "Passenger named "
                + name;
    }
}
