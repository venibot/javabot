package commands.utils;

import api.BasicEmbed;
import api.cache.CurrencyCache;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;

@DiscordCommand(name = "currency", description = "Перевод из валюты в валюту", aliases = {"cur", "curr", "валюта"},
        group = "Утилиты", hidden = true)
public class Currency implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        if (CurrencyCache.isRelevant()) {
            BasicEmbed infoEmbed = new BasicEmbed("info");

            infoEmbed.setTitle("Текущий курс валлюты " + arguments[0] + " составляет " +
                    CurrencyCache.getCache().get(arguments[0]) + " рублей");

            context.sendMessage(infoEmbed).queue();
        } else {
            CurrencyCache.setCache();
            System.out.println(CurrencyCache.getCache().keySet());
            doCommand(context, arguments);
        }
    }
}
