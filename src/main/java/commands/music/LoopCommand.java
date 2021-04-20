package commands.music;

import api.BasicEmbed;
import api.lavalink.MusicManager;
import api.lavalink.PlayerManager;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.Functions;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

@DiscordCommand(name = "loop", description = "Зацикливание трека", aliases = {"repeat", "повтор"},
        group = "Музыка")
public class LoopCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (Functions.checkMusicCommandAuthor(context)) {
            MusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
            AudioTrack track = musicManager.audioPlayer.getPlayingTrack();
            if (track != null) {
                boolean newLoopStatus = !musicManager.trackScheduler.looping;
                musicManager.trackScheduler.looping = newLoopStatus;
                BasicEmbed successEmbed = new BasicEmbed("success", "Зацикливание успешно "
                        + (newLoopStatus ? "включено" : "отключено"));
                context.sendMessage(successEmbed).queue();
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error", "В данный момент ничего не играет");
                context.sendMessage(errorEmbed).queue();
            }
        }
    }

}
