package workers;

import api.Database;
import api.models.database.Reminder;
import api.models.workers.Worker;
import api.models.workers.WorkerInfo;
import api.utils.Config;
import net.dv8tion.jda.api.entities.User;

import java.util.Date;

@WorkerInfo(name = "Reminder", description = "Проверка напоминаний")
public class ReminderWorker implements Worker {

    public void execute() {
        Database db = new Database();
        for (Reminder reminder: db.getReminders()) {
            if (reminder.getEndTime() <= new Date().getTime()) {
                User user = Config.BOT.getUserById(reminder.getUserID());
                if (user != null) {
                    user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("🎗 **Напоминание**" + "\n" + reminder.getText()).queue());
                    db.deleteReminder(reminder.getID());
                }
            }
        }
    }

}
