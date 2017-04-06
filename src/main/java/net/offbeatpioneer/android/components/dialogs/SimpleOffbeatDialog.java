package net.offbeatpioneer.android.components.dialogs;

import android.content.Context;
import android.view.View;

import net.offbeatpioneer.android.components.R;


/**
 * Simple "Yes" / "No" Dialog.
 * Extends class {@link OffbeatDialog}
 *
 * In this case Yes/No are synonyms for accepting or negating an option.
 * Implements a simple yes / no dialog in which the no-button dismisses the dialog.
 * Only implement the yes-button logic
 * Uses default template for this type.
 * Created by Dome on 31.10.2016.
 */

public class SimpleOffbeatDialog extends OffbeatDialog implements View.OnClickListener {

    public SimpleOffbeatDialog(Context context) {
        super(context);
        setLayoutResource(R.layout.offbeat_dialog); //default template
    }

    public SimpleOffbeatDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SimpleOffbeatDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void init() {

    }
}
