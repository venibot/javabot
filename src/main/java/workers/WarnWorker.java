package workers;

import api.Database;
import api.models.database.Reminder;
import api.models.database.Warn;
import api.models.workers.Worker;
import api.models.workers.WorkerInfo;
import api.utils.Config;
import net.dv8tion.jda.api.entities.User;

import java.util.Date;

@WorkerInfo(name = "Warn", description = "Снятие временных варнов")
public class WarnWorker implements Worker {

    public void execute() {
        Database db = new Database();
        for (Warn warn: db.getWarns()) {
            if (warn.getEndTime() <= new Date().getTime()) {
                db.deleteWarn(warn.getGuildID(), warn.getWarnID());
            }
        }
    }

}
