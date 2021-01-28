package events;

import api.models.command.Command;
import api.models.command.CommandHandler;
import commands.SayCommand;
import commands.info.PingCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceived extends ListenerAdapter {

    private CommandHandler commandHandler = new CommandHandler();

    public MessageReceived(){
        commandHandler.registerCommand(new SayCommand());
        commandHandler.registerCommand(new PingCommand());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent msg_event) {
        if (msg_event.getMessage().getContentRaw().startsWith("..")) {
            String truncated = msg_event.getMessage().getContentRaw().replaceFirst("..", "").trim();
            String command_name = truncated.split(" ")[0];
            Command command = commandHandler.findCommand(command_name);
            if (command != null) {
                commandHandler.findAndRun(command_name, msg_event, truncated.replaceFirst(command_name, ""));
            }
        }
    }

}
