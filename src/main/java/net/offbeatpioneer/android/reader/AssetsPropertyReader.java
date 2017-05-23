package net.offbeatpioneer.android.reader;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Read an ini-like file with the help of the {@link Properties} class.
 */
public class AssetsPropertyReader {
    public static final String TAG = "AssetsPropertyReader";
    private Context context;
    private Properties properties;

    public AssetsPropertyReader(Context context) {
        this.context = context;
        properties = new Properties();
    }

    /**
     * Load property file for the given filename. File has to be placed under the assets directory.
     *
     * @param fileName relative file path in the assets directory
     * @return properties object
     * @throws IOException throws exception if file could not be loaded
     */
    public Properties loadProperties(String fileName) throws IOException {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            properties.load(inputStreamReader);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            throw e;
        }
        return properties;
    }

    /**
     * Direct access to the underlying {@link java.util.Hashtable} of the {@link Properties} class.
     *
     * @param name key
     * @return value at the specified key. Throws {@link NullPointerException} if the key is null
     */
    public Object get(String name) {
        return properties.get(name);
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }
}
