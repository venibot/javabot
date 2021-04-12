package commands.utils;

import api.BasicEmbed;
import api.SupportServer;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;

@DiscordCommand(name = "bug", description = "Отправка сообщения о баге в боте", usage = "<Описание бага>", aliases = {"баг"},
                group = "Утилиты", arguments = 1)
public class BugCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите описание бага");
            context.sendMessage(errorEmbed).queue();
        } else {
            SupportServer supportServer = new SupportServer(context.getJDA());
            supportServer.sendBug(arguments[0], context.getAuthor());
            BasicEmbed successEmbed = new BasicEmbed("success", "Ваше сообщение о баге успешно отправлено",
                    "Спасибо за вклад в развитие бота");
            context.sendMessage(successEmbed).queue();
        }
    }
}
