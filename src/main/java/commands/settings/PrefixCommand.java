package commands.settings;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.database.Guild;
import net.dv8tion.jda.api.Permission;

@DiscordCommand(name = "prefix", description = "Установка префикса для сервера", aliases = {"префикс"},
        usage = "<Новый префикс>", group = "Настройки", arguments = 1, permissions = {Permission.MANAGE_SERVER})
public class PrefixCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            String prefix = context.getDatabaseGuild().getPrefix();
            BasicEmbed infoEmbed = new BasicEmbed("info");

            infoEmbed.setDescription("Мой текущий префикс на этом сервере - `" + prefix
                    + "` Для установки нового префикса воспользуйтесь командой "
                    + prefix + "префикс " + new PrefixCommand().getCommandData().usage());

            context.sendMessage(infoEmbed).queue();
        } else {
            if (arguments[0].contains(" ")) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Префикс не может содержать пробелов");
                context.sendMessage(errorEmbed).queue();
                return;
            } if (arguments[0].length() > 6) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Префикс не может быть длиннее 6 символов");
                context.sendMessage(errorEmbed).queue();
                return;
            } if (arguments[0].equals(context.getDatabaseGuild().getPrefix())) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "А смысл менять префикс на тот же самый?");
                context.sendMessage(errorEmbed).queue();
                return;
            }

            Database db = new Database();
            Guild guild = context.getDatabaseGuild();
            guild.setPrefix(arguments[0]);
            db.updateGuild(guild);
            BasicEmbed successEmbed = new BasicEmbed("success",
                    "Префикс успешно изменён на `" + guild.getPrefix() + "`");
            context.sendMessage(successEmbed).queue();
        }
    }
}
