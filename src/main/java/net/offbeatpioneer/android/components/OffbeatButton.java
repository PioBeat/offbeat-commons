package net.offbeatpioneer.android.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Dome on 10.10.2016.
 */

public class OffbeatButton extends Button {

    public OffbeatButton(Context context) {
        super(context);
    }

    public OffbeatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public OffbeatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public OffbeatButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.OffbeatTextView);
            String fontName = a.getString(R.styleable.OffbeatTextView_font);

            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            a.recycle();
        }
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }
}
