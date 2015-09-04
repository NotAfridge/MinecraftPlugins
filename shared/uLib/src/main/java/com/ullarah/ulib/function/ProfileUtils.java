package com.ullarah.ulib.function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class ProfileUtils {

    private static Cache<String, PlayerProfile> nameCache = new Cache<>();
    private static Cache<UUID, PlayerProfile> idCache = new Cache<>();
    private static JSONParser PARSER = new JSONParser();

    private ProfileUtils() {
    }

    public static PlayerProfile lookup(String name) {

        if (nameCache.contains(name)) return nameCache.get(name);

        List<PlayerProfile> response = postNames(new String[]{name});

        if (response == null) return null;
        if (response.isEmpty()) return null;

        return response.get(0);

    }

    public static PlayerProfile lookup(UUID id) {

        if (Bukkit.getPlayer(id) != null) return fromPlayer(Bukkit.getPlayer(id));
        return lookupProperties(id);

    }

    public static PlayerProfile lookupProperties(UUID id) {

        if (idCache.contains(id)) return idCache.get(id);

        Object rawResponse = getJson("https://sessionserver.mojang.com/session/minecraft/profile/"
                + id.toString().replace("-", ""));

        if (rawResponse == null || !(rawResponse instanceof JSONObject)) return null;

        JSONObject response = (JSONObject) rawResponse;
        PlayerProfile profile = deserializeProfile(response);

        if (profile == null) return null;

        idCache.put(id, profile);

        return profile;

    }

    @SuppressWarnings("unchecked")
    private static List<PlayerProfile> postNames(String[] names) {

        JSONArray request = new JSONArray();
        Collections.addAll(request, names);
        Object rawResponse = postJson(request);

        if (!(rawResponse instanceof JSONArray)) return null;

        JSONArray response = (JSONArray) rawResponse;
        List<PlayerProfile> profiles = new ArrayList<>();

        for (Object rawEntry : response) {

            if (!(rawEntry instanceof JSONObject)) return null;

            JSONObject entry = (JSONObject) rawEntry;
            PlayerProfile profile = deserializeProfile(entry);

            if (profile != null) profiles.add(profile);

        }

        return profiles;

    }

    private static PlayerProfile deserializeProfile(JSONObject json) {

        if (!json.containsKey("name") || !json.containsKey("id")) return null;

        if (!(json.get("name") instanceof String) || !(json.get("id") instanceof String)) return null;

        String name = (String) json.get("name");
        UUID id = toUUID((String) json.get("id"));

        if (id == null) return null;

        PlayerProfile profile = new PlayerProfile(id, name);

        if (json.containsKey("properties") && json.get("properties") instanceof JSONArray)
            profile.properties = (JSONArray) json.get("properties");

        return profile;

    }

    private static String toString(UUID id) {

        return id.toString().replace("-", "");

    }

    private static UUID toUUID(String raw) {

        String dashed;

        if (raw.length() == 32)
            dashed = raw.substring(0, 8) + "-" + raw.substring(8, 12) + "-" + raw.substring(12, 16)
                    + "-" + raw.substring(16, 20) + "-" + raw.substring(20, 32);

        else dashed = raw;

        return UUID.fromString(dashed);

    }

    private static Object getJson(String rawUrl) {

        BufferedReader reader = null;

        try {

            URL url = new URL(rawUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) result.append(line);

            return PARSER.parse(result.toString());

        } catch (Exception ex) {

            return null;

        } finally {

            try {

                if (reader != null) reader.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

    private static Object postJson(JSONArray body) {

        String rawResponse = post("https://api.mojang.com/profiles/minecraft", body.toJSONString());

        if (rawResponse == null) return null;

        try {

            return PARSER.parse(rawResponse);

        } catch (Exception e) {

            return null;

        }

    }

    private static String post(String rawUrl, String body) {

        BufferedReader reader = null;
        OutputStream out = null;

        try {

            URL url = new URL(rawUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            out = connection.getOutputStream();
            out.write(body.getBytes());

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) result.append(line);

            return result.toString();

        } catch (IOException ex) {

            ex.printStackTrace();
            return null;

        } finally {

            try {

                if (out != null) out.close();
                if (reader != null) reader.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

    private static PlayerProfile fromPlayer(Player player) {

        return new PlayerProfile(player.getUniqueId(), player.getName());

    }

    public static class PlayerProfile {

        private final UUID id;
        private final String name;
        private JSONArray properties;

        public PlayerProfile(UUID id, String name) {

            this.id = id;
            this.name = name;

        }

        public UUID getId() {

            return id;

        }

        public String getName() {

            return name;

        }

        public JSONArray getProperties() {

            return properties;

        }

    }

    private static class Cache<K, V> {

        private long expireTime = 1000 * 60 * 5;
        private Map<K, CachedEntry<V>> map = new HashMap<>();

        public boolean contains(K key) {

            return map.containsKey(key) && get(key) != null;

        }

        public V get(K key) {

            CachedEntry<V> entry = map.get(key);

            if (entry == null) return null;

            if (entry.isExpired()) {

                map.remove(key);
                return null;

            } else return entry.getValue();

        }

        public void put(K key, V value) {

            map.put(key, new CachedEntry<>(value, expireTime));

        }

        private static class CachedEntry<V> {

            private final SoftReference<V> value;
            private final long expires;

            public CachedEntry(V value, long expireTime) {

                this.value = new SoftReference<>(value);
                this.expires = expireTime + System.currentTimeMillis();

            }

            public V getValue() {

                if (isExpired()) return null;

                return value.get();

            }

            public boolean isExpired() {

                return value.get() == null || expires != -1 && expires > System.currentTimeMillis();

            }

        }

    }

}
