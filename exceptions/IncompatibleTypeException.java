package exceptions;

import routes.Route;
import vehicles.PublicTransport;

/**
 * Exception thrown when a subclass of {@link PublicTransport} is
 * added to a {@link Route} for a different type of transport.
 *
 * i.e. Train added to BusRoute
 */
public class IncompatibleTypeException extends TransportException {
}
