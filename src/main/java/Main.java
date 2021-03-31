import api.Database;
import api.models.command.Command;
import api.models.command.CommandHandler;
import api.models.database.Reminder;
import api.models.workers.WorkerHandler;
import api.utils.Config;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import workers.BotStatWorker;
import workers.ReminderWorker;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.FutureTask;

public class Main {

    public static void main(String[] args) throws LoginException, Exception {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        Config.getBotConfig();
        Config.getDatabaseConfig();
        Config.getMonthsConfig();
        Config.getUserFlagsAsEmojis();
        Config.getPermissions();
        Config.getLogActions();
        Config.getTimes();
        Database db = new Database();
        Integer statID = db.getLastStatID();
        Config.COMMANDS_COMPLETED = statID != 0 ? db.getBotStatByID(statID).getCommandsCount() : 0;
        if (Config.BOT_CONFIG.isEmpty()) {
            throw new Exception("Конфигурационный файл бота не был загружен!");
        }
        Collection<CacheFlag> cacheToDisable = new ArrayList<>();
        cacheToDisable.add(CacheFlag.ACTIVITY);
        cacheToDisable.add(CacheFlag.CLIENT_STATUS);
        builder.disableCache(cacheToDisable);
        Collection<GatewayIntent> intentsToDisable = new ArrayList<>();
        intentsToDisable.add(GatewayIntent.GUILD_PRESENCES);
        builder.disableIntents(intentsToDisable);
        builder.setToken(Config.BOT_CONFIG.get("token"));
        JDA bot = builder.build();
        loadCommands("src/main/java/commands", "commands");
        loadEvents(bot, "src/main/java/events", "events");
        Config.BOT = bot;
        WorkerHandler.registerWorker(new BotStatWorker());
        WorkerHandler.registerWorker(new ReminderWorker());
        FutureTask<Void> task = new FutureTask<>(new WorkerHandler());
        new Thread(task).start();
        bot.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        bot.getPresence().setActivity(Activity.playing("изучение java"));
    }

    public static void loadCommands(String path, String commands_package) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        commands_package += ".";
        File dir = new File(path);
        for (File file: dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".java")){
                Class cmd = Class.forName(commands_package + file.getName().replace(".java", ""));
                Command command = (Command) cmd.newInstance();
                CommandHandler.registerCommand(command);
            }
            else if (file.isDirectory()) {
                loadCommands(file.getPath(), commands_package + file.getName());
            }
        }
    }

    public static void loadEvents(JDA bot, String path, String events_package) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        events_package += ".";
        File dir = new File(path);
        for (File file: dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".java")){
                Class event_class = Class.forName(events_package + file.getName().replace(".java", ""));
                ListenerAdapter event = (ListenerAdapter) event_class.newInstance();
                bot.addEventListener(event);
            }
            else if (file.isDirectory()) {
                loadEvents(bot, file.getPath(), events_package + file.getName());
            }
        }
    }


}
