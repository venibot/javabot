package commands.info;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.exceptions.UserNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.entities.User;

@DiscordCommand(name = "avatar", description = "Получить аватарку пользователя", aliases = {"аватар", "ава"}, usage = "[пользователь]",
        group = "Информационные", arguments = 1)
public class AvatarCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        User user;
        if (arguments.length == 0) {
            user = context.getAuthor().getUser();
        } else {
            try {
                user = Converters.getUser(context.getJDA(), arguments[0]);
            } catch (UserNotFoundException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный пользователь не найден");
                context.sendMessage(errorEmbed).queue();
                return;
            }
        }
        BasicEmbed infoEmbed = new BasicEmbed("info");
        infoEmbed.setTitle("Аватарка пользователя " + user.getAsTag());
        infoEmbed.setImage(user.getEffectiveAvatarUrl());
        context.sendMessage(infoEmbed).queue();
    }

}
