package commands.music;

import api.BasicEmbed;
import api.lavalink.MusicManager;
import api.lavalink.PlayerManager;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.GuildVoiceState;

@DiscordCommand(name = "stop", description = "Приостановка воспроизведения музыки", aliases = {"стоп"}, group = "Музыка")
public class StopCommand implements Command {

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
            BasicEmbed errorEmbed = new BasicEmbed("error", "Для использования команды я должен быть в голосовом канале");
            context.sendMessage(errorEmbed).queue();
            return;
        }
        if (!userVoiceState.getChannel().equals(botVoiceState.getChannel())) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Вы должны быть в одном голосовом канале со мной");
            context.sendMessage(errorEmbed).queue();
            return;
        }

        PlayerManager.getInstance().getMusicManager(context.getGuild()).trackScheduler.player.setPaused(true);
        BasicEmbed successEmbed = new BasicEmbed("success", "Воспроизведение музыки успешно приостановлено. "
                + "Для продолжения проигрывания воспользуйтесь командой " + context.getUsedPrefix() + "play");
        context.sendMessage(successEmbed).queue();
    }

    private boolean isUrl(String text) {
        return text.startsWith("http://") || text.startsWith("https://");
    }

}
