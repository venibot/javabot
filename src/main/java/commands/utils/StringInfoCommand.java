package commands.utils;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@DiscordCommand(name = "stringinfo", description = "Получение информации о строке", aliases = {"string", "si", "строка"}, group = "Утилиты",
                usage = "<Строка>", arguments = 1, hidden = true)
public class StringInfoCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setDescription("Укажите строку, о которой хотите получить информацию");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        } else {
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setDescription("Информация о строке " + arguments[0]);
            infoEmbed.addField("Длина строки", String.valueOf(arguments[0].length()));
            infoEmbed.addField("Закодированная в base64", Base64.getEncoder().encode(arguments[0].getBytes(StandardCharsets.UTF_8)).toString());
            msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
        }
    }
}
