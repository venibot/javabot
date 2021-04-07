package api.models.workers;

import java.util.*;
import java.util.concurrent.Callable;

public class WorkerHandler implements Callable<Void> {

    public static Set<Worker> workers = new HashSet<>();

    public static HashMap<Worker, Thread> workersThreads = new HashMap<>();

    public static void registerWorkers(Worker... worker) {
        Collections.addAll(WorkerHandler.workers, worker);
    }

    public static void registerWorker(Worker worker) {
        WorkerHandler.registerWorkers(worker);
    }

    public static void unregisterWorkers(Worker... workers) {
        WorkerHandler.workers.removeAll(Arrays.asList(workers));
    }

    public static void unregisterWorker(Worker worker) {
        WorkerHandler.unregisterWorkers(worker);
    }

    private Timer timer = new Timer();

    class ExecuteTask extends TimerTask {

        private Worker worker;

        public ExecuteTask(Worker worker) {
            this.worker = worker;
        }

        public void run() {

            WorkerInfo info = this.worker.getWorkerInfo();

            if (info == null) return;

            try {
                this.worker.execute();
            } catch (Exception error) {
                error.printStackTrace();
            }

            timer.schedule(new ExecuteTask(this.worker), this.worker.getWorkerInfo().type()
                    .toMillis(this.worker.getWorkerInfo().duration()));
        }
    }

    @Override
    public Void call() {
        for (Worker worker: WorkerHandler.workers) {
            timer.schedule(new ExecuteTask(worker), worker.getWorkerInfo().type()
                    .toMillis(worker.getWorkerInfo().duration()));
        }
        return null;
    }
}
