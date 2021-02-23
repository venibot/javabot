import api.models.command.Command;
import api.models.command.CommandHandler;
import api.utils.Config;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.File;

public class Main {

    public static void main(String[] args) throws LoginException, Exception {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        Config.getBotConfig();
        Config.getDatabaseConfig();
        Config.getMonthsConfig();
        Config.getUserFlagsAsEmojis();
        Config.getPermissions();
        Config.getLogActions();
        if (Config.BOT_CONFIG.isEmpty()) {
            throw new Exception("Конфигурационный файл бота не был загружен!");
        }
        builder.setToken(Config.BOT_CONFIG.get("token"));
        JDA bot = builder.build();
        loadCommands("src/main/java/commands", "commands");
        loadEvents(bot, "src/main/java/events", "events");
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
                loadCommands(file.getPath(), events_package + file.getName());
            }
        }
    }


}
