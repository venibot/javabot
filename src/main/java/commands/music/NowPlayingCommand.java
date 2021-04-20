package commands.music;

import api.BasicEmbed;
import api.lavalink.MusicManager;
import api.lavalink.PlayerManager;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.DataFormatter;
import api.utils.Functions;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.concurrent.BlockingQueue;

@DiscordCommand(name = "now-playing", description = "Просмотр информации о текущем треке", aliases = {"np", "nowplaying", "сейчас"},
        group = "Музыка")
public class NowPlayingCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (Functions.checkMusicCommandAuthor(context)) {
            MusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
            AudioTrack track = musicManager.audioPlayer.getPlayingTrack();
            if (track != null) {
                BasicEmbed infoEmbed = new BasicEmbed("info");
                infoEmbed.setTitle("Играющий в данный момент трек");
                infoEmbed.addField("Название трека", track.getInfo().title);
                infoEmbed.addField("Автор трека", track.getInfo().author);
                infoEmbed.addField("Продолжительность трека",
                        track.getInfo().isStream ? "∞" : DataFormatter.getTrackLength(track.getDuration()));
                infoEmbed.addField("Зациклен", musicManager.trackScheduler.looping ? "Да" : "Нет");
                infoEmbed.addField("Является стримом", track.getInfo().isStream ? "Да" : "Нет");
                infoEmbed.addField("Ссылка на трек", "[Перейти](" + track.getInfo().uri + ")");
                context.sendMessage(infoEmbed).queue();
            } else {
                BasicEmbed errorEmbed = new BasicEmbed("error", "В данный момент ничего не играет");
                context.sendMessage(errorEmbed).queue();
            }
        }
    }

}
