package vehicles;

import routes.Route;

/**
 * Represents a train in the transportation network.
 */
public class Train extends PublicTransport {
    // the number of carriages the train has
    private int carriageCount;

    /**
     * Creates a new Train object with the given id, capacity, route, and
     * carriage count.
     *
     * <p>Should meet the specification of
     * {@link PublicTransport#PublicTransport(int, int, Route)},
     * as well as extending it to include the following:
     *
     * <p>If the given carriage count is less than or equal to zero, then 1
     * should be stored instead.
     *
     * @param id The identifying number of the train.
     * @param capacity The maximum capacity of the train.
     * @param route The route this train is on.
     * @param carriageCount The number of carriages this train has.
     */
    public Train(int id, int capacity, Route route, int carriageCount) {
        super(id, capacity, route);
        this.carriageCount = Math.max(carriageCount, 1);
    }

    /**
     * Returns the number of carriages this train has.
     *
     * @return The number of carriages the train has.
     */
    public int getCarriageCount() {
        return carriageCount;
    }

    /**
     * Encodes this train as a string in the same format as specified in
     * {@link PublicTransport#encode()}, but with an additional component at
     * the end, namely the carriage count. The encoded format should be as
     * follows:
     *
     * <p>'{type},{id},{capacity},{route},{carriages}'
     *
     * <p>where {carriages} is replaced by the carriage count of this train,
     * and all of the other components are the same as defined in the method
     * being overridden.
     * For example:
     *
     * <p>train,3,100,3,5
     *
     * @return This train encoded as a string.
     */
    @Override
    public String encode() {
        return super.encode() + "," + carriageCount;
    }
}
