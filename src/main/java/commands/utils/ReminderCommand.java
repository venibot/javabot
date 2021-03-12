package commands.utils;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.database.Reminder;
import api.utils.Config;
import api.utils.DataFormatter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@DiscordCommand(name = "reminder", description = "Напоминания", aliases = {"напоминания", "напоминание"},
        group = "Утилиты", usage = "<Действие> [Аргументы]", arguments = 3)
public class ReminderCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments)  {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setTitle("Не указано действие");
            errorEmbed.setDescription("Укажите одно из доступных действий:\nсписок\nсоздать");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        } else {
            Database db = new Database();
            switch (arguments[0]) {
                case "list":
                case "лист":
                case "список":
                    List<Reminder> reminders = db.getUserReminders(msg_event.getAuthor().getIdLong(),
                            msg_event.getGuild().getIdLong());
                    BasicEmbed reminderList = new BasicEmbed("info");
                    reminderList.setTitle("Ваши напоминания");
                    if (reminders != null) {
                        for (Reminder reminder: reminders) {
                            reminderList.addField(reminder.getText(),
                                    DataFormatter.datetimeToString(DataFormatter
                                            .unixToDateTime(reminder.getEndTime())));
                        }
                    } else {
                        reminderList.setDescription("Напоминания отсутствуют");
                    }
                    msg_event.getChannel().sendMessage(reminderList.build()).queue();
                    break;
                case "create":
                case "new":
                case "создать":
                    if (arguments.length < 3) {
                        BasicEmbed errorEmbed = new BasicEmbed("error");
                        errorEmbed.setDescription("Укажите время и текст напоминания");
                        msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
                    } else {
                        String time = arguments[1];
                        String duration = String.join("", time.split("\\D+"));
                        String unit = String.join("", time.split("\\d+"));
                        if (!duration.equals("") && !unit.equals("")) {
                            for (TimeUnit timeUnit: Config.TIMES.keySet()) {
                                if (Arrays.asList(Config.TIMES.get(timeUnit)).contains(unit)) {
                                    Long endTime = new Date().getTime() + timeUnit.toMillis(Integer.parseInt(duration));
                                    Reminder reminder = new Reminder(
                                            db.getLastReminderID(),
                                            msg_event.getAuthor().getIdLong(),
                                            msg_event.getGuild().getIdLong(),
                                            arguments[2],
                                            endTime);
                                    db.addReminder(reminder);
                                    msg_event.getMessage().addReaction("✅").queue();
                                    return;
                                }
                            }
                            BasicEmbed errorEmbed = new BasicEmbed("error");
                            errorEmbed.setDescription("Укажите время и текст напоминания");
                            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
                        } else {
                            BasicEmbed errorEmbed = new BasicEmbed("error");
                            errorEmbed.setDescription("Укажите время и текст напоминания");
                            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
                        }
                    }
                    break;
            }
        }
    }

}
