package api.APIs;

import api.utils.Config;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SDC {

    public void sendStat(long id, int guilds, int shards) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("servers", String.valueOf(guilds));
        params.put("shards", String.valueOf(shards));

        Content result = Request.Post("https://api.server-discord.com/v2/bots/" + id + "/stats")
                .bodyString(JSONObject.valueToString(params), ContentType.APPLICATION_JSON)
                .addHeader("Authorization", "SDC " + Config.BOT_CONFIG.get("sdcToken"))
                .execute().returnContent();
    }
}
