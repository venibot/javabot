package commands.utils;

import api.BasicEmbed;
import api.models.APIs.OpenWeatherMap;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.utils.DataFormatter;

@DiscordCommand(name = "weather", description = "Узнать погоду в определённом месте", aliases = {"погода"},
                usage = "<Город>", group = "Утилиты", arguments = 1)
public class WeatherCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите город, в котором вы хотите получить текущую погоду");
            context.sendMessage(errorEmbed).queue();
        } else {
            try {
                OpenWeatherMap weather = api.APIs.OpenWeatherMap.getWeather(arguments[0], context.getLocale());
                if (weather == null) {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "Произошла непредвиденная ошибка. Повторите попытку позже.");
                    context.sendMessage(errorEmbed).queue();
                } else if (weather.name == null) {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный город не найден");
                    context.sendMessage(errorEmbed).queue();
                } else {
                    BasicEmbed infoEmbed = new BasicEmbed("info");
                    infoEmbed.setTitle("Погода в городе " + weather.name);
                    infoEmbed.addField(DataFormatter.Weather.translateWeatherMain(weather.weather[0].get("main")),
                            DataFormatter.firstUpperCase(weather.weather[0].get("description")));
                    infoEmbed.addField("Температура", Math.round(weather.main.get("temp")) + "°C");
                    infoEmbed.addField("Ощущается как", Math.round(weather.main.get("feels_like")) + "°C");
                    infoEmbed.addField("Давление", DataFormatter.Weather.pressureToMillimeters(weather.main.get("pressure"))
                            + " мм ртутного столба");
                    infoEmbed.addField("Влажность", Math.round(weather.main.get("humidity")) + "%");
                    infoEmbed.addField("Облачность", Math.round(weather.clouds.get("all")) + "%");
                    infoEmbed.addField("Скорость ветра", Math.round(weather.wind.get("speed")) + " м/с");
                    context.sendMessage(infoEmbed).queue();
                }
            } catch (IllegalArgumentException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный город не найден");
                context.sendMessage(errorEmbed).queue();
            }
        }
    }

}
