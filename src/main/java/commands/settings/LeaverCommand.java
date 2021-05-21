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

@DiscordCommand(name = "leaver", description = "Настройка прощаний", aliases = {"прощания"},
        usage = "<канал/сообщение> <Значение>", group = "Настройки", arguments = 2, permissions = {Permission.MANAGE_SERVER})
public class LeaverCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите одно из доступных действий:"
                    + "\nканал\nсообщение");
            context.sendMessage(errorEmbed).queue();
        } else {
            Guild DBGuild = context.getDatabaseGuild();

            switch (arguments[0]) {
                case "channel":
                case "канал":
                    if (arguments.length != 1) {

                        if (!arguments[1].equals("0")) {
                            try {
                                DBGuild.setLeftChannel(Converters.getTextChannel(context.getGuild(),
                                        arguments[1]).getIdLong());

                                Database db = new Database();
                                db.updateGuild(DBGuild);
                                BasicEmbed successEmbed = new BasicEmbed("success",
                                        "Канал для прощальных сообщений успешно установлен");
                                context.sendMessage(successEmbed).queue();
                            } catch (ChannelNotFoundException e) {
                                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный канал не найден");
                                context.sendMessage(errorEmbed).queue();
                            }
                        } else {
                            Database db = new Database();
                            DBGuild.setLeftChannel(null);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success",
                                    "Канал для прощальных сообщений успешно сброшен");
                            context.sendMessage(successEmbed).queue();
                        }
                    } else {
                        BasicEmbed infoEmbed = new BasicEmbed("info");

                        infoEmbed.setDescription("Канал, установленный для прощальных сообщений на данный момент"
                                + (DBGuild.getLeftChannel() != null
                                ? (": " + context.getGuild()
                                .getTextChannelById(DBGuild.getLeftChannel()).getAsMention()) : " отсутствует")
                                + ".\nДля установки канала для прощальных сообщений используйте `"
                                + Config.BOT_CONFIG.get("prefix")
                                + "leaver канал` и укажите необходимый канал(0 для сброса)");

                        context.sendMessage(infoEmbed).queue();
                    }
                    break;
                case "message":
                case "сообщение":
                    if (arguments.length != 1) {
                        Database db = new Database();
                        if (!arguments[1].equals("0")) {
                            DBGuild.setLeftMessage(arguments[1]);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success", "Прощальное сообщение успешно установлено");
                            context.sendMessage(successEmbed).queue();
                        } else {
                            DBGuild.setLeftMessage(null);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success", "Прощальное сообщение успешно сброшено");
                            context.sendMessage(successEmbed).queue();
                        }
                    } else {
                        BasicEmbed infoEmbed = new BasicEmbed("info");

                        infoEmbed.setDescription("Прощальное сообщение на данный момент"
                                + (!DBGuild.getLeftMessage().equals("")
                                ? ": " + DBGuild.getLeftMessage() : " отсутствует")
                                + ".\nДля установки прощального сообщений используйте `"
                                + Config.BOT_CONFIG.get("prefix")
                                + "leaver сообщение` и укажите необходимое сообщение(0 для сброса). " +
                                "Доступные в сообщении переменные: {{member.tag}} - имя пользователя, {{member.mention}}" +
                                " - упоминание пользователя, {{guild.memberCount}} - кол-во участников на сервере");

                        context.sendMessage(infoEmbed).queue();
                    }
                    break;
            }
        }
    }
}
