package commands.info;

import java.awt.Color;
import api.models.command.Command;
import api.models.command.CommandHandler;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "help", description = "Помощь по командам бота", aliases = {"хелп"})
public class HelpCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String arguments) {
        EmbedBuilder embedInfo = new EmbedBuilder();
        embedInfo.setTitle("Помощь по командам бота");
        embedInfo.setColor(Color.blue);
        for (Command command: CommandHandler.commands) {
            embedInfo.addField(command.getDescription().name(), command.getDescription().description(), false);
        }
        msg_event.getChannel().sendMessage(embedInfo.build()).queue();
    }

}
