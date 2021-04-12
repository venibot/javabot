package commands.utils;

import api.BasicEmbed;
import api.SupportServer;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;

@DiscordCommand(name = "idea", description = "Отправка идеи для развития бота", usage = "<Текст идеи>", aliases = {"идея"},
                group = "Утилиты", arguments = 1)
public class IdeaCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите текст идеи");
            context.sendMessage(errorEmbed).queue();
        } else {
            SupportServer supportServer = new SupportServer(context.getJDA());
            supportServer.sendIdea(arguments[0], context.getAuthor());
            BasicEmbed successEmbed = new BasicEmbed("success", "Ваша идея успешно отправлена",
                    "Спасибо за вклад в развитие бота");
            context.sendMessage(successEmbed).queue();
        }
    }
}
