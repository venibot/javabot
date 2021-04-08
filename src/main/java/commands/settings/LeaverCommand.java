package commands.settings;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.database.Guild;
import api.models.exceptions.ChannelNotFoundException;
import api.utils.*;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "leaver", description = "Настройка прощаний", aliases = {"прощания"},
        usage = "<Аргумент> <Значение>", group = "Настройки", arguments = 2, permissions = {Permission.MANAGE_SERVER})
public class LeaverCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {

        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите одно из доступных действий:\nканал\nсообщение");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        } else {
            Database db = new Database();
            Guild DBGuild = db.getGuildByID(msg_event.getGuild().getIdLong());

            switch (arguments[0]) {
                case "channel":
                case "канал":
                    if (arguments.length != 1) {

                        if (!arguments[1].equals("0")) {
                            try {
                                DBGuild.setLeftChannel(Converters.getTextChannel(msg_event.getGuild(),
                                        arguments[1]).getIdLong());

                                db.updateGuild(DBGuild);
                                BasicEmbed successEmbed = new BasicEmbed("success", "Канал для прощальных сообщений успешно установлен");
                                msg_event.getChannel().sendMessage(successEmbed.build()).queue();
                            } catch (ChannelNotFoundException e) {
                                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный канал не найден");
                                msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
                            }
                        } else {
                            DBGuild.setLeftChannel(null);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success", "Канал для прощальных сообщений успешно сброшен");
                            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
                        }
                    } else {
                        BasicEmbed infoEmbed = new BasicEmbed("info");

                        infoEmbed.setDescription("Канал, установленный для прощальных сообщений на данный момент"
                                + (DBGuild.getLeftChannel() != null
                                ? (": " + msg_event.getGuild()
                                .getTextChannelById(DBGuild.getLeftChannel()).getAsMention()) : " отсутствует")
                                + ".\nДля установки канала для прощальных сообщений используйте `"
                                + Config.BOT_CONFIG.get("prefix")
                                + "leaver канал` и укажите необходимый канал(0 для сброса)");

                        msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
                    }
                    break;
                case "message":
                case "сообщение":
                    if (arguments.length != 1) {
                        if (!arguments[1].equals("0")) {

                            DBGuild.setLeftMessage(arguments[1]);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success", "Прощальное сообщение успешно установлено");
                            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
                        } else {
                            DBGuild.setLeftMessage(null);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success", "Прощальное сообщение успешно сброшено");
                            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
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

                        msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
                    }
                    break;
            }
        }
    }
}
