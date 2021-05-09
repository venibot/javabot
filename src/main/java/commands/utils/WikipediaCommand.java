package commands.utils;

import api.APIs.Wikipedia;
import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;

import java.io.IOException;

@DiscordCommand(name = "wikipedia", description = "Полученние информации из Википедии", aliases = {"wiki", "википедия", "вики"},
        usage = "<Запрос>", group = "Утилиты", arguments = 1, hidden = true, accessLevel = 1)
public class WikipediaCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите запрос");
            context.sendMessage(errorEmbed).queue();
        } else {
            String query = arguments[0];
            try {
                System.out.println(new Wikipedia().findByQuery(query));
                context.sendMessage(new Wikipedia().findByQuery(query).substring(1, 1999)).queue();
            } catch (IOException e) {
                e.printStackTrace();
                BasicEmbed errorEmbed = new BasicEmbed("error", "При поиске произошла ошибка. Повторите попытку позже."
                        + " Если ошибка будет повторяться, обратитесь на сервер поддержки(ссылка указана в "
                        + "`" + context.getDatabaseGuild().getPrefix() + "хелп`");
                context.sendMessage(errorEmbed).queue();
            }
        }
    }
}
