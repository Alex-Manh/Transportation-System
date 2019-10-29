package routes;

/**
 * Represents a train route in the transportation network.
 */
public class TrainRoute extends Route {

    /**
     * Creates a new TrainRoute with the given name and number.
     *
     * <p>Should meet the specification of {@link Route#Route(String, int)}
     *
     * @param name The name of the route.
     * @param routeNumber The route number of the route.
     */
    public TrainRoute(String name, int routeNumber) {
        super(name, routeNumber);
    }

    /**
     * {@inheritDoc}
     *
     * @return "train"
     */
    @Override
    public String getType() {
        return "train";
    }
}
