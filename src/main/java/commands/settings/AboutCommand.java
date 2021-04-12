package commands.settings;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.database.User;

@DiscordCommand(name = "about", description = "Установка описания о себе(в команде user)", group = "Настройки",
        aliases = "осебе", usage = "<Новый текст>", arguments = 1)
public class AboutCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        if (arguments.length > 0) {
            Database db = new Database();
            User DBUser = db.getUserByID(context.getAuthor().getIdLong(), context.getGuild().getIdLong());

            if (!DBUser.getAbout().equals(String.join(" ", arguments))) {
                DBUser.setAbout(String.join(" ", arguments));
                db.updateUser(DBUser);

                context.sendMessage(new BasicEmbed("success", "Вы успешно сменили описание о себе!")).queue();
            } else {
                context.sendMessage(new BasicEmbed("error", "О, а научите и меня менять текст на тот же самый"))
                        .queue();
            }
        } else {
            context.sendMessage(new BasicEmbed("error").setTitle("Укажите новый текст!")).queue();
        }
    }
}
