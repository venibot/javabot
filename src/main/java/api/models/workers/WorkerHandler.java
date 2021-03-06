package api.models.workers;

import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class WorkerHandler {

    private static final Logger logger = LoggerFactory.getLogger("JDA-Command");

    public static Set<Worker> workers = new HashSet<>();

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

    public static TimerTask execute(Worker worker, JDA bot) {
        WorkerInfo info = worker.getWorkerInfo();
        if (info == null) return null;
        try {
            Thread.sleep(worker.getWorkerInfo().type().toMillis(worker.getWorkerInfo().duration()));
            worker.execute(bot);
            Timer timer = new Timer();
            timer.schedule(WorkerHandler.execute(worker, bot), worker.getWorkerInfo().type().toMillis(worker.getWorkerInfo().duration()));
        } catch (Exception error) {
            logger.error("Ошибка при выполнении задания " + info.name() + ". " + error);
            throw new WorkerException(error);
        }
        return new TimerTask() {
            @Override
            public void run() {
                execute(worker, bot);
            }
        };
    }

    public static void run(JDA bot) {
        for (Worker worker: WorkerHandler.workers) {
            WorkerHandler.execute(worker, bot);
        }
    }

}
