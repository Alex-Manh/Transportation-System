package utilities;

/**
 * Defines the interface for classes which are able to be encoded for writing
 * into files.
 */
public interface Writeable {
    /**
     * Creates a string representation of the object for writing to a file.
     *
     * <p>See implementing classes for specific implementation details.
     *
     * @return A string representation of the object.
     */
    String encode();
}
