package commands.settings;

import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.database.User;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

@DiscordCommand(name = "about", description = "Установка описания о себе(в команде user)", group = "Настройки", aliases = "осебе", usage = "<Новый текст>", arguments = 1)
public class AboutCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (arguments.length > 0) {
            try {
                Database db = new Database();
                User DBUser = db.getUserByID(msg_event.getMember().getIdLong(), msg_event.getGuild().getIdLong());
                if (!DBUser.getAbout().equals(String.join(" ", arguments))) {
                    DBUser.setAbout(String.join(" ", arguments));
                    db.updateUser(DBUser);
                    msg_event.getChannel().sendMessage(new EmbedBuilder().setDescription("Вы успешно сменили описание о себе!").setColor(Color.green).build()).queue();
                } else {
                    msg_event.getChannel().sendMessage(new EmbedBuilder().setDescription("О, а научите и меня менять текст на тот же самый").setColor(Color.red).build()).queue();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            msg_event.getChannel().sendMessage(new EmbedBuilder().setTitle("Укажите новый текст!").setColor(Color.red).build()).queue();
        }
    }

}