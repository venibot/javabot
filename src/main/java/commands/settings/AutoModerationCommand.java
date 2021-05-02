package commands.settings;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.database.Guild;
import api.models.exceptions.ChannelNotFoundException;
import api.utils.Config;
import api.utils.Converters;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;

@DiscordCommand(name = "auto-moderation", description = "Настройка автомодерации",
        aliases = {"auto-mod", "авто-модерация", "авто-мод", "automod", "автомод"},
        arguments = 2, usage = "<Тип модерации> <Состояние>", group = "Настройки", permissions = {Permission.MANAGE_SERVER})
public class AutoModerationCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error",
                    ("Укажите одну из доступных настроек\nДоступные настройки:\nвсе\n"
                    + String.join("\n", Config.AUTO_MOD_ACTIONS.keySet()).replace("_", "\\_")));

            context.sendMessage(errorEmbed).queue();

        } else if (arguments.length == 1) {
            if (Config.AUTO_MOD_ACTIONS.get(arguments[0]) != null) {
                Guild DBGuild = context.getDatabaseGuild();
                BasicEmbed infoEmbed = new BasicEmbed("info");
                Boolean status = DBGuild.getAutoModeration() != null
                        && DBGuild.getAutoModeration().get(Config.AUTO_MOD_ACTIONS.get(arguments[0])) != null
                        ? DBGuild.getAutoModeration().get(Config.AUTO_MOD_ACTIONS.get(arguments[0])) : false;

                infoEmbed.setDescription("Текущее состояние автомодерации по данной настройке: "
                        + (status != null && status
                        ? "включено"
                        : "отключено"));

                context.sendMessage(infoEmbed).queue();
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанная настройка не обнаружена");
                context.sendMessage(errorEmbed).queue();
            }
        } else {

            if (Config.AUTO_MOD_ACTIONS.get(arguments[0]) != null) {
                Guild DBGuild = context.getDatabaseGuild();
                HashMap<String, Boolean> settings;
                if (DBGuild.getAutoModeration() != null) {
                    settings = DBGuild.getAutoModeration();
                } else {
                    settings = new HashMap<>();
                }

                settings.put(Config.AUTO_MOD_ACTIONS.get(arguments[0]),
                        arguments[1].equals("true") || arguments[1].equals("включить"));
                context.getDatabase().updateGuild(DBGuild);
                BasicEmbed successEmbed = new BasicEmbed("success",
                        "Статус данной настройки успешно обновлён!");
                context.sendMessage(successEmbed).queue();
            } else if (arguments[0].equals("all") || arguments[0].equals("все")) {
                Guild DBGuild = context.getDatabaseGuild();
                HashMap<String, Boolean> settings = DBGuild.getAutoModeration();

                Boolean status = arguments[1].equals("true") || arguments[1].equals("включить");
                for (String key: Config.AUTO_MOD_ACTIONS.values()) {
                    settings.put(key, status);
                }

                context.getDatabase().updateGuild(DBGuild);
                BasicEmbed successEmbed = new BasicEmbed("success",
                        "Статус всех настроек автомодерации успешно обновлён!");
                context.sendMessage(successEmbed).queue();
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанная настройкв не найдена");
                context.sendMessage(errorEmbed).queue();
            }
        }
    }
}
