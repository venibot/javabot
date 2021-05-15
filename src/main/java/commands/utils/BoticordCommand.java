package commands.utils;

import api.APIs.Boticord;
import api.BasicEmbed;
import api.SupportServer;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.DataFormatter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

@DiscordCommand(name = "boticord", description = "Получение различной информации с мониторинга BotiCord",
        aliases = {"ботикорд"}, usage = "bot <Запрос>", group = "Утилиты", arguments = 2)
public class BoticordCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Не указан тип запроса");
        }
        switch (arguments[0]) {
            case "бот":
            case "bot":
                context.sendMessage(new BasicEmbed("info", "Секунду...")).queue(message -> {
                    Boticord boticord = new Boticord();
                    try {
                        JsonObject botInfo = boticord.getBotInfo(arguments[1]);
                        if (botInfo != null) {
                            JsonObject botInformation = botInfo.get("information").getAsJsonObject();
                            BasicEmbed infoEmbed = new BasicEmbed("info");
                            infoEmbed.setTitle("Информация о боте " + botInfo.get("tag").getAsString());
                            if (botInformation.get("status").getAsString().equals("APPROVED")) {
                                infoEmbed.setDescription(botInformation
                                        .get("description").getAsJsonObject().get("short").getAsString());
                                infoEmbed.addField("Префикс бота по умолчанию",
                                        botInformation.get("prefix").getAsString());
                                infoEmbed.addField("Библиотека на которой написан бот",
                                        DataFormatter.getBoticordBotLibrary(botInformation.get("library").getAsString()));
                                JsonArray tags = botInformation.get("tags").getAsJsonArray();
                                String tagsShow = "";
                                for (JsonElement tag : tags) {
                                    tagsShow += tag.getAsString() + ", ";
                                }
                                if (!tagsShow.equals("")) {
                                    tagsShow = tagsShow.replace(", $", "");
                                    tagsShow = Character.toUpperCase(tagsShow.charAt(0)) + tagsShow.substring(1);
                                } else {
                                    tagsShow = "Не указаны";
                                }
                                infoEmbed.addField("Тэги бота", tagsShow);
                                JsonObject stats = botInformation.get("stats").getAsJsonObject();
                                infoEmbed.addField("Статистика",
                                        "Количество апов: " + botInformation.get("bumps").getAsString() +
                                                "\nКоличество добавлений: " + botInformation.get("added").getAsString() +
                                                "\nСерверов: " + stats.get("servers").getAsString() +
                                                "\nПользователей: " + stats.get("users").getAsString());
                                infoEmbed.addField("Ссылка на страницу бота",
                                        "[Перейти](https://boticord.top/bot/"
                                                + botInfo.get("id").getAsString() + ")");
                                String addLink = "https://discord.com/oauth2/authorize?scope=bot%20applications.commands&";
                                addLink += "client_id=" + botInfo.get("id").getAsString() + '&';
                                addLink += "permissions=" + botInformation.get("permissions");
                                infoEmbed.addField("Ссылка на добавление бота", "[Тык](" + addLink + ")");
                                String showLinks = "Отсутствуют";
                                JsonObject links = botInformation.get("links").getAsJsonObject();
                                try {
                                    if (links.get("github") != null) {
                                        showLinks = "[GitHub бота](" + links.get("github").getAsString() + ")";
                                    }
                                } catch (UnsupportedOperationException ignored) {

                                }
                                try {
                                    if (links.get("github") != null) {
                                        if (!showLinks.equals("Отсутствуют")) {
                                            showLinks += ", [сервер поддержки бота](https://discord.gg/"
                                                    + links.get("discord").getAsString() + ")";
                                        } else {
                                            showLinks = "[Сервер поддержки бота](https://discord.gg/"
                                                    + links.get("discord").getAsString() + ")";
                                        }
                                    }
                                } catch (UnsupportedOperationException ignored) {

                                }
                                try {
                                    if (links.get("site") != null) {
                                        if (!showLinks.equals("Отсутствуют")) {
                                            showLinks += ", [сайт бота](" + links.get("site").getAsString() + ")";
                                        } else {
                                            showLinks = "[Сайт бота](" + links.get("site").getAsString() + ")";
                                        }
                                    }
                                } catch (UnsupportedOperationException ignored) {

                                }
                                infoEmbed.addField("Указанные ссылки", showLinks);
                            } else {
                                infoEmbed.addField("Статус бота",
                                        DataFormatter.getBoticordBotStatus(botInformation.get("status").getAsString()));
                            }
                            message.editMessage(infoEmbed.build()).queue();
                        } else {
                            BasicEmbed errorEmbed = new BasicEmbed("error",
                                    "Указанный бот не найден на BotiCord");
                            message.editMessage(errorEmbed.build()).queue();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            case "комментарии":
            case "comments":
                if (!new SupportServer(context.getJDA()).isDeveloper(context.getAuthor().getUser())) {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "В разработке");
                    context.sendMessage(errorEmbed).queue();
                    return;
                }
                context.sendMessage(new BasicEmbed("info", "Секунду...")).queue(message -> {
                    Boticord boticord = new Boticord();
                    try {
                        JsonObject botInfo = boticord.getBotInfo(arguments[1]);
                        if (botInfo != null) {
                            BasicEmbed infoEmbed = new BasicEmbed("info");
                            infoEmbed.setTitle("Комментарии к боту " + botInfo.get("tag").getAsString());
                            JsonArray botComments = boticord.getBotComments(botInfo.get("id").getAsString());
                            if (botComments.size() != 0) {
                                infoEmbed.setDescription("Всего комментариев " + botComments.size());
                                if (botComments.size() > 25) {

                                } else {
                                    for (JsonElement comment: botComments) {
                                        infoEmbed.addField(
                                                "Комментарий от " + comment.getAsJsonObject().get("userID").getAsString(),
                                                comment.getAsJsonObject().get("text").getAsString());
                                    }
                                }
                            } else {
                                infoEmbed.setDescription("Комментарии отсутствуют");
                            }
                            message.editMessage(infoEmbed.build()).queue();
                        } else {
                            BasicEmbed errorEmbed = new BasicEmbed("error",
                                    "Указанный бот не найден на BotiCord");
                            message.editMessage(errorEmbed.build()).queue();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        }
    }
}
