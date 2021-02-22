package api.models.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public interface Command {

    void doCommand(MessageReceivedEvent msg_event, String[] arguments);

    default DiscordCommand getCommandData() {
        return getClass().getAnnotation(DiscordCommand.class);
    }

}
