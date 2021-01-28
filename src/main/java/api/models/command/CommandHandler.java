package api.models.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class CommandHandler {
    private final Logger logger = LoggerFactory.getLogger("JDA-Command");


    public static Set<Command> commands = new HashSet<>();


    public void registerCommands(Set<Command> commands) {
        CommandHandler.commands.addAll(commands);
    }

    public void registerCommands(Command... commands) {
        Collections.addAll(CommandHandler.commands, commands);
    }

    public void registerCommand(Command command) {
        this.registerCommands(command);
    }

    public void unregisterCommands(Set<Command> commands) {
        CommandHandler.commands.removeAll(commands);
    }

    public void unregisterCommands(Command... commands) {
        CommandHandler.commands.removeAll(Arrays.asList(commands));
    }

    public void unregisterCommand(Command command) {
        this.unregisterCommands(command);
    }

    public Command findCommand(String trigger) {
        return commands.stream().filter(c -> Arrays.asList(c.getDescription().aliases(), c.getDescription().name()).contains(trigger)).findFirst().orElse(null);
    }

    public void doCommand(Command command, MessageReceivedEvent msg_event, String arguments) {
        DiscordCommand cd = command.getDescription();
        if (cd == null) return;
        arguments = arguments.trim();
        if (cd.arguments() > arguments.split("\\s+").length) return;
        try {
            command.doCommand(msg_event, arguments);
        } catch (Exception error) {
            logger.error("Ошибка при выполнении команды " + cd.name() + ". " + error);
            throw new CommandException(error);
        }
    }

    public void findAndRun(String trigger, MessageReceivedEvent msg_event, String arguments) {
        Command command = this.findCommand(trigger);
        if (command == null || command.getDescription() == null) return;
        this.doCommand(command, msg_event, arguments);
    }
}