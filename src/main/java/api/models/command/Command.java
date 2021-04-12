package api.models.command;

public interface Command {

    void doCommand(CommandContext context, String[] arguments);

    default DiscordCommand getCommandData() {
        return getClass().getAnnotation(DiscordCommand.class);
    }
}
