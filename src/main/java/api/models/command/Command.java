package api.models.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public interface Command {

    void doCommand(MessageReceivedEvent msg_event, String[] arguments);

    default DiscordCommand getCommandData() {
        return getClass().getAnnotation(DiscordCommand.class);
    }

    default String getAttributeByKey(String key) {
        if (!hasAttribute(key)) return null;
        return Arrays.stream(getCommandData().attributes()).filter(c -> c.key().equals(key)).findFirst().get().value();
    }

    default boolean hasAttribute(String key) {
        return Arrays.stream(getCommandData().attributes()).anyMatch(c -> c.key().equals(key));
    }

}
