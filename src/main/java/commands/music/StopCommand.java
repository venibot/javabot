package commands.music;

import api.BasicEmbed;
import api.lavalink.PlayerManager;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.Functions;

@DiscordCommand(name = "stop", description = "Приостановка воспроизведения музыки", aliases = {"стоп"}, group = "Музыка")
public class StopCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (Functions.checkMusicCommandAuthor(context)) {
            PlayerManager.getInstance().getMusicManager(context.getGuild()).trackScheduler.player.setPaused(true);
            BasicEmbed successEmbed = new BasicEmbed("success", "Воспроизведение музыки успешно приостановлено. "
                    + "Для продолжения проигрывания воспользуйтесь командой " + context.getUsedPrefix() + "play");
            context.sendMessage(successEmbed).queue();
        }
    }

}
