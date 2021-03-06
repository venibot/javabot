package api.models.workers;

public class WorkerException extends RuntimeException {

    private static final long serialVersionUID = -3419515084851063729L;

    WorkerException(Throwable cause) {
        super(cause);
    }

}
