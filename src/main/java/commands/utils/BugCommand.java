package commands.utils;

import api.BasicEmbed;
import api.SupportServer;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "bug", description = "Отправка сообщения о баге в боте", usage = "<Описание бага>", aliases = {"баг"},
                group = "Утилиты", arguments = 1)
public class BugCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите описание бага");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        } else {
            SupportServer supportServer = new SupportServer(msg_event.getJDA());
            supportServer.sendBug(arguments[0], msg_event.getMember());
            BasicEmbed successEmbed = new BasicEmbed("success", "Ваше сообщение о баге успешно отправлено",
                    "Спасибо за вклад в развитие бота");
            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
        }
    }
}
