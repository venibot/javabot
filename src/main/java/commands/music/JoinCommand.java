package commands.music;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.PermissionsHandler;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;

@DiscordCommand(name = "join", description = "Вход бота в голосовой канал", aliases = {"войти"}, group = "Музыка")
public class JoinCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        GuildVoiceState userVoiceState = context.getAuthor().getVoiceState();
        GuildVoiceState botVoiceState = context.getGuild().getSelfMember().getVoiceState();
        if (botVoiceState != null && !PermissionsHandler.handleAccessLevel(context, (short) 4)) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Я уже нахожусь в голосовом канале");
            context.sendMessage(errorEmbed).queue();
        } else {
            if (userVoiceState == null || userVoiceState.getChannel() == null) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Для начала сами войдите в голосовой канал");
                context.sendMessage(errorEmbed).queue();
            } else {
                if (!context.getGuild().getSelfMember().hasPermission(userVoiceState.getChannel(),
                        Permission.VOICE_CONNECT, Permission.VOICE_SPEAK)) {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "У меня нет прав для входа в ваш канал");
                    context.sendMessage(errorEmbed).queue();
                } else {
                    context.getGuild().getAudioManager().openAudioConnection(userVoiceState.getChannel());
                    BasicEmbed successEmbed = new BasicEmbed("success", "Я успешно подключился к голосовому каналу `"
                            + userVoiceState.getChannel().getName() + "`");
                    context.sendMessage(successEmbed).queue();
                }
            }
        }
    }
}
