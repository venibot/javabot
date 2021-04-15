package commands.owner;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import groovy.lang.GroovyShell;

@DiscordCommand(name = "eval", description = "Выполнение кода, а ты что думал?", aliases = {"eval"}, usage = "<Код>",
                group = "Владельцу", arguments = 1, hidden = true, accessLevel = 4)
public class EvalCommand implements Command {

    private final GroovyShell engine = new GroovyShell();

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Может хотя бы код укажешь?");
            context.sendMessage(errorEmbed).queue();
        } else {
            try {
                engine.setProperty("arguments", arguments);
                engine.setProperty("ctx", context);
                engine.setProperty("bot", context.getJDA());
                engine.setProperty("dbGuild", context.getDatabase());
                engine.setProperty("guild", context.getGuild());
                engine.setProperty("author", context.getAuthor());
                engine.setProperty("db", context.getDatabase());

                String script = arguments[0];
                Object out = engine.evaluate(script);

                if (out != null) {
                    context.sendMessage(out.toString()).queue();
                } else {
                    context.getMessage().addReaction("✅").queue();
                }
            } catch (Exception e) {
                context.sendMessage(e.getMessage()).queue();
            }
        }
    }
}
