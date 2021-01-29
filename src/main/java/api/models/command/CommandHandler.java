package api.models.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger("JDA-Command");


    public static Set<Command> commands = new HashSet<>();


    public static void registerCommands(Set<Command> commands) {
        CommandHandler.commands.addAll(commands);
    }

    public static void registerCommands(Command... commands) {
        Collections.addAll(CommandHandler.commands, commands);
    }

    public static void registerCommand(Command command) {
        CommandHandler.registerCommands(command);
    }

    public static void unregisterCommands(Set<Command> commands) {
        CommandHandler.commands.removeAll(commands);
    }

    public static void unregisterCommands(Command... commands) {
        CommandHandler.commands.removeAll(Arrays.asList(commands));
    }

    public static void unregisterCommand(Command command) {
        CommandHandler.unregisterCommands(command);
    }

    public static Command findCommand(String trigger) {
        return commands.stream().filter(c -> Arrays.asList(c.getDescription().aliases()).contains(trigger) || c.getDescription().name().equals(trigger)).findFirst().orElse(null);
    }

    public static void doCommand(Command command, MessageReceivedEvent msg_event, String arguments) {
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

    public static void findAndRun(String trigger, MessageReceivedEvent msg_event, String arguments) {
        Command command = CommandHandler.findCommand(trigger);
        if (command == null || command.getDescription() == null) return;
        CommandHandler.doCommand(command, msg_event, arguments);
    }
}