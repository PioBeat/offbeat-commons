package net.offbeatpioneer.android.auxiliary;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dome on 15.03.2017.
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
     * @param enabled <code>true</code> to enable, <code>false</code> to disable
     * the views.
     *
     *                Source: https://stackoverflow.com/questions/5418510/disable-the-touch-events-for-all-the-views#8849672
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


}
