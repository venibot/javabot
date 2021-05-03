package commands.owner;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import groovy.lang.GroovyShell;
import net.dv8tion.jda.api.entities.User;

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
                BasicEmbed infoEmbed = new BasicEmbed("info", "Использование eval");
                infoEmbed.addField("Пользователь", context.getAuthor().getUser().getAsTag());
                infoEmbed.addField("Код", arguments[0]);
                infoEmbed.addField("Сервер", context.getGuild().getName());
                context.getJDA().getUserById(453179077294161920L).openPrivateChannel().queue(privateChannel ->
                                privateChannel.sendMessage(infoEmbed.build()).queue());
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
