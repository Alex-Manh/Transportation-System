package network;

import exceptions.DuplicateStopException;
import exceptions.TransportFormatException;
import routes.Route;
import stops.Stop;
import utilities.Writeable;
import vehicles.PublicTransport;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents the transportation network, and manages all of the various
 * components therein.
 */
public class Network {
    // standardises newline characters
    private static final String NEWLINE = System.lineSeparator();

    // all the stops in the network
    private List<Stop> stops;

    // all the vehicles in the network
    private List<PublicTransport> vehicles;

    // all the routes in the network
    private List<Route> routes;

    /**
     * Creates a new empty Network with no stops, vehicles, or routes.
     */
    public Network() {
        this.stops = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    /**
     * Creates a new Network from information contained in the file indicated by
     * the given filename. The file should be in the following format:
     *
     * <p>{number_of_stops}<br>
     * {stop0:x0:y0}<br>
     * ...<br>
     * {stopN:xN:yN}<br>
     * {number_of_routes}<br>
     * {type0,name0,number0:stop0|stop1|...|stopM}<br>
     * ...<br>
     * {typeN,nameN,numberN:stop0|stop1|...|stopM}<br>
     * {number_of_vehicles}<br>
     * {type0,id0,capacity0,routeNumber,extra}<br>
     * ...<br>
     * {typeN,idN,capacityN,routeNumber,extra}<br>
     *
     * <p>where {number_of_stops}, {number_of_routes}, and {number_of_vehicles}
     * are the number of stops, routes, and vehicles (respectively) in the
     * network, and where {stop0,x0,y0} is the encode() representation of a
     * Stop, {type0,name0,number0:stop0|stop1|...|stopM} is the encode()
     * representation of a Route, and {typeN,idN,capacityN,routeNumber,extra}
     * is the encode() representation of a PublicTransport.
     *
     * <p>Whilst parsing, if spaces (i.e. ' ') are encountered before or after
     * integers, (i.e. {number_of_stops}, {number_of_routes}, or
     * {number_of_vehicles}), the spaces should simply be trimmed (for example,
     * using something like {@link String#trim()}).
     *
     * <p>For example:<br>
     * 4<br>
     * stop0:0:1<br>
     * stop1:-1:0<br>
     * stop2:4:2<br>
     * stop3:2:-8<br>
     * 2<br>
     * train,red,1:stop0|stop2|stop1<br>
     * bus,blue,2:stop1|stop3|stop0<br>
     * 3<br>
     * train,123,30,1,2<br>
     * train,42,60,1,3<br>
     * bus,412,20,2,ABC123<br>
     *
     * <p>The Network object created should have the stops, routes, and vehicles
     * contained in the given file.
     *
     * @param filename The name of the file to load the network from.
     * @throws IOException If any IO exceptions occur whilst trying to read from
     *         the file, or if the filename is null.
     * @throws TransportFormatException
     *         <ol>
     *             <li>If any of the lines representing stops, routes, or
     *             vehicles are incorrectly formatted according to their
     *             respective decode methods (i.e. if their decode method
     *             throws an exception).</li>
     *             <li>If any of the integers are incorrectly formatted
     *             (i.e. cannot be parsed).</li>
     *             <li>If the {number_of_stops} does not match the actual number
     *             of lines representing stops present. This also applies to
     *             {number_of_routes} and {number_of_vehicles}. An error should
     *             also be thrown if any of these integers are negative.</li>
     *             <li>If there are any extra lines present in the file (the
     *             file may end with a single newline character, but there may
     *             not be multiple blank lines at the end of the file).</li>
     *             <li>If any other formatting issues are encountered whilst
     *             parsing the file (sample valid and invalid network files will
     *             be provided to help identify some potential issues).</li>
     *         </ol>
     */
    public Network(String filename)
            throws IOException, TransportFormatException {
        this();
        if (filename == null) {
            throw new IOException();
        }

        // create a file reader
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> lines = new ArrayList<>();
        String line;

        // while the end of file has not been reached.
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        Iterator<String> elements = lines.iterator();

        try {
            // read the stopos
            stops = new ArrayList<>();
            int stopCount = Integer.parseInt(elements.next().trim());
            for (int i = 0; i < stopCount; i++) {
                String stop = elements.next();
                stops.add(Stop.decode(stop));
            }

            // read the routes
            routes = new ArrayList<>();
            int routeCount = Integer.parseInt(elements.next().trim());
            for (int i = 0; i < routeCount; i++) {
                String route = elements.next();
                routes.add(Route.decode(route, stops));
            }

            // read the public transport
            vehicles = new ArrayList<>();
            int vehicleCount = Integer.parseInt(elements.next().trim());
            for (int i = 0; i < vehicleCount; i++) {
                String vehicle = elements.next();
                vehicles.add(PublicTransport.decode(vehicle, routes));
            }

            // there should be no extra lines in the file
            if (elements.hasNext()) {
                throw new TransportFormatException();
            }
        } catch (NoSuchElementException | NumberFormatException e) {
            throw new TransportFormatException();
        }
    }

    /**
     /**
     * Adds the given stop to the transportation network.
     *
     * <p>If the given stop is null, it should not be added to the network.
     * If the stop is already in the list, a DuplicateStopException
     * should be thrown
     *
     * @param stop The stop to add to the network.
     * @throws DuplicateStopException - If the given stop already exists
     * in the network.
     */
    public void addStop(Stop stop) throws DuplicateStopException {
        if (stop == null) {
            return;
        }

        if (stops.contains(stop)){
            throw new DuplicateStopException();
        }

        stops.add(stop);
    }

    /**
     * Adds multiple stops to the transport network.
     *
     * <p>If any of the stops in the given list are null, none of them should be
     * added (i.e. either all of the stops are added, or none are).
     * If any of the stops in the given list already exist in the network,
     * a DuplicateStopException should be thrown
     *
     * @param stops The stops to add to the network.
     * @throws DuplicateStopException - If any of the stops already exist in
     * the network. If there are any duplicate stops, none of the stops should
     * be added (i.e. either all of the stops are added, or none are).
     */
    public void addStops(List<Stop> stops) throws DuplicateStopException {
        for (Stop stop : stops) {

            if (stop == null) {
                return;
            }

            if (stops.contains(stop)){
                throw new DuplicateStopException();
            }
        }
        this.stops.addAll(stops);
    }

    /**
     * Gets all of the stops in this network.
     *
     * <p>Modifying the returned list should not result in changes to the
     * internal state of the class.
     *
     * @return All the stops in the network.
     */
    public List<Stop> getStops() {
        return new ArrayList<>(stops);
    }

    /**
     * Adds the given route to the network.
     *
     * <p>If the given route is null, it should not be added to the network.
     *
     * @param route The route to add to the network.
     */
    public void addRoute(Route route) {
        if (route != null) {
            routes.add(route);
        }
    }

    /**
     * Gets all the routes in this network.
     *
     * <p>Modifying the returned list should not result in changes to the
     * internal state of the class.
     *
     * @return All the routes in the network.
     */
    public List<Route> getRoutes() {
        return new ArrayList<>(routes);
    }

    /**
     * Adds the given vehicle to the network.
     *
     * <p>If the given vehicle is null, it should not be added to the network.
     *
     * @param vehicle The vehicle to add to the network.
     */
    public void addVehicle(PublicTransport vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
        }
    }

    /**
     * Gets all the vehicles in this transportation network.
     *
     * <p>Modifying the returned list should not result in changes to the
     * internal state of the class.
     *
     * @return All the vehicles in the transportation network.
     */
    public List<PublicTransport> getVehicles() {

        return new ArrayList<>(vehicles);
    }

    /**
     * Saves this network to the file indicated by the given filename.
     *
     * <p>The file should be written with the same format as described in the
     * {@link #Network(String)} constructor.
     *
     * <p>The stops should be written to the file in the same order in which
     * they were added to the network. This also applies to the routes and the
     * vehicles.
     *
     * <p>If the given filename is null, the method should do nothing.
     *
     * @param filename The name of the file to save the network to.
     * @throws IOException If there are any IO errors whilst writing to the
     * file.
     */
    public void save(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(this.encode());
        writer.close();
    }

    /*
     * Encodes the given list into a String of the format:
     * {size}
     * {toString}
     * {toString}
     * ...
     * {toString}
     *
     * where {size} is the size of the list and {toString} is a call to the
     * toString method of each item in the list.
     */
    private String encodeComponent(List<? extends Writeable> toEncode) {
        StringBuilder builder = new StringBuilder();

        builder.append(toEncode.size()).append(NEWLINE);
        for (Writeable component : toEncode) {
            builder.append(component.encode()).append(NEWLINE);
        }

        return builder.toString();
    }

    /*
     * Encodes the component of this network as a string.
     */
    private String encode() {
        return encodeComponent(stops) + encodeComponent(routes) +
                encodeComponent(vehicles);
    }
}
