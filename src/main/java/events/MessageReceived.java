package events;

import api.models.command.Command;
import api.models.command.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceived extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent msg_event) {
        if (msg_event.getMessage().getContentRaw().startsWith("..") && !msg_event.getAuthor().isBot()) {
            String truncated = msg_event.getMessage().getContentRaw().replaceFirst("..", "").trim();
            String command_name = truncated.split(" ")[0];
            Command command = CommandHandler.findCommand(command_name);
            if (command != null) {
                CommandHandler.findAndRun(command_name, msg_event, truncated.replaceFirst(command_name, ""));
            }
        }
    }

}
