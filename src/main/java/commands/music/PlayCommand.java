package commands.music;

import api.BasicEmbed;
import api.lavalink.AudioTrackInformation;
import api.lavalink.MusicManager;
import api.lavalink.PlayerManager;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.Functions;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

@DiscordCommand(name = "play", description = "Проигрывание музыки", aliases = {"плей"}, usage = "[Название трека]",
        group = "Музыка", arguments = 1)
public class PlayCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (Functions.checkMusicCommandAuthor(context)) {
            if (arguments.length == 0) {
                MusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
                if (musicManager.trackScheduler.player.isPaused()) {
                    AudioTrack track = musicManager.trackScheduler.player.getPlayingTrack();
                    musicManager.trackScheduler.player.setPaused(false);
                    BasicEmbed successEmbed = new BasicEmbed("success", "Воспроизведение успешно продолжено с трека "
                            + track.getInfo().title);
                    context.sendMessage(successEmbed).queue();
                } else {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "Очередь сервера пуста",
                            "Для продолжения проигрывания добавьте треки в очередь");
                    context.sendMessage(errorEmbed).queue();
                }
            } else {
                String link;
                if (isUrl(arguments[0])) {
                    link = arguments[0];
                } else {
                    link = "ytsearch:" + arguments[0];
                }
                AudioTrackInformation trackInformation = new AudioTrackInformation();
                trackInformation.guild = context.getGuild();
                trackInformation.adder = context.getAuthor().getUser();
                trackInformation.connectedChannel = context.getMessage().getTextChannel();
                trackInformation.voiceChannel = context.getGuild().getAudioManager().getConnectedChannel();
                PlayerManager.getInstance().loadAndPlay(trackInformation, link);
            }
        }
    }

    private boolean isUrl(String text) {
        return text.startsWith("http://") || text.startsWith("https://");
    }

}
