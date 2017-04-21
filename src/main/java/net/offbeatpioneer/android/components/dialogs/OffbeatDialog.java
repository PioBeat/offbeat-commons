package net.offbeatpioneer.android.components.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import net.offbeatpioneer.android.components.R;


/**
 * Super class of the default Dialog for Offbeat-Components
 * Default template is used if no layout resource is specified
 *
 * @author Dominik Grzelak
 *         Created by Dome on 08.10.2016.
 */

public abstract class OffbeatDialog extends Dialog implements View.OnClickListener {
    protected Context context;
    private View.OnClickListener clickListener;

    int layoutResource = -1;
    boolean fullscreen = false;


    protected OffbeatDialog(Context context) {
        super(context);
        this.context = context;
    }

    protected OffbeatDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected OffbeatDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (layoutResource != -1) {
            setContentView(layoutResource);
        } else {
            setContentView(R.layout.offbeat_dialog);
        }
        setCancelable(false);
        init();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        if (isFullscreen()) {

            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        }
//        else {
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        }

        getWindow().setAttributes(lp);
    }

    /**
     * Initialize values in dialog
     */
    protected abstract void init();

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public int getLayoutResource() {
        return layoutResource;
    }

    public void setLayoutResource(int layoutResource) {
        this.layoutResource = layoutResource;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(v);
    }
}
