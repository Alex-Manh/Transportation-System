package vehicles;

import routes.Route;

/**
 * Represents a ferry in the transportation network.
 */
public class Ferry extends PublicTransport {
    // the type of the ferry
    private String ferryType;

    /**
     * Creates a new Ferry object with the given id, capacity, route, and type.
     *
     * <p>Should meet the specification of
     * {@link PublicTransport#PublicTransport(int, int, Route)},
     * as well as extending it to include the following:
     *
     * <p>If the given ferryType is null or empty, the string "CityCat" should
     * be stored instead. If the ferry type contains any newline characters
     * ('\n') or carriage returns ('\r'), they should be removed from the string
     * before it is stored.
     *
     * @param id The identifying number of the ferry.
     * @param capacity The maximum capacity of the ferry.
     * @param route The route this ferry is on.
     * @param ferryType The type of the ferry (e.g. CityCat).
     */
    public Ferry(int id, int capacity, Route route, String ferryType) {
        super(id, capacity, route);
        this.ferryType = ferryType == null || ferryType.isEmpty() ? "CityCat" :
                ferryType.replace("\n", "")
                        .replace("\r", "");
    }

    /**
     * Returns the type of this ferry.
     *
     * @return The type of the ferry.
     */
    public String getFerryType() {
        return ferryType;
    }

    /**
     * Encodes this ferry as a string in the same format as specified in
     * {@link PublicTransport#encode()}, but with an additional component at
     * the end, namely the ferry type. The encoded format should be as follows:
     *
     * <p>'{type},{id},{capacity},{route},{ferryType}'
     *
     * <p>where {ferryType} is replaced by the type of this ferry, and all of
     * the other components are the same as defined in the method being
     * overridden.
     * For example:
     *
     * <p>ferry,2,50,2,CityCat
     *
     * @return This ferry encoded as a string.
     */
    @Override
    public String encode() {
        return super.encode() + "," + ferryType;
    }
}
