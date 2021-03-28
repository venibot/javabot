package api.cache;

import api.models.Cache;
import com.google.gson.Gson;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class CurrencyCache extends Cache {

    protected static Map cache;

    public static void setCache() {
        try {
            Content result = Request.Get("https://api.exchangeratesapi.io/latest?base=RUB").execute().returnContent();
            CurrencyCache.cache = (Map) new Gson().fromJson(result.asString(), Map.class).get("rates");
            System.out.println(CurrencyCache.cache.keySet());
            CurrencyCache.date = new Date().getTime();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map getCache() {
        return CurrencyCache.cache;
    }

}
