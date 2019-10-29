package routes;

/**
 * Represents a ferry route in the transportation network.
 */
public class FerryRoute extends Route {

    /**
     * Creates a new FerryRoute with the given name and number.
     *
     * <p>Should meet the specification of {@link Route#Route(String, int)}
     *
     * @param name The name of the route.
     * @param routeNumber The route number of the route.
     */
    public FerryRoute(String name, int routeNumber) {

        super(name, routeNumber);
    }

    /**
     * {@inheritDoc}
     *
     * @return "ferry"
     */
    @Override
    public String getType() {
        return "ferry";
    }
}
