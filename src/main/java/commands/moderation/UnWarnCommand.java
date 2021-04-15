package commands.moderation;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.database.Warn;
import net.dv8tion.jda.api.Permission;

@DiscordCommand(name = "unwarn", description = "Снять варн с пользователя",
        aliases = {"remwarn", "снятьпред", "снятьварн"},
                usage = "<ID предупреждения>", arguments = 1,
        group = "Модерация", permissions = {Permission.KICK_MEMBERS})
public class UnWarnCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите ID предупреждения, которое необходимо снять");
            context.sendMessage(errorEmbed).queue();
        } else {
            try {
                Integer warnID = Integer.parseInt(arguments[0]);
                Database db = new Database();
                Warn warn = db.getWarnByID(context.getGuild().getIdLong(), warnID);

                if (warn != null) {
                    if (warn.getIntruderID() != context.getAuthor().getIdLong()) {
                        db.deleteWarn(context.getGuild().getIdLong(), warnID);
                        context.getMessage().addReaction("✅").queue();
                    } else {
                        BasicEmbed errorEmbed = new BasicEmbed("error", "Я конечно всё понимаю, "
                                + "но нельзя снимать варны с себя");
                        context.sendMessage(errorEmbed).queue();
                    }
                } else {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "Неверно указан ID предупреждения. " +
                            "Предупреждения с таким ID не существует.");
                    context.sendMessage(errorEmbed).queue();
                }
            } catch (NumberFormatException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Неверно указан ID предупреждения. Укажите число, а не буквы.");
                context.sendMessage(errorEmbed).queue();
            }
        }
    }
}
