package commands.utils;

import api.BasicEmbed;
import api.cache.CurrencyCache;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "currency", description = "Перевод из валюты в валюту", aliases = {"cur", "curr", "валюта"},
        group = "Утилиты", hidden = true)
public class CurrencyCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (CurrencyCache.isRelevant()) {
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setTitle("Текущий курс валлюты " + arguments[0] + " составляет " +
                    CurrencyCache.getCache().get(arguments[0]) + " рублей");
            msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
        } else {
            CurrencyCache.setCache();
            System.out.println(CurrencyCache.getCache().keySet());
            doCommand(msg_event, arguments);
        }
    }

}
