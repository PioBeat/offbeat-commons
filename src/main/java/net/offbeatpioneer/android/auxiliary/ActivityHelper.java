package net.offbeatpioneer.android.auxiliary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Dominik Grzelak
 */
public class ActivityHelper {
    private static final ActivityHelper ourInstance = new ActivityHelper();
    private static Context context;

    public static ActivityHelper getInstance() {
        return ourInstance;
    }

    private ActivityHelper() {
    }

    public ActivityHelper init(Context context) {
        this.context = context.getApplicationContext();
        return this;
    }


    public int getDrawableResource(String name) {
        return this.getResourceId(name, "drawable");
    }

    public int getResourceId(String name, String type) {
        return context.getResources().getIdentifier(name, type, context.getPackageName());
    }

    public static void startAcitivty(Class<?> activityClass, Activity activity) {
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
    }

    public static void startAcitivty(Class<?> activityClass, Activity activity, Bundle bundle) {
        Intent intent = new Intent(activity, activityClass);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ani = cm.getActiveNetworkInfo();
        if (ani != null && ani.isConnectedOrConnecting()) {
            return true;
        } else
            return false;
    }

    /**
     * Enables/Disables all child views in a view group.
     *
     * @param viewGroup the view group
     * @param enabled   <code>true</code> to enable, <code>false</code> to disable
     *                  the views.
     *                  <p>
     *                  Source: https://stackoverflow.com/questions/5418510/disable-the-touch-events-for-all-the-views#8849672
     */
    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

    //source: http://www.codejava.net/coding/how-to-calculate-md5-and-sha-hash-values-in-java
    //TODO change to char array
    public static String generateSHA256(String message) throws Exception {
        return hashString(message, "SHA-256");
    }
    //TODO change to char array
    public static String generateSHA1(String message) throws Exception {
        return hashString(message, "SHA-1");
    }

    //TODO change to char array
    private static String hashString(String message, String algorithm)
            throws Exception {

        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new Exception(
                    "Could not generate hash from String", ex);
        }
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }

}
