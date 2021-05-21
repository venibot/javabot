package commands.info;

import api.BasicEmbed;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.Config;
import api.utils.DataFormatter;
import api.utils.Paginator;
import net.dv8tion.jda.api.Permission;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.util.FormatUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DiscordCommand(name = "botinfo", aliases = {"ботинфо"}, group = "Информация")
public class BotInfoCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        BasicEmbed botAboutEmbed = new BasicEmbed("info");
        botAboutEmbed.setTitle("Немного информации обо мне");
        botAboutEmbed.addField("Кто я такой?", "Я - многофункциональный и постоянно развивающийся бот для вашего сервера. Уже сейчас я обладаю достаточно большим спектром возможностей, которых в недалёком будущем будет ещё больше!");
        botAboutEmbed.addField("Мой создатель", "dmemsm#1706");
        botAboutEmbed.addField("Огромное спасибо",
                "Не пропадающий#2943 - без него проекта бы скорее всего и не существовало. Главный креативщик и придумыватель идей\n" +
                "FELLIKS#2070 - и без него проекта тоже бы не существовало. Постоянно мотивирует меня и иногда даёт крутые идеи\n" +
                "БарБарик#4211 - человек, который постоянно поддерживает меня и не даёт подумать о том, что VeniBot никому не нужен. Ну и делает смешные мемчики, над которыми вся наша команда разработчиков любит посмеяться");
        botAboutEmbed.addField("Я создан", DataFormatter.datetimeToString(context.getJDA().getSelfUser().getTimeCreated()));
        botAboutEmbed.addField("Текущая версия", Config.BOT_CONFIG.get("version"));
        botAboutEmbed.addField("Полезные ссылки", "[Добавить бота бота](" + context.getJDA().getInviteUrl(Permission.ADMINISTRATOR) + ")\n[Сервер поддержки](https://dicord.gg/uEhrZUX)");

        BasicEmbed statEmbed = new BasicEmbed("info");
        statEmbed.setTitle("Некоторая статистика бота");
        statEmbed.addField("Количество серверов", String.valueOf(context.getJDA().getShardManager().getGuilds().size()));
        statEmbed.addField("Количество пользователей", String.valueOf(context.getJDA().getShardManager().getUsers().size()));
        statEmbed.addField("Количество каналов", String.valueOf(context.getJDA().getShardManager().getTextChannels().size()
                + context.getJDA().getShardManager().getVoiceChannels().size()));
        statEmbed.addField("Количество эмодзи", String.valueOf(context.getJDA().getShardManager().getEmotes().size()));
        statEmbed.addField("Количество шардов", String.valueOf(context.getJDA().getShardManager().getShardsTotal()));
        statEmbed.addField("Шард этого сервера", "#" + context.getJDA().getShardInfo().getShardId());
        statEmbed.addField("Пинг этого шарда", context.getJDA().getGatewayPing() + " мс");
        statEmbed.addField("Дата запуска бота", DataFormatter.datetimeToString(Config.START_TIME));

        BasicEmbed botHostInfo = new BasicEmbed("info");
        botHostInfo.setTitle("Немного информации о хостинге бота");
        SystemInfo systemInfo = new SystemInfo();
        botHostInfo.addField("Аптайм хостинга",
                DataFormatter.longToUptime(systemInfo.getOperatingSystem().getSystemUptime()));
        GlobalMemory RAM = systemInfo.getHardware().getMemory();
        botHostInfo.addField("Потребление оперативной памяти",
                (FormatUtil.formatBytes(RAM.getTotal() - RAM.getAvailable()) + "/" + FormatUtil.formatBytes(RAM.getTotal()))
                        .replace("Available: ", "").replace("GiB", "ГБ").replace("Mib", "МБ")
                + "(" + (((RAM.getTotal() - RAM.getAvailable()) * 100) / RAM.getTotal()) + "%)");
        botHostInfo.addField("Температура процессора", ((int) systemInfo.getHardware().getSensors().getCpuTemperature()) + "°C");

        List<BasicEmbed> embeds = new ArrayList<>();
        embeds.add(botAboutEmbed);
        embeds.add(statEmbed);
        embeds.add(botHostInfo);
        context.sendMessage(botAboutEmbed).queue(message -> new Paginator(context.getAuthor().getUser(), message, embeds));
    }
}
