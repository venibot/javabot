package commands.moderation;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.database.Warn;
import api.models.exceptions.MemberNotFoundException;
import api.utils.*;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@DiscordCommand(name = "warn", description = "Выдача предупреждения пользователю", aliases = {"варн", "пред"},
        usage = "<Пользователь> [Время действия] [Причина]", group = "Модерация",
        arguments = 3, permissions = {Permission.KICK_MEMBERS})
public class WarnCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите пользователя, которому вы хотите выдать предупреждение");
            context.sendMessage(errorEmbed).queue();
        } else {
            Member intruder;
            Long endTime = 0L;
            String reason;

            try {
                intruder = Converters.getMember(context.getGuild(), arguments[0]);
            } catch (MemberNotFoundException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный пользователь не найден");
                context.sendMessage(errorEmbed).queue();
                return;
            }

            if (!context.getAuthor().canInteract(intruder)) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "У вас нет прав выдавать предупреждения данному пользователю");
                context.sendMessage(errorEmbed).queue();
                return;
            }

            if (arguments.length > 1) {
                String time = arguments[1];
                String duration = String.join("", time.split("\\D+"));
                String unit = String.join("", time.split("\\d+"));

                if (!duration.equals("") && !unit.equals("")) {
                    for (TimeUnit timeUnit : Config.TIMES.keySet()) {
                        if (Arrays.asList(Config.TIMES.get(timeUnit)).contains(unit)) {

                            endTime = new Date().getTime() + timeUnit.toMillis(Integer.parseInt(duration));
                            reason = arguments.length > 2 ? arguments[2] : "Причина не указана";
                            Database db = new Database();
                            Integer warnID = db.getLastWarnID(context.getGuild().getIdLong()) + 1;

                            db.addWarn(new Warn(
                                    context.getGuild().getIdLong(),
                                    warnID,
                                    context.getAuthor().getIdLong(),
                                    intruder.getIdLong(),
                                    reason,
                                    endTime
                            ));

                            BasicEmbed successEmbed = new BasicEmbed("success");
                            successEmbed.setTitle("Пользователю " + intruder
                                    .getEffectiveName() + " успешно выдан варн с ID " + warnID);

                            if (arguments.length > 2) {
                                successEmbed.addField("Причина выдачи", reason);
                            }

                            successEmbed.addField("Дата истечения действия",
                                    DataFormatter.datetimeToString(DataFormatter.unixToDateTime(endTime)));

                            context.sendMessage(successEmbed).queue();
                            return;
                        }
                    }
                }

                reason = arguments.length > 2 ? arguments[1] + " " + arguments[2] : arguments[1];
                Database db = new Database();
                Integer warnID = db.getLastWarnID(context.getGuild().getIdLong()) + 1;

                db.addWarn(new Warn(
                        context.getGuild().getIdLong(),
                        warnID,
                        context.getAuthor().getIdLong(),
                        intruder.getIdLong(),
                        reason,
                        endTime
                ));

                BasicEmbed successEmbed = new BasicEmbed("success");

                successEmbed.setTitle("Пользователю " + intruder
                        .getEffectiveName() + " успешно выдан варн с ID " + warnID);

                successEmbed.addField("Причина выдачи", reason);
                context.sendMessage(successEmbed).queue();
            } else {
                Database db = new Database();
                Integer warnID = db.getLastWarnID(context.getGuild().getIdLong()) + 1;

                db.addWarn(new Warn(
                        context.getGuild().getIdLong(),
                        warnID,
                        context.getAuthor().getIdLong(),
                        intruder.getIdLong(),
                        "Причина не указана",
                        endTime
                ));

                BasicEmbed successEmbed = new BasicEmbed("success");

                successEmbed.setTitle("Пользователю " + intruder
                        .getEffectiveName() + " успешно выдан варн с ID " + warnID);

                context.sendMessage(successEmbed).queue();
            }
        }
    }
}
