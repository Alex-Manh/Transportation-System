package stops;

import java.util.*;

/**
 * The class should map destination stops to RoutingEntry objects.
 *
 * <p>The table is able to redirect passengers from their current stop to the
 * next intermediate stop which they should go to in order to reach their final
 * destination.
 */
public class RoutingTable {
    //The stop for which this table will handle routing.
    private Stop initialStop;

    //A map of neighbouring stops
    private Map<Stop, RoutingEntry> neighbours;

    //A map of cost to neighbouring stops
    private Map<Stop, Integer> costs;

    /**
     * Creates a new RoutingTable for the given stop.
     *
     * <p>The routing table should be created with an entry for its initial stop
     * (i.e. a mapping from the stop to a {@link RoutingEntry#RoutingEntry()}
     * for that stop.
     * @param initialStop The stop for which this table will handle routing.
     */
    public RoutingTable(Stop initialStop){
        this.initialStop = initialStop;
        neighbours = new HashMap<>();
        costs = new HashMap<>();

        //The initialStop should be able to reach itself, at the cost of 0
        costs.put(initialStop, 0);
        neighbours.put(initialStop, new RoutingEntry(initialStop, 0));
    }

    /**
     * Adds the given stop as a neighbour of the stop stored in this table.
     *
     * <p>A neighbouring stop should be added as a destination in this table,
     * with the cost to reach that destination simply being the Manhattan
     * distance between this table's stop and the given neighbour stop.
     *
     * <p>If the given neighbour already exists in the table, it should be
     * updated (as defined in {@link #addOrUpdateEntry(Stop, int, Stop)}).
     *
     * <p>The 'intermediate'/'next' stop between this table's stop and the new
     * neighbour stop should simply be the neighbour stop itself.
     *
     * <p>Once the new neighbour has been added as an entry, this table should
     * be synchronised with the rest of the network using the
     * {@link #synchronise()} method.
     *
     * @param neighbour
     */
    public void addNeighbour(Stop neighbour){
        getStop().addNeighbouringStop(neighbour);
        int cost = getStop().distanceTo(neighbour);

        costs.put(neighbour, cost);
        neighbours.put(neighbour, new RoutingEntry(neighbour, cost));
        synchronise();
    }

    /**
     * If there is currently no entry for the destination in the table, a
     * new entry for the given destination should be added, with a RoutingEntry
     * for the given cost and next (intermediate) stop.
     *
     * <p>If there is already an entry for the given destination, and the
     * newCost is lower than the current cost associated with the destination,
     * then the entry should be updated to have the given newCost and next
     * (intermediate) stop.
     *
     * <p>If there is already an entry for the given destination, but the
     * newCost is greater than or equal to the current cost associated with
     * the destination, then the entry should remain unchanged.
     *
     * @param destination The destination stop to add/update the entry.
     * @param newCost The new cost to associate with the new/updated entry.
     * @param intermediate The new intermediate/next stop to associate with
     *                     the new/updated entry.
     * @return True if a new entry was added, or an existing one was updated,
     * or false if the table remained unchanged.
     */
    public boolean addOrUpdateEntry(Stop destination,
                                    int newCost,
                                    Stop intermediate){
        //Add entry
        if (!neighbours.containsKey(destination)){
            neighbours.put(destination, new RoutingEntry(intermediate, newCost));
            costs.put(destination, newCost);
            return true;
        }

        //Update entry
        int currentCost = costTo(destination);

        if (newCost < currentCost){
            RoutingEntry updateEntry = new RoutingEntry(intermediate, newCost);
            neighbours.replace(destination, updateEntry);
            costs.replace(destination, newCost);
            return true;
        }
        return false;
    }

    /**
     * Returns the cost associated with getting to the given stop.
     * @param stop The stop to get the cost.
     * @return The cost to the given stop, or Integer.MAX_VALUE if the stop
     * is not currently in this routing table.
     */
    public int costTo(Stop stop){
        if (costs.containsKey(stop)){
            return costs.get(stop);
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Maps each destination stop in this table to the cost associated with
     * getting to that destination.
     *
     * @return A mapping from destination stops to the costs associated with
     * getting to those stops.
     */
    public Map<Stop, Integer> getCosts(){
        return new HashMap<>(costs);
    }

    /**
     * Return the stop for which this table will handle routing.
     * @return The stop for which this table will handle routing.
     */
    public Stop getStop(){
        return initialStop;
    }

    /**
     * Returns the next intermediate stop which passengers should be routed to
     * in order to reach the given destination. If the given stop is null or
     * not in the table, then return null
     *
     * @param destination The destination which the passengers are being routed.
     * @return The best stop to route the passengers to in order to reach the
     * given destination.
     */
    public Stop nextStop(Stop destination){
        if (destination == null || !neighbours.containsKey(destination)){
            return null;
        }
        return neighbours.get(destination).getNext();
    }

    /**
     * <p>Synchronises this routing table with the other tables in the network.
     *
     * <p>In each iteration, every stop in the network which is reachable by
     * this table's stop (as returned by {@link #traverseNetwork()})
     * must be considered. For each stop x in the network, each of its neighbours
     * must be visited, and the entries from x must be transferred to each
     * neighbour (using the {@link #transferEntries(Stop)} method).
     *
     * <p>If any of these transfers results in a change to the table that the
     * entries are being transferred, then the entire process must be repeated
     * again. These iterations should continue happening until no changes occur
     * to any of the tables in the network.
     *
     * <p>This process is designed to handle changes which need to be propagated
     * throughout the entire network, which could take more than one iteration.
     */
    public void synchronise(){
        List<Stop> currentReach = traverseNetwork();

        //Update the reachable stop's table
        for (Stop current : currentReach){
            boolean check = transferEntries(current);

            //If exists primary change
            //Implement secondary change
            while (check && current != getStop()){
                current.getRoutingTable().synchronise();
                check = false;
            }
        }

        //Update to this stop's table
        for (Stop current : currentReach){
            boolean check2 = current.getRoutingTable().transferEntries(getStop());
            while (check2){
                synchronise();
                check2 = false;
            }
        }
    }

    /**
     * <p>Updates the entries in the routing table of the given other stop, with
     * the entries from this routing table.
     *
     * <p>If this routing table has entries which the other stop's table doesn't,
     * then the entries should be added to the other table (as defined in
     * {@link #addOrUpdateEntry(Stop, int, Stop)} with the cost being updated
     * to include the distance.
     *
     * <p>If this routing table has entries which the other stop's table does
     * have, and the new cost would be lower than that associated with its
     * existing entry, then its entry should be updated (as defined in
     * {@link #addOrUpdateEntry(Stop, int, Stop)}.
     *
     * <p>If this routing table has entries which the other stop's table does
     * have, but the new cost would be greater than or equal to that associated
     * with its existing entry, then its entry should remain unchanged.
     *
     * @param other The stop whose routing table this table's entries should
     *              be transferred.
     * @return True if any new entries were added to the other stop's table,
     * or if any of its existing entries were updated, or false if the other
     * stop's table remains unchanged.
     *
     * @require this.getStop().getNeighbours().contains(other) == true
     */
    public boolean transferEntries(Stop other){
        //Record if there's any changes made to the otherStop
        int change = 0;

        for (Stop destination : neighbours.keySet()){
            Stop intermediateStop = getStop();

            //newCost = otherStop to initialStop + initialStop to destinationStop
            //If any of them are Integer.Max_value, newCost = Integer.Max_value
            int newCost;
            int costToOther = costTo(other);
            int costToDestination = costTo(destination);

            if (costToOther == Integer.MAX_VALUE ||
                    costToDestination == Integer.MAX_VALUE){
                newCost = Integer.MAX_VALUE;
            } else {
                newCost = costToOther + costToDestination;
            }

            boolean check = other.getRoutingTable().addOrUpdateEntry
                    (destination, newCost, intermediateStop);

            if (check){
                change++;
            }
        }
        return change != 0;
    }

    /**
     * Performs a traversal of all the stops in the network, and returns a list
     * of every stop which is reachable from the stop stored in this table.
     * <ol>
     *     <li>Firstly create an empty list of Stops and an empty Stack
     *     of Stops. </li>
     *     <li>Push the RoutingTable's Stop on to the stack.</li>
     *     <li>While the stack is not empty,</li>
     *     <ol>
     *         <li>pop the top Stop (current) from the stack.</li>
     *         <li>For each of that stop's neighbours,</li>
     *         <ol>
     *             <li>if they are not in the list, add them to the stack.</li>
     *         </ol>
     *         <li>Then add the current Stop to the list.</li>
     *     </ol>
     *     <li>Return the list of seen stops.</li>
     * </ol>
     *
     * @return All of the stops in the network which are reachable by the stop
     * stored in this table.
     */
    public List<Stop> traverseNetwork(){
        HashSet<Stop> reachableStops = new HashSet<>();
        Stack<Stop> stack = new Stack<>();
        stack.push(getStop());

        while (!stack.empty()){
            Stop current = stack.pop();

            for (Stop neighbour: current.getNeighbours()){

                if (!reachableStops.contains(neighbour)){
                    stack.push(neighbour);
                }
            }
            reachableStops.add(current);
        }
        return new ArrayList<>(reachableStops);
    }
}
