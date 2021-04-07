package api.models.workers;

public interface Worker {

    void execute();

    default WorkerInfo getWorkerInfo() {
        return getClass().getAnnotation(WorkerInfo.class);
    }
}
