package commands.moderation;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "unwarn", description = "Снять варн с пользователя", aliases = {"remwarn", "снятьпред", "снятьварн"},
                usage = "<ID предупреждения>", arguments = 1, group = "Модерация", permissions = {Permission.KICK_MEMBERS})
public class UnWarnCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setDescription("Укажите ID предупреждения, которое необходимо снять");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        } else {
            try {
                Integer warnID = Integer.parseInt(arguments[0]);
                Database db = new Database();
                if (db.getWarnByID(msg_event.getGuild().getIdLong(), warnID) != null) {
                    db.deleteWarn(msg_event.getGuild().getIdLong(), warnID);
                    msg_event.getMessage().addReaction("✅").queue();
                } else {
                    BasicEmbed errorEmbed = new BasicEmbed("error");
                    errorEmbed.setDescription("Неверно указан ID предупреждения. Предупреждения с таким ID не существует.");
                    msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
                }
            } catch (NumberFormatException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error");
                errorEmbed.setDescription("Неверно указан ID предупреждения. Укажите число, а не буквы.");
                msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
            }
        }
    }
}
