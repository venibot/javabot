package api.APIs;

import api.utils.Config;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Wikipedia {

    private String language;

    private String url;

    public Wikipedia() {
        this.language = "ru";
        this.url = "https://" + this.language + ".wikipedia.org/w/api.php";
    }

    public Wikipedia(String language) {
        this.language = language;
        this.url = "https://" + this.language + ".wikipedia.org/w/api.php";
    }

    public String findByQuery(String query) throws IOException {
        String pageName = this.getPageNameByQuery(query);
        String url = this.url;
        url += "?format=json";
        url += "&action=parse";
        url += "&prop=text";
        url += "&page=" + pageName;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Host", "ru.wikipedia.org");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        StringBuilder response = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine = "";
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new Gson().fromJson(response.toString(), JsonObject.class).get("parse").getAsJsonObject().get("text").getAsJsonObject().get("*").getAsString();
    }

    public String getPageNameByQuery(String query) throws IOException {
        String url = this.url;
        url += "?format=json";
        url += "&action=query";
        url += "&list=search";
        url += "&srprop=";
        url += "&srlimit=1";
        url += "&srsearch=" + query;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Host", "ru.wikipedia.org");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        StringBuilder searchResponse = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            searchResponse.append(inputLine);
        }
        in.close();
        JsonObject result = new Gson().fromJson(searchResponse.toString(), JsonObject.class);

        return ((JsonObject) ((JsonArray) ((JsonObject) result.get("query")).get("search")).get(0)).get("title").getAsString();
    }

}
