package utils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
    public static final Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
    public static final JsonParser JSON_PARSER = new JsonParser();

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static Object getJsonObjectValue(String data, String key) {
        return getJsonObjectValue(new JsonParser().parse(data), key);
    }

    public static Object getJsonObjectValue(JsonElement element, String key) {
        Object result = null;
        if (element.isJsonObject()) {
            JsonObject jsonObject = (JsonObject) element;
            JsonElement element2 = jsonObject.get(key);
            if (null != element2) {
                if (element2.isJsonPrimitive()) {
                    JsonPrimitive primitive = (JsonPrimitive)element2;
                    result = primitive.getAsString();
                } else if (element2.isJsonObject()) {
                    result = element2.getAsJsonObject();
                }
            }
        }
        return result;
    }

    public static JsonObject parseJsonObject(String data) {
        JsonParser paser = new JsonParser();
        JsonElement element = paser.parse(data);
        if (element.isJsonObject()) {
            return (JsonObject) element;
        }
        throw new IllegalArgumentException(
                "illeageJsonFormat,which can not be parsed to JsonObject!");
    }

    /**
     * 对JsonObject形式的data set/append指定的key/value
     */
    public static JsonObject setJsonObjectPair(String data, Object... pair) {
        JsonParser paser = new JsonParser();
        JsonElement element = paser.parse(data);
        JsonObject jsonObject = null;
        if (element.isJsonObject()) {
            jsonObject = (JsonObject) element;
            setJsonObjectPair(jsonObject, pair);
        }
        return jsonObject;
    }

    public static JsonObject setJsonObjectPair(JsonObject jsonObject, Object... pair) {
        Preconditions.checkArgument(0 == pair.length % 2, "param not in pairs");
        for (int i = 0; i < pair.length; i += 2) {
            if (jsonObject.has(pair[i].toString())) {
                jsonObject.remove(pair[i].toString());
            }
            if (pair[i + 1] instanceof String) {
                String valueObj = (String) pair[i + 1];
                jsonObject.add(pair[i].toString(), new JsonPrimitive(valueObj));
            }
            if (pair[i + 1] instanceof Boolean) {
                Boolean valueObj = (Boolean) pair[i + 1];
                jsonObject.add(pair[i].toString(), new JsonPrimitive(valueObj));
            } else if (pair[i + 1] instanceof Number) {
                Number valueObj = (Number) pair[i + 1];
                jsonObject.add(pair[i].toString(), new JsonPrimitive(valueObj));
            } else if (pair[i + 1] instanceof JsonObject) {
                jsonObject.add(pair[i].toString(), gson.toJsonTree(pair[i + 1]));
            }
        }
        return jsonObject;
    }


}
