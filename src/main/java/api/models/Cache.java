package api.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Cache {

    protected static Integer relevanceTime = 60 * 60 * 3600;

    protected static Long date = 0L;

    protected static Map cache;

    public static boolean isRelevant() {

        Long now = new Date().getTime();
        if (Cache.date == 0) {return false;}
        System.out.println(Cache.date + relevanceTime);
        System.out.println(now);
        return (Cache.date + relevanceTime) > now;
    }

    public static void setCache(Map newCache) {

        Cache.date = new Date().getTime();
        Cache.cache = newCache;
    }

    public static void clearCache() {

        Cache.cache = null;
        Cache.date = null;
    }
}
