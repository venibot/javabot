package api.APIs;

import api.utils.Config;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
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

}
