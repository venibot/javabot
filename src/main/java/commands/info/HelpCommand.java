package commands.info;

import api.models.command.Command;
import api.models.command.CommandHandler;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "help", description = "Помощь по командам бота", aliases = {"хелп"})
public class HelpCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String arguments) {
        for (Command command: CommandHandler.commands) {
            msg_event.getChannel().sendMessage(command.getDescription().name()).queue();
        }
    }

}
