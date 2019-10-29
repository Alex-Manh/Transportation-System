package routes;

/**
 * Represents a bus route in the transportation network.
 */
public class BusRoute extends Route {

    /**
     * Creates a new BusRoute with the given name and number.
     *
     * <p>Should meet the specification of {@link Route#Route(String, int)}
     *
     * @param name The name of the route.
     * @param routeNumber The route number of the route.
     */
    public BusRoute(String name, int routeNumber) {
        super(name, routeNumber);
    }

    /**
     * {@inheritDoc}
     *
     * @return "bus"
     */
    @Override
    public String getType() {
        return "bus";
    }
}
