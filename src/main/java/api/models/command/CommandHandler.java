package api.models.command;

import api.BasicEmbed;
import api.SupportServer;
import api.utils.DataFormatter;
import api.utils.PermissionsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger("JDA-Command");

    public static Set<Command> commands = new HashSet<>();

    public static void registerCommands(Command... commands) {
        Collections.addAll(CommandHandler.commands, commands);
    }

    public static void registerCommand(Command command) {
        CommandHandler.registerCommands(command);
    }

    public static void unregisterCommands(Command... commands) {
        CommandHandler.commands.removeAll(Arrays.asList(commands));
    }

    public static void unregisterCommand(Command command) {
        CommandHandler.unregisterCommands(command);
    }

    public static Command findCommand(String trigger) {
        return commands.stream().filter(c -> Arrays.asList(c.getCommandData().aliases()).
                contains(trigger) || c.getCommandData().name().equals(trigger)).findFirst().orElse(null);
    }

    public static void doCommand(Command command, CommandContext context, String args) throws CommandException {
        DiscordCommand cd = command.getCommandData();

        if (cd == null) return;

        if (PermissionsHandler.handleAccessLevel(context, cd.accessLevel(), context.getAuthor().getUser())) {
            if (context.getAuthor().hasPermission(cd.permissions())) {
                String[] arguments;
                if (args.equals("")) {
                    arguments = new String[0];
                } else {

                    if (cd.arguments() == 0 || cd.arguments() == 1) {
                        arguments = new String[1];
                        arguments[0] = args.replace("^[ ]*", "").trim();
                    } else {
                        arguments = args.replace("^[ ]*", "").trim().split(" ", cd.arguments());
                    }
                }

                try {
                    command.doCommand(context, arguments);
                } catch (Exception error) {
                    throw new CommandException(error);
                }
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error");
                errorEmbed.setTitle("У вас недостаточно прав для выполнения данной команды");
                errorEmbed.setDescription("Необходимые права: " + DataFormatter.getMissingPermissions(cd.permissions()));

                context.getChannel().sendMessage(errorEmbed.build()).queue();
            }
        } else {
            BasicEmbed errorEmbed = new BasicEmbed("error", "У вас отсутствует необходимый уровень доступа. Для выполнения данной необходим "
                    + cd.accessLevel() + " уровень доступа(" + DataFormatter.accessLevelToString(cd.accessLevel()) + ")");

            context.getChannel().sendMessage(errorEmbed.build()).queue();
        }
    }

    public static void findAndRun(String trigger, CommandContext context, String arguments) {
        Command command = CommandHandler.findCommand(trigger);
        context.setCommand(command);

        if (command == null || command.getCommandData() == null) return;
        try {
            CommandHandler.doCommand(command, context, arguments);
        } catch (CommandException e) {
            SupportServer supportServer = new SupportServer(context.getJDA());
            if (!supportServer.isDeveloper(context.getAuthor().getUser())) {
                supportServer.sendCommandError(e, context, false);
                BasicEmbed errorEmbed = new BasicEmbed("error", "При выполнении команды произошла ошибка :<. "
                        + "Информация об ошибке уже отправлена разработчикам");
                context.sendMessage(errorEmbed).queue();
            } else {
                supportServer.sendCommandError(e, context, true);
            }
        }
    }
}