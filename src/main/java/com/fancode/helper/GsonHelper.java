package com.fancode.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {

    final private static Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();
    final private static Gson GSON_WITH_NULL = new GsonBuilder()
            .disableHtmlEscaping()
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    public static Gson getGson() {
        return GSON;
    }

    public static Gson getGsonWithNull() {
        return GSON_WITH_NULL;
    }

}
