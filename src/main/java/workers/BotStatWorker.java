package workers;

import api.Database;
import api.models.database.Bot;
import api.models.exceptions.AlreadyInDatabaseException;
import api.models.workers.Worker;
import api.models.workers.WorkerInfo;
import api.utils.Config;
import net.dv8tion.jda.api.JDA;

@WorkerInfo(name = "BotStat", description = "Занесение статистики бота в базу данных")
public class BotStatWorker implements Worker {

    public void execute(JDA bot) {
        if (bot.getGatewayPing() > 0) {
            Database db = new Database();
            try {
                db.addBotStat(new Bot(db.getLastStatID() + 1, bot, Config.COMMANDS_COMPLETED));
            } catch (AlreadyInDatabaseException e) {
                e.printStackTrace();
            }
        }
    }

}
