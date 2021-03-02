package commands.settings;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.database.Guild;
import api.models.exceptions.ChannelNotFoundException;
import api.models.exceptions.RoleNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "welcomer", description = "Настройка приветствий", aliases = {"приветствия"},
        usage = "<Аргумент> <Значение>", group = "Настройки", arguments = 2)
public class WelcomerCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setDescription("Укажите одно из доступных действий:\nроли\nканал\nсообщение\nвосстановление");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        } else {
            Database db = new Database();
            Guild DBGuild = db.getGuildByID(msg_event.getGuild().getIdLong());
            switch (arguments[0]) {
                case "roles":
                case "роли":
                    if (arguments.length != 1) {
                        if (!arguments[1].equals("0")) {
                            String[] rolesSplit = arguments[1].split(", ");
                            Long[] roles = new Long[rolesSplit.length];
                            int i = 0;
                            for (String role : rolesSplit) {
                                try {
                                    roles[i] = Converters.getRole(msg_event.getGuild(), role).getIdLong();
                                } catch (RoleNotFoundException ignored) {

                                }
                            }
                            DBGuild.setWelcomeRoles(roles);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success");
                            successEmbed.setDescription("Роли успешно установлены в качестве приветственных");
                            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
                        } else {
                            DBGuild.setWelcomeRoles(null);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success");
                            successEmbed.setDescription("Приветственные роли успешно сброшены");
                            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
                        }
                    } else {
                        Long[] rolesIDs = DBGuild.getWelcomeRoles();
                        String roles = "";
                        for (Long roleID: rolesIDs) {
                            roles += msg_event.getGuild().getRoleById(roleID).getAsMention();
                        }
                        BasicEmbed infoEmbed = new BasicEmbed("info");
                        infoEmbed.setDescription("Роли, установленные в качестве приветственных на данный момент"
                                + (roles != "" ? (": " + roles) : " отсутствуют")
                                + ".\nДля установки приветственных ролей используйте `..welcomer роли` и перечислите все роли через запятую(0 для сброса)");
                        msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
                    }
                    break;
                case "channel":
                case "канал":
                    if (arguments.length != 1) {
                        if (!arguments[1].equals("0")) {
                            try {
                                DBGuild.setWelcomeChannel(Converters.getTextChannel(msg_event.getGuild(), arguments[1]).getIdLong());
                                db.updateGuild(DBGuild);
                                BasicEmbed successEmbed = new BasicEmbed("success");
                                successEmbed.setDescription("Канал для приветственных сообщений успешно установлен");
                                msg_event.getChannel().sendMessage(successEmbed.build()).queue();
                            } catch (ChannelNotFoundException e) {
                                BasicEmbed errorEmbed = new BasicEmbed("error");
                                errorEmbed.setDescription("Указанный канал не найден");
                                msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
                            }
                        } else {
                            DBGuild.setWelcomeChannel(null);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success");
                            successEmbed.setDescription("Канал для приветственныз сообщений успешно сброшен");
                            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
                        }
                    } else {
                        BasicEmbed infoEmbed = new BasicEmbed("info");
                        infoEmbed.setDescription("Канал, установленный для приветственных сообщений на данный момент"
                                + (DBGuild.getWelcomeChannel() != null
                                ? (": " + msg_event.getGuild().getTextChannelById(DBGuild.getWelcomeChannel()).getAsMention()) : " отсутствует")
                                + ".\nДля установки канала для приветственных сообщений используйте `..welcomer канал` и укажите необходимый канал(0 для сброса)");
                        msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
                    }
                    break;
                case "message":
                case "сообщение":
                    if (arguments.length != 1) {
                        if (!arguments[1].equals("0")) {
                            DBGuild.setWelcomeMessage(arguments[1]);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success");
                            successEmbed.setDescription("Приветственное сообщение успешно установлено");
                            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
                        } else {
                            DBGuild.setWelcomeChannel(null);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success");
                            successEmbed.setDescription("Приветственное сообщение успешно сброшено");
                            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
                        }
                    } else {
                        BasicEmbed infoEmbed = new BasicEmbed("info");
                        infoEmbed.setDescription("Приветственное сообщение на данный момент"
                                + (!DBGuild.getWelcomeMessage().equals("")
                                ? ": " + DBGuild.getWelcomeMessage() : " отсутствует")
                                + ".\nДля установки канала для приветственных сообщений используйте `..welcomer сообщение` и укажите необходимое сообщение(0 для сброса). Доступные в сообщении переменные: {{member.tag}} - имя пользователя, {{member.mention}} - упоминание пользователя, {{guild.memberCount}} - кол-во участников на сервере");
                        msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
                    }
                    break;
                case "restore":
                case "восстановление":
                    if (arguments.length != 1) {
                        boolean toRestoreRoles = arguments[1].equals("1");
                        DBGuild.setRestoreRoles(toRestoreRoles);
                        db.updateGuild(DBGuild);
                        BasicEmbed successEmbed = new BasicEmbed("success");
                        successEmbed.setDescription("Восстановление ролей успешно " + (toRestoreRoles ? "включено" : "отключено"));
                        msg_event.getChannel().sendMessage(successEmbed.build()).queue();
                    } else {
                        BasicEmbed infoEmbed = new BasicEmbed("info");
                        infoEmbed.setDescription("Приветственное сообщение на данный момент "
                                + (DBGuild.getRestoreRoles() ? "включено" : "отключено")
                                + ".\nДля изменения используйте `..welcomer восстановление` и укажите необходимое состояние(1 для включения, 0 для отключения).");
                        msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
                    }
                    break;
            }
        }
    }

}
