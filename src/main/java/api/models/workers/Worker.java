package api.models.workers;

import net.dv8tion.jda.api.JDA;

public interface Worker {

    void execute(JDA bot);

    default WorkerInfo getWorkerInfo() {
        return getClass().getAnnotation(WorkerInfo.class);
    }

}
