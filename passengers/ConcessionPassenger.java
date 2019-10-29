package passengers;

import stops.Stop;

/**
 * A passenger that pays concession fares. Concession fares require a concession
 * id.
 */
public class ConcessionPassenger extends Passenger {
    /* Concession id for validating concession fares. */
    private int concessionId;
    /* Whether the concession passenger has a valid concession id */
    private static final int INVALID = -1;

    /**
     * Construct a new concession fare passenger with the given name and
     * concessionId.
     *
     * <p>Should meet the specification of
     * {@link Passenger#Passenger(String, Stop)}.
     *
     * @param name The name of the passenger.
     * @param destination The destination of the passenger.
     * @param concessionId Identifying number of the passenger's concession
     *                     card.
     */
    public ConcessionPassenger(String name, Stop destination,
                               int concessionId) {
        super(name, destination);
        renew(concessionId);
    }

    /**
     * Sets the concession fare to be expired, and thus invalid.
     * {@link #isValid()} returns false.
     */
    public void expire() {
        this.concessionId = INVALID;
    }

    /**
     * Attempts to renew this concession passenger's fares with the given id.
     *
     * @param newId The ID of the renewed concession card.
     */
    public void renew(int newId) {
        if (newId < 0 || Integer.toString(newId).length() < 6
                || !Integer.toString(newId).startsWith("42")) {
            this.concessionId = INVALID;
        } else {
            this.concessionId = newId;
        }
    }

    /**
     * Returns true if and only if the stored concessionId is valid.
     *
     * <p>In this transportation network, a valid concessionId begins with the
     * digits '42', should be positive, and should be a minimum of six digits in
     * length (for example, 420000 would be a valid concessionId, while 430000,
     * -420000, or 42000 would not).
     *
     * @return True if concession fares have not expired (are valid), false
     * otherwise.
     */
    public boolean isValid() {
        return this.concessionId != INVALID;
    }
}
