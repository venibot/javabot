package commands.settings;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.database.Guild;
import api.utils.Config;
import com.mongodb.DB;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

@DiscordCommand(name = "logs", description = "Настройка логов", aliases = {"логи"}, arguments = 2,
        usage = "<Тип действия> <Канал>", group = "Настройки")
public class LogsCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        Database db = new Database();
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setDescription(("Укажите одно из доступных событий\nДоступные события:\n"
                    + String.join("\n", Config.LOG_ACTIONS.keySet()).replace("_", "\\_")));
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        } else if (arguments.length == 1) {
            if (Config.LOG_ACTIONS.get(arguments[0]) != null) {
                Guild DBGuild = db.getGuildByID(msg_event.getGuild().getIdLong());
                BasicEmbed infoEmbed = new BasicEmbed("info");
                infoEmbed.setDescription("Текущий канал для отправки логов по данному событию "
                        + (DBGuild.getLogs().get(Config.LOG_ACTIONS.get(arguments[0])) != null
                        ? msg_event.getGuild().getTextChannelById(DBGuild.getLogs().get(Config.LOG_ACTIONS.get(arguments[0]))).getAsMention()
                        : "не установлен"));
                msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error");
                errorEmbed.setDescription("Указанное событие не обнаружено");
                msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
            }
        } else {
            if (Config.LOG_ACTIONS.get(arguments[0]) != null) {
                Guild DBGuild = db.getGuildByID(msg_event.getGuild().getIdLong());
                HashMap<String, Long> logs = DBGuild.getLogs();
                logs.put(Config.LOG_ACTIONS.get(arguments[0]), Long.parseLong(arguments[1]));
                db.updateGuild(DBGuild);
                BasicEmbed successEmbed = new BasicEmbed("success");
                successEmbed.setDescription("Канал для отправко логов о событии успешно обновлён!");
                msg_event.getChannel().sendMessage(successEmbed.build()).queue();
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error");
                errorEmbed.setDescription("Указанное событие не обнаружено");
                msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
            }
        }
    }

}
