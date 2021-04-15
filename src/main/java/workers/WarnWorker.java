package workers;

import api.Database;
import api.models.database.Warn;
import api.models.workers.*;
import api.utils.Logger;

import java.util.Date;

@WorkerInfo(name = "Warn", description = "Снятие временных варнов")
public class WarnWorker implements Worker {

    public void execute() {
        Database db = new Database();
        for (Warn warn: db.getWarns()) {
            if (warn.getEndTime() <= new Date().getTime() && warn.getEndTime() != 0) {
                db.deleteWarn(warn.getGuildID(), warn.getWarnID());
                Logger.logWarnDelete(warn);
            }
        }
    }
}
