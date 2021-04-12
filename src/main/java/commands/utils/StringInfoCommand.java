package commands.utils;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@DiscordCommand(name = "stringinfo", description = "Получение информации о строке",
        aliases = {"string", "si", "строка"}, group = "Утилиты",
                usage = "<Строка>", arguments = 1, hidden = true)
public class StringInfoCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите строку, о которой хотите получить информацию");
            context.sendMessage(errorEmbed).queue();
        } else {
            BasicEmbed infoEmbed = new BasicEmbed("info", "Информация о строке " + arguments[0]);
            infoEmbed.addField("Длина строки", String.valueOf(arguments[0].length()));

            infoEmbed.addField("Закодированная в base64", Base64.getEncoder()
                    .encode(arguments[0].getBytes(StandardCharsets.UTF_8)).toString());

            context.sendMessage(infoEmbed).queue();
        }
    }
}
