package commands.music;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.PermissionsHandler;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.VoiceChannel;

@DiscordCommand(name = "leave", description = "Выход бота из голосового канала", aliases = {"выйти"}, group = "Музыка")
public class LeaveCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        GuildVoiceState botVoiceState = context.getGuild().getSelfMember().getVoiceState();
        if (botVoiceState == null || botVoiceState.getChannel() == null) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Я не нахожусь в голосовом канале");
            context.sendMessage(errorEmbed).queue();
        } else {
            // TODO Проверка на то, этот ли человек добавил бота в канал
            VoiceChannel voiceChannel = context.getGuild().getAudioManager().getConnectedChannel();
            context.getGuild().getAudioManager().closeAudioConnection();
            BasicEmbed successEmbed = new BasicEmbed("success", "Я успешно покинул голосовой канал `"
                    + voiceChannel.getName() + "`");
            context.sendMessage(successEmbed).queue();
        }
    }
}
