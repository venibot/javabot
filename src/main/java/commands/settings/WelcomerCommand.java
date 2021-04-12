package commands.settings;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.database.Guild;
import api.models.exceptions.*;
import api.utils.*;
import net.dv8tion.jda.api.Permission;

@DiscordCommand(name = "welcomer", description = "Настройка приветствий", aliases = {"приветствия"},
        usage = "<Аргумент> <Значение>", group = "Настройки", arguments = 2, permissions = {Permission.MANAGE_SERVER})
public class WelcomerCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите одно из доступных действий:"
                    + "\nроли\nканал\nсообщение\nвосстановление");
            context.sendMessage(errorEmbed).queue();
        } else {
            Guild DBGuild = context.getDatabaseGuild();

            switch (arguments[0]) {
                case "roles":
                case "роли":
                    if (arguments.length != 1) {
                        Database db = new Database();
                        if (!arguments[1].equals("0")) {
                            String[] rolesSplit = arguments[1].split(", ");
                            Long[] roles = new Long[rolesSplit.length];
                            int i = 0;

                            for (String role : rolesSplit) {
                                try {
                                    roles[i] = Converters.getRole(context.getGuild(), role).getIdLong();
                                } catch (RoleNotFoundException ignored) {
                                    System.out.println(role);
                                }
                                i += 1;
                            }

                            DBGuild.setWelcomeRoles(roles);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success",
                                    "Роли успешно установлены в качестве приветственных");
                            context.sendMessage(successEmbed).queue();
                        } else {
                            DBGuild.setWelcomeRoles(null);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success",
                                    "Приветственные роли успешно сброшены");
                            context.sendMessage(successEmbed).queue();
                        }
                    } else {
                        Long[] rolesIDs = DBGuild.getWelcomeRoles();
                        String roles = "";

                        for (Long roleID: rolesIDs) {
                            roles += context.getGuild().getRoleById(roleID).getAsMention() + ", ";
                        }

                        BasicEmbed infoEmbed = new BasicEmbed("info");
                        infoEmbed.setDescription("Роли, установленные в качестве приветственных на данный момент"
                                + (roles != "" ? (": " + roles.replaceAll(", $", "")) : " отсутствуют")
                                + ".\nДля установки приветственных ролей используйте `"
                                + Config.BOT_CONFIG.get("prefix") + "welcomer роли` и перечислите все роли через " +
                                "запятую(0 для сброса)");

                        context.sendMessage(infoEmbed).queue();
                    }
                    break;
                case "channel":
                case "канал":
                    if (arguments.length != 1) {
                        Database db = new Database();
                        if (!arguments[1].equals("0")) {
                            try {
                                DBGuild.setWelcomeChannel(Converters.getTextChannel(context.getGuild(),
                                        arguments[1]).getIdLong());

                                db.updateGuild(DBGuild);
                                BasicEmbed successEmbed = new BasicEmbed("success",
                                        "Канал для приветственных сообщений успешно установлен");
                                context.sendMessage(successEmbed).queue();

                            } catch (ChannelNotFoundException e) {
                                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный канал не найден");
                                context.sendMessage(errorEmbed).queue();
                            }
                        } else {
                            DBGuild.setWelcomeChannel(null);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success",
                                    "Канал для приветственных сообщений успешно сброшен");
                            context.sendMessage(successEmbed).queue();
                        }
                    } else {
                        BasicEmbed infoEmbed = new BasicEmbed("info");

                        infoEmbed.setDescription("Канал, установленный для приветственных сообщений на данный момент"
                                + (DBGuild.getWelcomeChannel() != null
                                ? (": " + context.getGuild()
                                .getTextChannelById(DBGuild.getWelcomeChannel()).getAsMention()) : " отсутствует")
                                + ".\nДля установки канала для приветственных сообщений используйте `"
                                + Config.BOT_CONFIG.get("prefix")
                                + "welcomer канал` и укажите необходимый канал(0 для сброса)");

                        context.sendMessage(infoEmbed).queue();
                    }
                    break;
                case "message":
                case "сообщение":
                    if (arguments.length != 1) {
                        Database db = new Database();
                        if (!arguments[1].equals("0")) {
                            DBGuild.setWelcomeMessage(arguments[1]);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success",
                                    "Приветственное сообщение успешно установлено");
                            context.sendMessage(successEmbed).queue();
                        } else {
                            DBGuild.setWelcomeChannel(null);
                            db.updateGuild(DBGuild);
                            BasicEmbed successEmbed = new BasicEmbed("success",
                                    "Приветственное сообщение успешно сброшено");
                            context.sendMessage(successEmbed).queue();
                        }
                    } else {
                        BasicEmbed infoEmbed = new BasicEmbed("info");

                        infoEmbed.setDescription("Приветственное сообщение на данный момент"
                                + (!DBGuild.getWelcomeMessage().equals("")
                                ? ": " + DBGuild.getWelcomeMessage() : " отсутствует")
                                + ".\nДля установки канала для приветственных сообщений используйте `"
                                + Config.BOT_CONFIG.get("prefix") + "welcomer сообщение` и укажите необходимое " +
                                "сообщение(0 для сброса). Доступные в сообщении переменные: {{member.tag}} - имя" +
                                " пользователя, {{member.mention}} - упоминание пользователя, {{guild.memberCount}} - " +
                                "кол-во участников на сервере");

                        context.sendMessage(infoEmbed).queue();
                    }
                    break;
                case "restore":
                case "восстановление":
                    if (arguments.length != 1) {
                        Database db = new Database();
                        boolean toRestoreRoles = arguments[1].equals("1");
                        DBGuild.setRestoreRoles(toRestoreRoles);
                        db.updateGuild(DBGuild);
                        BasicEmbed successEmbed = new BasicEmbed("success");

                        successEmbed.setDescription("Восстановление ролей успешно "
                                + (toRestoreRoles ? "включено" : "отключено"));

                        context.sendMessage(successEmbed).queue();
                    } else {
                        BasicEmbed infoEmbed = new BasicEmbed("info");

                        infoEmbed.setDescription("Приветственное сообщение на данный момент "
                                + (DBGuild.getRestoreRoles() ? "включено" : "отключено")
                                + ".\nДля изменения используйте `"
                                + Config.BOT_CONFIG.get("prefix")
                                + "welcomer восстановление` и укажите необходимое " +
                                "состояние(1 для включения, 0 для отключения).");

                        context.sendMessage(infoEmbed).queue();
                    }
                    break;
            }
        }
    }
}
