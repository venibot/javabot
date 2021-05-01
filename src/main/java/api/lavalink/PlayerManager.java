package api.lavalink;

import api.BasicEmbed;
import api.utils.DataFormatter;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private static PlayerManager INSTANCE;

    private final Map<Long, MusicManager> musicManagers;
    private final AudioPlayerManager playerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();


        AudioSourceManagers.registerRemoteSources(this.playerManager);
        AudioSourceManagers.registerLocalSource(this.playerManager);
    }

    public MusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildID) -> {
            final MusicManager musicManager = new MusicManager(this.playerManager);
            guild.getAudioManager().setSendingHandler(musicManager.getPlayerSendHandler());
            return musicManager;
        });
    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    public void loadAndPlay(AudioTrackInformation trackInformation, String url) {
        final MusicManager musicManager = this.getMusicManager(trackInformation.guild);

        this.playerManager.loadItemOrdered(musicManager, url, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                audioTrack.setUserData(trackInformation);
                audioTrack.setPosition(musicManager.trackScheduler.queue.size() + 1);
                musicManager.trackScheduler.queue(audioTrack);

                if (PlayerManager.getInstance().getMusicManager(trackInformation.guild).trackScheduler.queue.size() != 0) {
                    BasicEmbed successEmbed = new BasicEmbed("success");
                    successEmbed.setTitle("Трек успешно добавлен в очередь");
                    successEmbed.setDescription("[" + audioTrack.getInfo().title + "](" + audioTrack.getInfo().uri + ")\nПродолжительность: "
                            + (audioTrack.getInfo().isStream ? "∞" : DataFormatter.getTrackLength(audioTrack.getInfo().length)));
                    trackInformation.connectedChannel.sendMessage(successEmbed.build()).queue();
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                if (audioPlaylist.isSearchResult()) {
                    trackLoaded(audioPlaylist.getTracks().get(0));
                } else {
                    for (AudioTrack track : audioPlaylist.getTracks()) {
                        track.setUserData(trackInformation);
                        track.setPosition(musicManager.trackScheduler.queue.size() + 1);
                        musicManager.trackScheduler.queue(track);
                    }

                    BasicEmbed successEmbed = new BasicEmbed("success");
                    successEmbed.setTitle("Плейлист успешно добавлен в очередь");
                    successEmbed.setDescription(audioPlaylist.getName() + "\nСостоит из "
                            + audioPlaylist.getTracks().size() + " треков");
                    trackInformation.connectedChannel.sendMessage(successEmbed.build()).queue();
                }
            }

            @Override
            public void noMatches() {
                BasicEmbed successEmbed = new BasicEmbed("error");
                successEmbed.setTitle("По вашему запросу ничего не найден");
                successEmbed.setDescription("Укажите другой запрос");
                trackInformation.connectedChannel.sendMessage(successEmbed.build()).queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                BasicEmbed successEmbed = new BasicEmbed("error");
                successEmbed.setTitle("Произошла непредвиденная ошибка");
                successEmbed.setDescription("При загрузке трека произошла непредвиденная ошибка. Повторите попытку позже. "
                        + "Если проблема повторяется - обратитесь на сервер поддержки");
                trackInformation.connectedChannel.sendMessage(successEmbed.build()).queue();
                e.printStackTrace();
            }
        });
    }

}
