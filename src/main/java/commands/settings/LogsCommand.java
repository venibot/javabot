package commands.settings;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.database.Guild;
import api.models.exceptions.ChannelNotFoundException;
import api.utils.*;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;

@DiscordCommand(name = "logs", description = "Настройка логов", aliases = {"логи"}, arguments = 2,
        usage = "<Тип действия> <Канал>", group = "Настройки", permissions = {Permission.MANAGE_SERVER})
public class LogsCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error",
                    ("Укажите одно из доступных событий\nДоступные события:\nвсе\n"
                    + String.join("\n", Config.LOG_ACTIONS.keySet()).replace("_", "\\_")));

            context.sendMessage(errorEmbed).queue();

        } else if (arguments.length == 1) {
            if (Config.LOG_ACTIONS.get(arguments[0]) != null) {
                Guild DBGuild = context.getDatabaseGuild();
                BasicEmbed infoEmbed = new BasicEmbed("info");

                infoEmbed.setDescription("Текущий канал для отправки логов по данному событию "
                        + (DBGuild.getLogs().get(Config.LOG_ACTIONS.get(arguments[0])) != null
                        ? context.getGuild().getTextChannelById(DBGuild.getLogs()
                        .get(Config.LOG_ACTIONS.get(arguments[0]))).getAsMention()
                        : "не установлен"));

                context.sendMessage(infoEmbed).queue();
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанное событие не обнаружено");
                context.sendMessage(errorEmbed).queue();
            }
        } else {

            if (Config.LOG_ACTIONS.get(arguments[0]) != null) {
                try {
                    Database db = new Database();
                    Guild DBGuild = context.getDatabaseGuild();
                    HashMap<String, Long> logs = DBGuild.getLogs();

                    logs.put(Config.LOG_ACTIONS.get(arguments[0]),
                            Converters.getTextChannel(context.getGuild(), arguments[1]).getIdLong());
                    db.updateGuild(DBGuild);
                    BasicEmbed successEmbed = new BasicEmbed("success",
                            "Канал для отправки логов о событии успешно обновлён!");
                    context.sendMessage(successEmbed).queue();
                } catch (ChannelNotFoundException e) {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный канал не найден");
                    context.sendMessage(errorEmbed).queue();
                }
            } else if (arguments[0].equals("all") || arguments[0].equals("все")) {
                Guild DBGuild = context.getDatabaseGuild();
                HashMap<String, Long> logs = DBGuild.getLogs();
                TextChannel logChannel = null;

                try {
                    logChannel = Converters.getTextChannel(context.getGuild(), arguments[1]);
                } catch (ChannelNotFoundException e) {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный канал не найден");
                    context.sendMessage(errorEmbed).queue();
                }

                for (String key: Config.LOG_ACTIONS.values()) {
                    logs.put(key, logChannel.getIdLong());
                }

                Database db = new Database();
                db.updateGuild(DBGuild);
                BasicEmbed successEmbed = new BasicEmbed("success",
                        "Канал для отправки логов о всех событиях успешно обновлён!");
                context.sendMessage(successEmbed).queue();
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанное действие не найдено");
                context.sendMessage(errorEmbed).queue();
            }
        }
    }
}
