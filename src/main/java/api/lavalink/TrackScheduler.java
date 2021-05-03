package api.lavalink;

import api.BasicEmbed;
import api.utils.DataFormatter;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {

    public final AudioPlayer player;
    public final BlockingQueue<AudioTrack> queue;
    public boolean looping = false;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public void nextTrack() {
        this.player.startTrack(this.queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            if (this.queue.size() != 0) {
                if (this.looping) {
                    this.player.startTrack(track.makeClone(), false);
                    return;
                }
                nextTrack();
            } else {
                AudioTrackInformation trackInfo = track.getUserData(AudioTrackInformation.class);
                TextChannel channel = trackInfo.connectedChannel;
                BasicEmbed infoEmbed = new BasicEmbed("info", "Песни в очереди закончилась. Покидаю канал");
                channel.sendMessage(infoEmbed.build()).queue();
                trackInfo.guild.getAudioManager().closeAudioConnection();
            }
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioTrackInformation trackInfo = track.getUserData(AudioTrackInformation.class);
        TextChannel channel = trackInfo.connectedChannel;
        BasicEmbed infoEmbed = new BasicEmbed("info", "Новый проигрываемый трек",
                "Теперь проигрывается трек [" + track.getInfo().title + "](" + track.getInfo().uri + ")"
                + "\nПродолжительность: "
                + (track.getInfo().isStream ? "∞" : DataFormatter.getTrackLength(track.getInfo().length)) + "\n" +
                "Трек добавил: " + trackInfo.adder.getAsMention());
        channel.sendMessage(infoEmbed.build()).queue();
    }
}
