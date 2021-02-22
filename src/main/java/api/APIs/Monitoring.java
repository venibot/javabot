package api.APIs;

import api.utils.*;
import org.apache.http.client.fluent.*;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import java.io.IOException;
import java.util.*;

public class Monitoring {

    public static void sendBoticordStat(int guilds, int shards, int users) throws IOException {
        Map<String, String> params = new HashMap<String, String>() {
            {
                put("servers", String.valueOf(guilds));
                put("shards", String.valueOf(shards));
                put("users", String.valueOf(users));
            }
        };

        Request.Post("https://boticord.top/api/stats")
                .bodyString(JSONObject.valueToString(params), ContentType.APPLICATION_JSON)
                .addHeader("Authorization", Config.BOT_CONFIG.get("boticordToken"))
                .execute().returnContent();
    }

    public static void sendSDCStat(long id, int guilds, int shards) throws IOException {
        Map<String, String> params = new HashMap<String, String>() {
            {
                put("servers", String.valueOf(guilds));
                put("shards", String.valueOf(shards));
            }
        };

        Request.Post("https://api.server-discord.com/v2/bots/" + id + "/stats")
                .bodyString(JSONObject.valueToString(params), ContentType.APPLICATION_JSON)
                .addHeader("Authorization", "SDC " + Config.BOT_CONFIG.get("sdcToken"))
                .execute();
    }
}
