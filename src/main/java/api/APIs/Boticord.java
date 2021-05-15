package api.APIs;

import api.utils.Config;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Boticord {

    public void sendStat(int guilds, int shards, int users) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("servers", String.valueOf(guilds));
        params.put("shards", String.valueOf(shards));
        params.put("users", String.valueOf(users));

        Content result = Request.Post("https://boticord.top/api/stats")
                .bodyString(JSONObject.valueToString(params), ContentType.APPLICATION_JSON)
                .addHeader("Authorization", Config.BOT_CONFIG.get("boticordToken"))
                .execute().returnContent();
    }

    public JsonObject getBotInfo(String query) throws IOException {
        try {
            Gson gson = new Gson();
            Content searchResult = Request.Post("https://boticord.top/search")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .bodyString("term=" + query, ContentType.APPLICATION_FORM_URLENCODED)
                    .execute().returnContent();
            JsonArray searchResultArray = gson.fromJson(searchResult.asString(), JsonArray.class);
            if (searchResultArray.size() != 0) {
                Content result = Request.Get("https://boticord.top/api/v1/bot/"
                        + searchResultArray.get(0).getAsJsonObject().get("botID").getAsString())
                        .addHeader("Authorization", Config.BOT_CONFIG.get("boticordToken"))
                        .execute().returnContent();
                return gson.fromJson(result.asString(), JsonObject.class);
            } else {
                throw new HttpResponseException(404, "Not found");
            }
        } catch (HttpResponseException e) {
            return null;
        }
    }

    public JsonArray getBotComments(String botID) throws IOException {
        Content result = Request.Get("https://boticord.top/api/v1/bot/" + botID + "/comments")
                .addHeader("Authorization", Config.BOT_CONFIG.get("boticordToken"))
                .execute().returnContent();
        return new Gson().fromJson(result.asString(), JsonArray.class);
    }

}
