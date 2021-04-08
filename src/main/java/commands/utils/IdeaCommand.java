package commands.utils;

import api.BasicEmbed;
import api.SupportServer;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "idea", description = "Отправка идеи для развития бота", usage = "<Текст идеи>", aliases = {"идея"},
                group = "Утилиты", arguments = 1)
public class IdeaCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите текст идеи");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        } else {
            SupportServer supportServer = new SupportServer(msg_event.getJDA());
            supportServer.sendIdea(arguments[0], msg_event.getMember());
            BasicEmbed successEmbed = new BasicEmbed("success", "Ваша идея успешно отправлена",
                    "Спасибо за вклад в развитие бота");
            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
        }
    }
}
