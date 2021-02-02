package commands.info;

import api.models.command.Command;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "ping", description = "Вывод пинга бота", aliases = {"пинг"}, group = "Информация")
public class PingCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        EmbedBuilder pingEmbed = new EmbedBuilder();
        pingEmbed.setTitle("Пинг бота");
        pingEmbed.addField("Пинг до вебсокета", msg_event.getJDA().getGatewayPing() + " мс", false);
        long timeBefore = System.currentTimeMillis();
        msg_event.getMessage().reply(pingEmbed.build()).queue(response -> {
                pingEmbed.addField("Пинг до Discord API", System.currentTimeMillis() - timeBefore + " мс", false);
                response.editMessage(pingEmbed.build()).queue();
            }
        );
    }

}
