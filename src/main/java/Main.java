import api.Database;
import api.models.command.*;
import api.models.workers.*;
import api.utils.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.FutureTask;

public class Main {

    public static void main(String[] args) throws LoginException, Exception {
        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        Config.init();
        Database db = new Database();
        Integer statID = db.getLastStatID();
        Config.COMMANDS_COMPLETED = statID != 0 ? db.getBotStatByID(statID).getCommandsCount() : 0;
        if (Config.BOT_CONFIG.isEmpty()) {
            throw new Exception("Конфигурационный файл бота не был загружен!");
        }

        Collection<CacheFlag> cache = new ArrayList<>();
        cache.add(CacheFlag.EMOTE);
        cache.add(CacheFlag.VOICE_STATE);
        cache.add(CacheFlag.MEMBER_OVERRIDES);
        cache.add(CacheFlag.ROLE_TAGS);
        builder.enableCache(cache);

        Collection<GatewayIntent> intents = new ArrayList<>();
        intents.add(GatewayIntent.GUILD_MESSAGES);
        intents.add(GatewayIntent.DIRECT_MESSAGES);
        intents.add(GatewayIntent.GUILD_BANS);
        intents.add(GatewayIntent.GUILD_INVITES);
        intents.add(GatewayIntent.GUILD_MESSAGE_REACTIONS);
        intents.add(GatewayIntent.GUILD_EMOJIS);
        intents.add(GatewayIntent.GUILD_MEMBERS);
        intents.add(GatewayIntent.GUILD_VOICE_STATES);
        intents.add(GatewayIntent.GUILD_WEBHOOKS);
        builder.enableIntents(intents);

        builder.setToken(Config.BOT_CONFIG.get("token"));
        ShardManager bot = builder.build();
        loadCommands("src/main/java/commands", "commands");
        loadWorkers("src/main/java/workers", "workers");
        loadEvents(bot, "src/main/java/events", "events");
        Config.BOT = bot;
        FutureTask<Void> task = new FutureTask<>(new WorkerHandler());
        new Thread(task).start();
        for (JDA shard: bot.getShards()) {
            shard.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
            shard.getPresence().setActivity(Activity.playing(shard.getShardInfo().getShardId() + " шард | veni.tk"));
        }
    }

    public static void loadCommands(String path, String commands_package)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
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

    public static void loadEvents(ShardManager bot, String path, String events_package)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
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

    public static void loadWorkers(String path, String workers_package)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        workers_package += ".";
        File dir = new File(path);
        for (File file: dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".java")){
                Class workerClass = Class.forName(workers_package + file.getName().replace(".java", ""));
                Worker worker = (Worker) workerClass.newInstance();
                WorkerHandler.registerWorker(worker);
            }
            else if (file.isDirectory()) {
                loadCommands(file.getPath(), workers_package + file.getName());
            }
        }
    }
}
