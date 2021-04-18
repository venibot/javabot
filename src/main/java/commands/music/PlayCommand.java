package commands.music;

import api.BasicEmbed;
import api.lavalink.MusicManager;
import api.lavalink.PlayerManager;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.GuildVoiceState;

@DiscordCommand(name = "play", description = "Проигрывание музыки", aliases = {"плей"}, usage = "[Название трека]",
        group = "Музыка", arguments = 1)
public class PlayCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        GuildVoiceState botVoiceState = context.getGuild().getSelfMember().getVoiceState();
        GuildVoiceState userVoiceState = context.getAuthor().getVoiceState();
        if (!userVoiceState.inVoiceChannel()) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Для использования команды войдите в голосовой канал");
            context.sendMessage(errorEmbed).queue();
            return;
        }
        if (!botVoiceState.inVoiceChannel()) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Перед использованием этой команды воспользуйтесь "
                    + context.getUsedPrefix() + "join");
            context.sendMessage(errorEmbed).queue();
            return;
        }
        if (!userVoiceState.getChannel().equals(botVoiceState.getChannel())) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Вы должны быть в одном голосовом канале со мной");
            context.sendMessage(errorEmbed).queue();
            return;
        }

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
            PlayerManager.getInstance().loadAndPlay(context.getMessage().getTextChannel(), link);
        }
    }

    private boolean isUrl(String text) {
        return text.startsWith("http://") || text.startsWith("https://");
    }

}
