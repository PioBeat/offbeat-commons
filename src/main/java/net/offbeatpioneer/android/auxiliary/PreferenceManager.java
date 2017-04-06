package net.offbeatpioneer.android.auxiliary;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Utility class to read and write from a shared preferences file
 */
public class PreferenceManager {
    private static PreferenceManager ourInstance = new PreferenceManager();
    public static String PREFERENCE_FILE = "OFFBEAT_CONF";

    final public static int VALUE_STRING = 0;
    final public static int VALUE_INT = 1;
    final public static int VALUE_BOOL = 2;
    final public static int VALUE_LONG = 3;
    final public static int VALUE_FLOAT = 4;

    public Context context;

    SharedPreferences sharedPreferences;

    public synchronized static PreferenceManager getInstance(Context context) {
        if (ourInstance.context != context) {
            ourInstance.context = context;
            ourInstance.sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE, MODE_PRIVATE);
        }
        return ourInstance;
    }

    private PreferenceManager() {
    }

    /**
     * Reads a value for the specified key in sharedpreferences of the app
     * Default for String: ""
     * Default for Int: 0
     * Default for Boolean: false
     * Default for Long: 0
     * Default for Float: 0f
     *
     * @param key
     * @param valueType
     * @return
     */
    public Object readKey(String key, int valueType) {
        switch (valueType) {
            case 0:
                return sharedPreferences.getString(key, "");
            case 1:
                return sharedPreferences.getInt(key, 0);
            case 2:
                return sharedPreferences.getBoolean(key, false);
            case 3:
                return sharedPreferences.getLong(key, 0);
            case 4:
                return sharedPreferences.getFloat(key, 0.0f);
        }
        return null;
    }

    public void writeValue(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        }
        if (value instanceof String) {
            editor.putString(key, (String) value);
        }
        if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        }
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
        if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.apply();
    }

    public void removeKey(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }


}
