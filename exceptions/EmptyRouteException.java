package exceptions;

import routes.Route;
import vehicles.PublicTransport;

/**
 * Exception thrown when a {@link PublicTransport} is added to a
 * {@link Route} with no {@link stops.Stop}s.
 */
public class EmptyRouteException extends TransportException {
}
