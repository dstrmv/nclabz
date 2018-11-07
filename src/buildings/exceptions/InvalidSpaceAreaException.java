package buildings.exceptions;

public class InvalidSpaceAreaException extends IllegalArgumentException {
    public InvalidSpaceAreaException() {
        super();
    }

    public InvalidSpaceAreaException(String s) {
        super(s);
    }

    public InvalidSpaceAreaException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSpaceAreaException(Throwable cause) {
        super(cause);
    }
}
