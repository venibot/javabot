package api.APIs;

import api.utils.Config;
import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class OpenWeatherMap {

    public static api.models.APIs.OpenWeatherMap getWeather(String town, String lang) {
        String apiKey = Config.BOT_CONFIG.get("openWeatherMapKey");
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + town.replace(" ", "%20")
                + "&appid=" + apiKey + "&lang=" + lang + "&units=metric";

        try {
            Content result = Request.Get(url).execute().returnContent();
            return new Gson().fromJson(result.asString(), api.models.APIs.OpenWeatherMap.class);
        } catch (HttpResponseException e) {
            if (e.getMessage().equals("Not Found")) {
                return new api.models.APIs.OpenWeatherMap();
            } else {
                e.printStackTrace();
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
