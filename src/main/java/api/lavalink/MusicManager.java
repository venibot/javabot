package api.lavalink;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class MusicManager {

    public final AudioPlayer audioPlayer;
    public final TrackScheduler trackScheduler;
    private final AudioPlayerSendHandler playerSendHandler;

    public MusicManager(AudioPlayerManager playerManager) {
        this.audioPlayer = playerManager.createPlayer();
        this.trackScheduler = new TrackScheduler(this.audioPlayer);
        this.audioPlayer.addListener(this.trackScheduler);
        this.playerSendHandler = new AudioPlayerSendHandler(this.audioPlayer);
    }

    public AudioPlayerSendHandler getPlayerSendHandler() {
        return this.playerSendHandler;
    }

}
