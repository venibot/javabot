import api.models.command.Command;
import api.models.command.CommandHandler;
import api.utils.Config;
import events.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;
import java.io.File;

public class Main {

    public static void main(String[] args) throws LoginException, Exception {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        Config.getBotConfig();
        if (Config.BOT_CONFIG.isEmpty()) {
            throw new Exception("Конфигурационный файл бота не был загружен!");
        }
        builder.setToken(Config.BOT_CONFIG.get("token"));
        JDA bot = builder.build();
        loadCommands("src/main/java/commands", "commands");
        bot.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        bot.getPresence().setActivity(Activity.playing("изучение java"));
        bot.addEventListener(new MessageReceived());
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
                loadCommands(file.getPath(), "commands." + file.getName());
            }
        }
    }

}
