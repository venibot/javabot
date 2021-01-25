import commands.info.PingCommand;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {

    public static final String PATH_TO_BOT_CONFIG = "src/main/resources/bot.properties";

    public static HashMap<String, String> BOT_CONFIG = new HashMap<>();

    public static void main(String[] args) throws LoginException, Exception {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        getBotConfig();
        if (BOT_CONFIG.isEmpty()) {
            throw new Exception("Конфигурационный файл бота не был загружен!");
        }
        builder.setToken(BOT_CONFIG.get("token"));
        JDA bot = builder.build();
        bot.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        bot.getPresence().setActivity(Activity.playing("изучение java"));
        bot.addEventListener(new PingCommand());
    }

    public static void getBotConfig() throws Exception {
        Properties props = new Properties();

        try {
            FileInputStream config_file = new FileInputStream(PATH_TO_BOT_CONFIG);
            props.load(config_file);
            for (String key: props.stringPropertyNames()) {
                BOT_CONFIG.put(key, props.getProperty(key));
            }
        }
        catch (IOException e) {
            throw new Exception("Конфигурационного файла " + BOT_CONFIG + " не обнаружено!");
        }
    }

}
