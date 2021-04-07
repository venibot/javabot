package workers;

import api.Database;
import api.models.database.Bot;
import api.models.exceptions.AlreadyInDatabaseException;
import api.models.workers.*;
import api.utils.Config;

@WorkerInfo(name = "BotStat", description = "Занесение статистики бота в базу данных")
public class BotStatWorker implements Worker {

    public void execute() {
        Database db = new Database();
        try {
            db.addBotStat(new Bot(db.getLastStatID() + 1, Config.BOT, Config.COMMANDS_COMPLETED));
        } catch (AlreadyInDatabaseException e) {
            e.printStackTrace();
        }
    }
}
