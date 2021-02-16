package commands;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "say", aliases = {"сказать", "скажи"}, arguments = 1, group = "Утилиты")
public class SayCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments){
        if (arguments.length != 0) {
            msg_event.getMessage().delete().queue();
            msg_event.getChannel().sendMessage(String.join(" ", arguments)).queue();
        } else {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setDescription("О, а можете меня научить ничего не говорить по команде?");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        }
    }

}
