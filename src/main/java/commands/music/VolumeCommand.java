package commands.music;

import api.BasicEmbed;
import api.lavalink.MusicManager;
import api.lavalink.PlayerManager;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.Functions;

@DiscordCommand(name = "volume", description = "Установка громкости проигрывания музыки", aliases = {"громкость"},
        usage = "<Новая громкость>", group = "Музыка", arguments = 1)
public class VolumeCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (Functions.checkMusicCommandAuthor(context)) {
            MusicManager musicManager = PlayerManager.getInstance().getMusicManager(context.getGuild());
            if (arguments.length == 0) {
                BasicEmbed infoEmbed = new BasicEmbed("info", "Текущая громкость проигрывания музыки составляет "
                        + musicManager.audioPlayer.getVolume() + "%");
                context.sendMessage(infoEmbed).queue();
            } else {
                try {
                    int newVolume = Integer.parseInt(arguments[0]);
                    if (newVolume < 1) {
                        BasicEmbed errorEmbed = new BasicEmbed("error", "Если вы не хотите слышать музыку, "
                                + "не проще ли вам воспользоваться командой `" + context.getDatabaseGuild().getPrefix() + "stop`?");
                        context.sendMessage(errorEmbed).queue();
                        return;
                    }
                    if (newVolume > 200) {
                        BasicEmbed errorEmbed = new BasicEmbed("error", "Мой создатель заботится о ваших ушах, "
                                + "так что лучше не надо устанавливать такую высокую громкость");
                        context.sendMessage(errorEmbed).queue();
                        return;
                    }
                    musicManager.audioPlayer.setVolume(newVolume);
                    BasicEmbed successEmbed = new BasicEmbed("success", "Громкость успешно установлена на " + newVolume + "%");
                    context.sendMessage(successEmbed).queue();
                } catch (NumberFormatException e) {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите новую громоксть в виде числа от 1 до 200");
                    context.sendMessage(errorEmbed).queue();
                }
            }
        }
    }
}
