package commands;

import api.models.command.Command;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "say", aliases = {"сказать", "скажи"}, arguments = 1, group = "Утилиты")
public class SayCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments){
        msg_event.getMessage().delete().queue();
        msg_event.getChannel().sendMessage(String.join(" ", arguments)).queue();
    }

}
