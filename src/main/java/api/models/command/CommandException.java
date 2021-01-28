package api.models.command;

public class CommandException extends RuntimeException {
    private static final long serialVersionUID = -3419515084851063729L;

    CommandException(Throwable cause) {
        super(cause);
    }
}