package commands.music;

import api.BasicEmbed;
import api.lavalink.MusicManager;
import api.lavalink.PlayerManager;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.Functions;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.NoSuchElementException;

@DiscordCommand(name = "skip", description = "Пропуск текущего играющего трека", aliases = {"скип"}, group = "Музыка")
public class SkipCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (Functions.checkMusicCommandAuthor(context)) {
            MusicManager musicManager =  PlayerManager.getInstance().getMusicManager(context.getGuild());
            if (musicManager.audioPlayer.getPlayingTrack() != null) {
                musicManager.trackScheduler.nextTrack();
                try {
                    BasicEmbed successEmbed = new BasicEmbed("success", "Текущий трек успешно пропущен. "
                            + "Теперь играет трек " + musicManager.audioPlayer.getPlayingTrack().getInfo().title);
                    context.sendMessage(successEmbed).queue();
                } catch (NoSuchElementException e) {
                    BasicEmbed successEmbed = new BasicEmbed("success", "Текущий трек успешно пропущен. "
                            + "Очередь сервера пуста, покидаю канал");
                    context.sendMessage(successEmbed).queue();
                    musicManager.trackScheduler.queue.clear();
                    context.getGuild().getAudioManager().closeAudioConnection();
                }
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error", "В данный момент никакой трек не проигрывается");
                context.sendMessage(errorEmbed).queue();
            }
        }
    }

}
