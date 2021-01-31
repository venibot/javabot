import api.SupportServer;
import api.utils.Config;
import events.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException, Exception {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        Config.getBotConfig();
        if (Config.BOT_CONFIG.isEmpty()) {
            throw new Exception("Конфигурационный файл бота не был загружен!");
        }
        builder.setToken(Config.BOT_CONFIG.get("token"));
        JDA bot = builder.build();
        bot.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        bot.getPresence().setActivity(Activity.playing("изучение java"));
        bot.addEventListener(new MessageReceived());
    }

}
