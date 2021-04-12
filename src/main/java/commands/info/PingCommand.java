package commands.info;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;

@DiscordCommand(name = "ping", description = "Вывод пинга бота", aliases = {"пинг"}, group = "Информация")
public class PingCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        BasicEmbed pingEmbed = new BasicEmbed("info");
        pingEmbed.setTitle("Пинг бота");
        pingEmbed.addField("Пинг до вебсокета", context.getJDA().getGatewayPing() + " мс", false);
        long timeBefore = System.currentTimeMillis();

        context.getMessage().reply(pingEmbed.build()).queue(response -> {
                pingEmbed.addField("Пинг до Discord API", System.currentTimeMillis() - timeBefore +
                        " мс", false);

                response.editMessage(pingEmbed.build()).queue();
            }
        );
    }
}
